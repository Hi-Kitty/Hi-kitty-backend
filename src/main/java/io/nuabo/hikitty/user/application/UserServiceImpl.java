package io.nuabo.hikitty.user.application;

import io.nuabo.common.application.port.DefaultImageConfig;
import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.amazons3.application.port.AWSConnection;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.security.application.port.PasswordEncoderHolder;
import io.nuabo.hikitty.user.application.port.ProfileRepository;
import io.nuabo.hikitty.user.application.port.UserProfileDto;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.Role;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import io.nuabo.hikitty.user.presentation.port.UserService;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import io.nuabo.hikitty.user.presentation.request.UserUpdateRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Builder
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UuidHolder uuidHolder;

    private final CertificationService certificationService;
    private final ProfileRepository profileRepository;
    private final PasswordEncoderHolder passwordEncoder;
    private final AWSConnection awsConnection;

    private final DefaultImageConfig defaultImageConfig;
    @Transactional
    @Override
    public User create(UserCreateRequest userCreateRequest) {
        User user = User.register(userCreateRequest, passwordEncoder, uuidHolder);

        Optional<User> beforeUser = userRepository.findByEmail(userCreateRequest.getEmail());
        if (beforeUser.isPresent()) {
            user = user.check(beforeUser.get().getId(), uuidHolder);
        }

        user = userRepository.save(user);
        certificationService.sendMailFromTemplate(userCreateRequest.getEmail(), user.getCertificationCode(), userCreateRequest.getName());
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Transactional
    @Override
    public UserProfileDto update(String email, UserUpdateRequest userUpdate, MultipartFile img) {
        User user = getByEmail(email);
        user = user.update(userUpdate);
        user = userRepository.save(user);
        if (img != null) {
            AmazonS3Upload amazonS3Upload = awsConnection.sendFileToAWS(img);
            Profile newProfile = Profile.from(amazonS3Upload, user);

            Optional<Profile> profile = profileRepository.findByUserId(user.getId());
            if (profile.isPresent()) {
                newProfile = profile.get().update(newProfile, user);
            }
            newProfile = profileRepository.save(newProfile, user);
            return UserProfileDto.merge(newProfile, user);
        }
        User finalUser = user;
        return profileRepository.findByUserId(user.getId()).map(
                profile -> UserProfileDto.merge(profile, finalUser)
        ).orElse(UserProfileDto.from(user));
    }

    @Transactional
    @Override
    public void verifyEmail(long id, String certificationCode) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        user = user.certificate(certificationCode, user.getCertificationCode());
        userRepository.save(user);
    }

    @Override
    public UserProfileDto getUserAndProfile(String email) {
        User user = getByEmail(email);
        log.info("user: {}", user.getId());


        return profileRepository.findByUserId(user.getId())
                .map(profile -> UserProfileDto.merge(profile, user))
                .orElse(getDefaultProfile(user));
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE).isPresent();
    }

    private UserProfileDto getDefaultProfile(User user) {
        Profile profile;
        if (user.getRole() == Role.ROLE_DONER) {
            profile = UserProfileDto.merge(user,
                    defaultImageConfig.getDefaultImageDonerUrl(),
                    defaultImageConfig.getDefaultImageDonerOriginalName());
        } else {
            profile = UserProfileDto.merge(user,
                    defaultImageConfig.getDefaultImageFundraiserUrl(),
                    defaultImageConfig.getDefaultImageFundraiserOriginalName());
        }
        return UserProfileDto.merge(profile, user);
    }
}

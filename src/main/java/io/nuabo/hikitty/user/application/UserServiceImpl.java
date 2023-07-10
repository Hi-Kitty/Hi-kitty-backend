package io.nuabo.hikitty.user.application;

import io.micrometer.core.annotation.Timed;
import io.nuabo.common.application.port.DefaultImageConfig;
import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.amazons3.application.port.AWSConnection;
import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.board.application.port.BoardRepository;
import io.nuabo.hikitty.board.application.port.HeartRepository;
import io.nuabo.hikitty.board.domain.Board;
import io.nuabo.hikitty.board.domain.Heart;
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

import java.util.List;
import java.util.Optional;

@Timed("user")
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

    private final BoardRepository boardRepository;
    private final DefaultImageConfig defaultImageConfig;
    private final HeartRepository heartRepository;
    @Transactional
    @Override
    public User create(UserCreateRequest userCreateRequest) {
        User user = User.register(userCreateRequest, passwordEncoder, uuidHolder);

        Optional<User> beforeUser = userRepository.findByEmail(userCreateRequest.getEmail());
        if (beforeUser.isPresent()) {
            user = user.check(beforeUser.get().getId(), uuidHolder);
        }

        user = userRepository.save(user);
        certificationService.sendMailFromTemplate(userCreateRequest.getEmail(), user.getCertificationCode(), userCreateRequest.getName(), user.getId());
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
        if (userUpdate != null) {
            user = user.update(userUpdate, passwordEncoder);
            user = userRepository.save(user);
        }
        if (img != null) {
            AmazonS3Upload amazonS3Upload = awsConnection.sendFileToAWS(img);
            Profile newProfile = Profile.from(amazonS3Upload, user);

            Optional<Profile> profile = profileRepository.findByUserId(user.getId());
            if (profile.isPresent()) {
                newProfile = profile.get().update(newProfile, user);
            }
            newProfile = profileRepository.save(newProfile, user);

            if (user.getRole() == Role.ROLE_FUNDRAISER) {
                List<Board> boards = boardRepository.getAllByUserId(user.getId());
                if (boards != null) {
                    boards = boards.stream().map(board -> board.updateImg(amazonS3Upload)).toList();
                    boardRepository.saveAll(boards);
                }
            }
            if (user.getRole() == Role.ROLE_DONER) {
                List<Heart> hearts = heartRepository.getAllByUserId(user.getId());
                if (hearts != null) {
                    hearts = hearts.stream().map(heart -> heart.updateImg(amazonS3Upload)).toList();
                    heartRepository.saveAll(hearts);
                }
            }

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

    @Transactional(readOnly = true)
    @Override
    public UserProfileDto getUserAndProfile(String email) {
        User user = getByEmail(email);

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

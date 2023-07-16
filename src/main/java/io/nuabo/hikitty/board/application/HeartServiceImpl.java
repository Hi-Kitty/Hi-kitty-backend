package io.nuabo.hikitty.board.application;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.board.application.port.BoardRepository;
import io.nuabo.hikitty.board.application.port.HeartRepository;
import io.nuabo.hikitty.board.domain.Board;
import io.nuabo.hikitty.board.domain.Heart;
import io.nuabo.hikitty.board.domain.Status;
import io.nuabo.hikitty.board.presentation.port.HeartService;
import io.nuabo.hikitty.user.application.port.ProfileRepository;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Builder
@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {

    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    @Override
    public Heart createHeart(Long boardId, String email) {
        Board board = boardRepository.getById(boardId);
        User user = userRepository.getByEmailAndStatus(email, UserStatus.ACTIVE);

        return heartRepository.findByBoardIdAndDonerId(boardId, user.getId())
                .filter(heart -> heart.getStatus().equals(Status.ACTIVE))
                .filter(heart -> heart.getStatus().equals(Status.INACTIVE))
                .map(Heart::active)
                .orElseGet(() -> {
                    Heart heart = profileRepository.findByUserId(user.getId())
                            .map(profile -> Heart.from(board, user, profile))
                            .orElse(Heart.from(board, user));
                    return heartRepository.save(heart);
                });
    }

    @Transactional
    @Override
    public Heart deleteHeart(Long heartId) {
        return heartRepository.findById(heartId)
                .filter(heart -> heart.getStatus().equals(Status.ACTIVE))
                .map(heart -> heartRepository.save(heart.inActivce()))
                .orElseThrow(() -> new ResourceNotFoundException("Heart", heartId));
    }
}

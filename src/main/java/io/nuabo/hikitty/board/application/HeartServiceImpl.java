package io.nuabo.hikitty.board.application;

import io.nuabo.common.domain.exception.HeartException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

        Optional<Heart> beforeHeart = heartRepository.findByBoardIdAndDonerId(boardId, user.getId());
        if (beforeHeart.isPresent()) {
            Heart heart = beforeHeart.get();
            if (heart.getStatus().equals(Status.ACTIVE)) {
                throw new HeartException("Heart를 이미 하트를 누른 게시글입니다.");
            }
            heart = heart.active();
            heartRepository.save(heart);
            return heart;
        }

        Heart heart = profileRepository.findByUserId(user.getId())
                .map(profile -> Heart.from(board, user, profile))
                .orElse(Heart.from(board, user));
        heart = heartRepository.save(heart);

        return heart;
    }

    @Transactional
    @Override
    public Heart deleteHeart(Long heartId) {

        Heart heart = heartRepository.getById(heartId);
        if (heart.getStatus().equals(Status.INACTIVE)) {
            throw new HeartException("Heart를 이미 취소한 게시글입니다.");
        }
        heart = heart.inActivce();
        heart = heartRepository.save(heart);
        return heart;
    }
}

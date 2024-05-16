package jpa.study.post.application.service;

import jpa.study.post.repository.CommnetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommnetRepository commnetRepository;

//    public Long writeComment(Long userId, ) {
//
//    }
}

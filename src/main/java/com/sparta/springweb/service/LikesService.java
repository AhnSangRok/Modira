package com.sparta.springweb.service;

import com.sparta.springweb.model.User;
import com.sparta.springweb.repository.LikesRepository;
import com.sparta.springweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likes(Long contentsId, String username){
        User user = userRepository.findOneByUsername(username);
        likesRepository.likes(contentsId,user.getId());

    }
    @Transactional
    public void unLikes(Long contentsId, String username){
        User user = userRepository.findOneByUsername(username);
        likesRepository.unlikes(contentsId,user.getId());
    }

}
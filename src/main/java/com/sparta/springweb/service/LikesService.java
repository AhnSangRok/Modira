package com.sparta.springweb.service;

import com.sparta.springweb.dto.LikeDto;
import com.sparta.springweb.model.Likes;
import com.sparta.springweb.model.Posts;
import com.sparta.springweb.repository.LikesRepository;
import com.sparta.springweb.repository.PostsRepository;
import com.sparta.springweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public void likes(LikeDto likeDto) {
        // ch로 유저가 like를 누름여부 판단 , false = unlike | true = like
        boolean ch = false;
        // 요첨받는 Dto로 DB에 저장할 likes 객체 생성
        Likes likes = new Likes(likeDto);
        // DB에 있는 모든 likes 정보 받아오기
        List<Likes> likesList = findLikes(likes.getPostId());

        // for문을 통해 객체를 순차적으로 불러옴
        for (Likes check : likesList) {
            // 유저가 해당 Post에 likes를 눌렀는지 getUserName으로 불러와 비교
            if (Objects.equals(check.getUserName(), likes.getUserName())){
                likesRepository.deleteById(check.getId()); // 해당 유저가 이미 likes를 눌렀으면 unlikes로 변환
                ch = true;
                break;
            }
        }

        //likes를 누른 post의 정보를 받아옴
        Posts post = postsRepository.findById(likes.getPostId()).orElseThrow(() -> new IllegalArgumentException("해당하는 ID가 없습니다."));
        if (!ch){
            if(post.getJoinNum() >= post.getPartyNum()){ // 만약 이미 모집인원이 다 모집됬을 경우 정보를 저장하지 않는다.
                throw new IllegalArgumentException("참여가능인원이 모두 모집되었습니다.");
            }else{
                likesRepository.save(likes);
            }
        }
    }

    @Transactional
    public List<Likes> findLikes(Long postId) { // DB에 있는 모든 likes 정보를 찾아온다.
        List<Likes> likes = likesRepository.findAllByPostId(postId);
        return likes;
    }

}
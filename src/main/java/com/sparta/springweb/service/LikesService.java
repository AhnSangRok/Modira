package com.sparta.springweb.service;

import com.sparta.springweb.dto.LikeDto;
import com.sparta.springweb.model.Likes;
import com.sparta.springweb.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final PostsService postsService;

    @Transactional
    public void likes(LikeDto likeDto) {

//      boolean ch = false;
        Likes likes = new Likes(likeDto);
//        List<Likes> likesList = findLikes(likes.getPostId());

//        for (Likes check : likesList) {
//            if (Objects.equals(check.getUserName(), likes.getUserName())){
//                likesRepository.deleteById(check.getId());
//                ch = true;
//                break;
//            }
//        }

//        Posts post = postsRepository.findById(likes.getPostId()).orElseThrow(() -> new IllegalArgumentException("해당하는 ID가 없습니다."));
//        if (ch ){
//            if(post.getJoinNum() >= post.getPartyNum()){
//                throw new IllegalArgumentException("참여가능인원이 모두 모집되었습니다.");
//            }else{
//                likesRepository.save(likes);
//            }
//        }
//    }
        boolean exists = likesRepository.existsByPostIdAndUserName(likeDto.getPostId(), likeDto.getUserName());
        if (exists) {
            postsService.minuslikecnt(likeDto.getPostId());
            likesRepository.deleteByPostIdAndUserName(likeDto.getPostId(), likeDto.getUserName());
        } else {
            postsService.pluslikecnt(likeDto.getPostId());
            likesRepository.save(likes);
        }
    }
}





//    @Transactional
//    public List<Likes> findLikes(Long postId) {
//        List<Likes> likes = likesRepository.findAllByPostId(postId);
//        return likes;
//    }
//}
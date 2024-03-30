package com.nawabali.nawabali.service;

import com.nawabali.nawabali.constant.Address;
import com.nawabali.nawabali.constant.Town;
import com.nawabali.nawabali.domain.Post;
import com.nawabali.nawabali.domain.User;
import com.nawabali.nawabali.domain.image.PostImage;
import com.nawabali.nawabali.dto.PostDto;
import com.nawabali.nawabali.exception.CustomException;
import com.nawabali.nawabali.exception.ErrorCode;
import com.nawabali.nawabali.repository.PostRepository;
import com.nawabali.nawabali.s3.AwsS3Service;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final AwsS3Service awsS3Service;

    // 게시물 생성
    @Transactional
    public PostDto.ResponseDto createPost(User user, PostDto.RequestDto requestDto, List<MultipartFile> files) {
        User findUser = userService.getUserId(user.getId());

        Town town = new Town(
                requestDto.getLatitude(),
                requestDto.getLongitude()
        );

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .category(requestDto.getCategory())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .town(town)
                .user(findUser)
                .build();

        List<String> imageUrls = awsS3Service.uploadFile(files, "postImages");
        imageUrls.forEach(url -> {
            PostImage image = new PostImage();
            image.setFileName(url);
            image.setImgUrl(url);
            post.addImage(image);
        });

        postRepository.save(post);

        return new PostDto.ResponseDto(post);

    }

    // 전체 게시물 조회 - 무한 스크롤


    // 게시물 조회
    public PostDto.ResponseDto getPost(Long postId) {
        Post post = getPostId(postId);
        return new PostDto.ResponseDto(post);
    }

    // 게시물 수정 - 사용자 신원 확인
    @Transactional
    public PostDto.ResponseDto updatePost(Long postId, User user, PostDto.PatchDto patchDto) {
        Post post = getPostId(postId);
        if(!post.getUser().getId().equals(user.getId())){
            throw new CustomException(ErrorCode.FORBIDDEN_MEMBER);
        }

        Town town = new Town(patchDto.getLatitude(), patchDto.getLongitude());

        post.update(patchDto.getTitle(),
                patchDto.getContents(),
                patchDto.getCategory(),
                town);

        return new PostDto.ResponseDto(post);
    }


    // 게시물 삭제
    @Transactional
    public PostDto.DeleteDto deletePost(Long postId) {
        postRepository.deleteById(postId);
        return new PostDto.DeleteDto("해당 게시물이 삭제되었습니다.");
    }

    public Post getPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_POST));

    }


}
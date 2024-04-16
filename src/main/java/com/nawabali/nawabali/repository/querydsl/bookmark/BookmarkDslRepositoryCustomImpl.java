package com.nawabali.nawabali.repository.querydsl.bookmark;

import com.nawabali.nawabali.domain.*;
import com.nawabali.nawabali.domain.image.PostImage;
import com.nawabali.nawabali.dto.querydsl.BookmarkDslDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookmarkDslRepositoryCustomImpl implements BookmarkDslRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public Slice<BookmarkDslDto.UserBookmarkDto> getUserBookmarks(User user, Pageable pageable) {
        QBookMark bookMark = QBookMark.bookMark;
        QPost post = QPost.post;
        QUser quser = QUser.user;

        List<BookMark> bookmarks = queryFactory
                .selectFrom(bookMark)
                .where(bookMark.user.id.eq(user.getId()))
                .join(bookMark.post, post).fetchJoin()
                .join(post.user ,quser).fetchJoin()
                .orderBy(bookMark.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        boolean hasNext = bookmarks.size() > pageable.getPageSize();
        if(hasNext) {
            bookmarks.remove(bookmarks.size() - 1);
        }

        List<BookmarkDslDto.UserBookmarkDto> responseDtos = convertBookmarkDto(bookmarks);
//        bookmarks.stream()
//                .map(newBookmark -> BookmarkDslDto.UserBookmarkDto.builder()
//                        .userId(newBookmark.getUser().getId())
//                        .postId(newBookmark.getPost().getId())
//                        .nickname(newBookmark.getUser().getNickname())
//                        .contents(newBookmark.getPost().getContents())
//                        .category(newBookmark.getPost().getCategory().name())
//                        .district(newBookmark.getPost().getTown().getDistrict())
//                        .latitude(newBookmark.getPost().getTown().getLatitude())
//                        .longitude(newBookmark.getPost().getTown().getLongitude())
//                        .createdAt(newBookmark.getCreatedAt())
//                        .imageUrls(newBookmark.getPost().getImages().stream().map(PostImage::getImgUrl).collect(Collectors.toList()))
//                        .commentCount(newBookmark.getPost().getComments().size())
//                        .build())
//                .collect(Collectors.toList());

        return new SliceImpl<>(responseDtos, pageable, hasNext);
    }

    private  List<BookmarkDslDto.UserBookmarkDto> convertBookmarkDto(List<BookMark> bookMarks) {
        return bookMarks.stream()
                .map(bookMark -> BookmarkDslDto.UserBookmarkDto.builder()
                        .userId(bookMark.getUser().getId())
                        .postId(bookMark.getPost().getId())
                        .nickname(bookMark.getUser().getNickname())
                        .contents(bookMark.getPost().getContents())
                        .category(bookMark.getPost().getCategory().name())
                        .district(bookMark.getPost().getTown().getDistrict())
                        .latitude(bookMark.getPost().getTown().getLatitude())
                        .longitude(bookMark.getPost().getTown().getLongitude())
                        .createdAt(bookMark.getCreatedAt())
                        .imageUrls(bookMark.getPost().getImages().stream().map(PostImage::getImgUrl).collect(Collectors.toList()))
                        .commentCount(bookMark.getPost().getComments().size())
                        .build())
                .collect(Collectors.toList());
    }

}

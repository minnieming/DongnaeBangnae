package com.nawabali.nawabali.repository.dslrepository;

import com.nawabali.nawabali.domain.Post;
import com.nawabali.nawabali.dto.dslDto.PostDslDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostDslRepositoryCustom {

    Slice<PostDslDto.ResponseDto> findPostsByLatest(Pageable pageable);

}
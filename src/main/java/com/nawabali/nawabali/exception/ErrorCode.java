package com.nawabali.nawabali.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 400 BAD_REQUEST: 잘못된 요청
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    WRONG_USERNAME(BAD_REQUEST, "아이디가 잘못 입력되었습니다."),
    WRONG_PASSWORD(UNAUTHORIZED, "비밀번호가 잘못 입력되었습니다."),
    MISMATCH_PASSWORD(BAD_REQUEST,"두 비밀번호가 일치하지 않습니다."),
    UNVERIFIED_EMAIL(BAD_REQUEST, "이메일 인증이 완료되지 않았습니다."),
    WRONG_MULTIPARTFILE(BAD_REQUEST, "Multipartfile에 문제가 있습니다"),
    WRONG_DTO(BAD_REQUEST,"DTO를 다시 확인해주세요"),
    MISMATCH_ID(BAD_REQUEST,"잘못된 요청입니다."),
    MISMATCH_ADDRESS(BAD_REQUEST, "동네인증 좋아요는 해당 지역의 회원만 누를 수 있습니다."),
    MAX_UPLOAD_PHOTO(BAD_REQUEST, "최대 5장의 사진만 업로드할 수 있습니다."),
    WRONG_OTHERUSER(BAD_REQUEST, "올바른 상대방의 닉네임을 넣어주세요"),
    INVALID_CITY_NAME(BAD_REQUEST, "올바른 도시명을 입력해주세요."),
    INVALID_DISTRICT_NAME(BAD_REQUEST,"올바른 구 이름을 입력해주세요."),

    // 401 UNAUTHORIZED: 인증되지 않은 사용자
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "권한 정보가 없는 회원입니다."),
    UNAUTHORIZED_POST(UNAUTHORIZED, "권한 정보가 없는 게시물입니다."),
    UNAUTHORIZED_COMMENT(UNAUTHORIZED, "권한 정보가 없는 댓글입니다."),
    UNAUTHORIZED_BOOKMARK(UNAUTHORIZED, "권한 정보가 없는 북마크입니다."),
    UNAUTHORIZED_CATEGORY(UNAUTHORIZED, "권한 정보가 없는 카테고리입니다."),
    INVALID_JWT_SIGNATURE(UNAUTHORIZED,"유효하지 않은 JWT 서명입니다."),
    EXPIRED_JWT(UNAUTHORIZED,"만료된 TOKEN 입니다." ),
    UNSUPPORTED_JWT(UNAUTHORIZED,"지원되지 않는 JWT 토큰 입니다." ),
    INVALID_JWT_VALUE(UNAUTHORIZED,"잘못된 JWT 토큰입니다." ),

    // 403 FORBIDDEN : 클라이언트는 콘텐츠에 접근할 권리를 가지고 있지 않다
    FORBIDDEN_MEMBER(FORBIDDEN,"본인의 게시물이 아닙니다."),
    FORBIDDEN_CHATMESSAGE(FORBIDDEN, "본인의 채팅이 아닙니다."),

    // 404 NOT_FOUND: 잘못된 리소스 접근
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 회원 정보를 찾을 수 없습니다."),
    POST_NOT_FOUND(NOT_FOUND, "해당 게시물 정보를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(NOT_FOUND, "해당 댓글 정보를 찾을 수 없습니다."),
    USER_NOT_FOUND(NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    PROFILEIMAGE_NOT_FOUND(NOT_FOUND, "해당 프로필이미지를 찾을 수 없습니다."),
    LIKE_NOT_FOUND(NOT_FOUND, "해당 좋아요를 찾을 수 없습니다."),
    CHATROOM_NOT_FOUND(NOT_FOUND, "해당 채팅방을 찾을 수 없습니다."),
    INCHATROOM_NOT_FOUND(NOT_FOUND, "속해있는 채팅방이 없습니다."),
    CHAT_MESSAGE_NOT_FOUND(NOT_FOUND, "해당 메세지를 찾을 수 없습니다."),
    NOTIFICATION_NOT_FOUND(NOT_FOUND, "해당 알림을 찾을 수 없습니다."),
    DISTRICTPOST_NOT_FOUND(NOT_FOUND, "해당 구의 총 게시물 수를 찾을 수 없습니다."),
    DISTRICTLIKE_NOT_FOUND(NOT_FOUND, "해당 구의 총 좋아요 수를 찾을 수 없습니다."),
    DISTRICTLOCALLIKE_NOT_FOUND(NOT_FOUND, "해당 구의 총 동네인증 수를 찾을 수 없습니다."),
    DISTRICTCATEGORY_NOT_FOUND(NOT_FOUND, "해당 구의 가장 많은 카테고리를 찾을 수 없습니다."),
    RANK_NOT_FOUND(NOT_FOUND, "해당 사용자의 회원등급을 찾을 수 없습니다."),
    PERIOD_NOT_FOUND(NOT_FOUND, "해당 기간을 찾을 수 없습니다(일주일 또는 한달만 가능합니다)."),

    // 409 CONFLICT: 중복된 리소스 (요청이 현재 서버 상태와 충돌될 때)
    DUPLICATE_EMAIL(CONFLICT, "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(CONFLICT, "이미 존재하는 닉네임입니다."),
    DUPLICATE_LIKE_TRUE(CONFLICT, "이미 좋아요 되어있습니다."),
    DUPLICATE_LIKE_FALSE(CONFLICT, "이미 좋아요가 취소 되어있습니다."),
    DUPLICATE_BOOKMARK(CONFLICT, "이미 등록된 북마크 입니다."),
    DUPLICATE_CHATROOM(CONFLICT, "이미 존재하는 채팅방 입니다."),


    // 500 SERVER ERROR
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "내부 서버 에러입니다."),
    PHOTO_UPLOAD_ERROR(INTERNAL_SERVER_ERROR, "이미지 파일 업로드에 실패했습니다.");


    private final HttpStatus httpStatus;
    private final String message;

}

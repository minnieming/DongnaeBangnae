package com.nawabali.nawabali.domain;

import com.nawabali.nawabali.dto.ChatDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

public class Chat {

    @Entity
    @Getter
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "chatMessage")
    @Slf4j(topic = "chat 로그")
    public static class ChatMessage {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;

        @Column (nullable = false)
        private ChatDto.ChatMessageDto.MessageType type; // 메세지 타입

        @Column (nullable = false)
        private String sender; // 메시지 보낸사람

        @Column (nullable = false)
        private String message; // 메시지

        @Column (nullable = false)
        private LocalDateTime createdAt;

        @ManyToOne
        @JoinColumn (name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn (name = "room_id")
        private ChatRoom chatRoom;

        private ChatMessage (User user) {
            this.sender = user.getNickname();
        }

    }

    @Entity
    @Getter
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "chatRoom")
    @Slf4j(topic = "chat 로그")
    public static class ChatRoom {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;

        @Column(nullable = false)
        private String roomNumber;

        @Column (nullable = false)
        private String name;


//        public ChatRoom () {
//            this.Id = UUID.randomUUID().toString();
//        }
    }
}
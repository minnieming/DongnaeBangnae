package com.nawabali.nawabali.repository;

import com.nawabali.nawabali.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository <Notification, Long> {
    Optional<List<Notification>> findAllByUserId(Long userId);

    Optional<List<Notification>> findAllByReceiver(String nickname);
}

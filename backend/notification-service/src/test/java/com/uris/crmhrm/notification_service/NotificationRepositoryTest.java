package com.uris.crmhrm.notification_service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void persistsNotificationWithReceivedTimestamp() {
        Notification saved = notificationRepository.save(new Notification("Kreiran je zadatak 7", 7L));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getReceivedAt()).isNotNull();
        assertThat(notificationRepository.findAll()).hasSize(1);
    }
}

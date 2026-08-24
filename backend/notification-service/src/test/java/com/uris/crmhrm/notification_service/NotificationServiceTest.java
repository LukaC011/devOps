package com.uris.crmhrm.notification_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void returnsAllNotifications() {
        given(notificationRepository.findAll())
                .willReturn(List.of(new Notification("Kreiran je zadatak 1", 1L)));

        assertThat(notificationService.findAll()).hasSize(1);
    }

    @Test
    void recordsNotificationWithTaskReference() {
        given(notificationRepository.save(any(Notification.class))).willAnswer(call -> call.getArgument(0));

        Notification recorded = notificationService.record("Kreiran je zadatak 9", 9L);

        assertThat(recorded.getMessage()).isEqualTo("Kreiran je zadatak 9");
        assertThat(recorded.getTaskId()).isEqualTo(9L);
        assertThat(recorded.getReceivedAt()).isNotNull();
    }
}

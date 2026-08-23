package com.uris.crmhrm.notification_service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskCreatedListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TaskCreatedListener taskCreatedListener;

    @Test
    void storesNotificationForReceivedEvent() {
        given(notificationService.record(eq("Kreiran je zadatak: Pripremi ponudu"), eq(5L)))
                .willReturn(new Notification("Kreiran je zadatak: Pripremi ponudu", 5L));

        taskCreatedListener.onTaskCreated(new TaskCreatedEvent(5L, "Pripremi ponudu", 1L));

        verify(notificationService).record("Kreiran je zadatak: Pripremi ponudu", 5L);
    }
}

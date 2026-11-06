package team.projectpulse.notification.dto;

import team.projectpulse.notification.SubmittableItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @param sent   how many reminders the mail server accepted; 0 with no failures means nobody owed the item
 * @param failed the students who could not be reached
 */
public record ReminderResultDto(SubmittableItem item,
                                String week,
                                int sent,
                                List<OwingStudentDto> failed) {

    public ReminderResultDto {
        // Snapshot collection structure while preserving nullable API values.
        failed = failed == null ? null : Collections.unmodifiableList(new ArrayList<>(failed));
    }
}

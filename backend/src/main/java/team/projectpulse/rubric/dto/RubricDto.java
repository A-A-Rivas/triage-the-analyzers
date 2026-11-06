package team.projectpulse.rubric.dto;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public record RubricDto(
        Integer rubricId,
        String rubricName,
        Set<CriterionDto> criteria,
        Integer courseId) {

    public RubricDto {
        // Snapshot collection structure while preserving nullable API values.
        criteria = criteria == null ? null : Collections.unmodifiableSet(new LinkedHashSet<>(criteria));
    }
}

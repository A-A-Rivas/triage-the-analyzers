package team.projectpulse.ram.usecase.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record UseCaseMainStepDto(
        Long id,
        String actor,
        String actionText,
        List<UseCaseExtensionDto> extensions) {

    public UseCaseMainStepDto {
        // Snapshot collection structure while preserving nullable API values.
        extensions = extensions == null ? null : Collections.unmodifiableList(new ArrayList<>(extensions));
    }
}

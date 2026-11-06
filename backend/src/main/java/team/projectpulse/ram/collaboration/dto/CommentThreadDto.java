package team.projectpulse.ram.collaboration.dto;

import team.projectpulse.ram.collaboration.CommentThreadStatus;
import team.projectpulse.user.dto.PeerEvaluationUserDto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record CommentThreadDto(Long id,
                               CommentThreadStatus status,
                               Instant createdAt,
                               PeerEvaluationUserDto createdBy,
                               List<CommentDto> comments) {

    public CommentThreadDto {
        // Snapshot collection structure while preserving nullable API values.
        comments = comments == null ? null : Collections.unmodifiableList(new ArrayList<>(comments));
    }
}

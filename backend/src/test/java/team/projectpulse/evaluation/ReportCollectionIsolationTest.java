package team.projectpulse.evaluation;

import org.junit.jupiter.api.Test;
import team.projectpulse.ram.document.template.DocumentTemplate;
import team.projectpulse.ram.requirement.dto.ArtifactLinkViewDto;
import team.projectpulse.ram.requirement.dto.ArtifactTraceability;
import team.projectpulse.rubric.dto.CriterionDto;
import team.projectpulse.rubric.dto.RubricDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReportCollectionIsolationTest {

    @Test
    void templateSectionsRemainNullableAndDoNotShareTheInputList() {
        DocumentTemplate template = new DocumentTemplate();
        DocumentTemplate.SectionTemplate section = new DocumentTemplate.SectionTemplate();
        List<DocumentTemplate.SectionTemplate> sections = new ArrayList<>(List.of(section));
        template.setSections(sections);
        sections.clear();

        assertThat(template.getSections()).containsExactly(section);
        assertThatThrownBy(() -> template.getSections().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        template.setSections(null);
        assertThat(template.getSections()).isNull();
    }

    @Test
    void reportCommentsPreserveNullsWithoutSharingTheCallersList() {
        List<String> comments = new ArrayList<>(Arrays.asList("Helpful", null));
        PeerEvaluationAverage average = new PeerEvaluationAverage();
        average.setPublicComments(comments);
        comments.clear();

        assertThat(average.getPublicComments()).containsExactly("Helpful", null);
        assertThatThrownBy(() -> average.getPublicComments().clear())
                .isInstanceOf(UnsupportedOperationException.class);

        average.setPrivateComments(null);
        assertThat(average.getPrivateComments()).isNull();
    }

    @Test
    void weeklyReportCopiesTheAssembledResults() {
        PeerEvaluationAverage average = new PeerEvaluationAverage();
        List<PeerEvaluationAverage> averages = new ArrayList<>(List.of(average));
        WeeklyPeerEvaluationReport report = new WeeklyPeerEvaluationReport();
        report.setPeerEvaluationAverages(averages);
        averages.clear();

        assertThat(report.getPeerEvaluationAverages()).containsExactly(average);
        assertThatThrownBy(() -> report.getPeerEvaluationAverages().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void listRecordPreservesNullElementsAndNullableCollections() {
        List<ArtifactLinkViewDto> incoming = new ArrayList<>();
        incoming.add(null);
        ArtifactTraceability traceability = new ArtifactTraceability(1L, incoming, null);
        incoming.clear();

        assertThat(traceability.incoming()).hasSize(1).containsNull();
        assertThat(traceability.outgoing()).isNull();
        assertThatThrownBy(() -> traceability.incoming().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void setRecordCopiesItsInputAndPreservesNullElements() {
        Set<CriterionDto> criteria = new LinkedHashSet<>();
        criteria.add(null);
        RubricDto rubric = new RubricDto(1, "Review", criteria, 2);
        criteria.clear();

        assertThat(rubric.criteria()).hasSize(1).containsNull();
        assertThatThrownBy(() -> rubric.criteria().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThat(new RubricDto(1, "Review", null, 2).criteria()).isNull();
    }
}

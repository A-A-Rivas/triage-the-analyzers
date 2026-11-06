package team.projectpulse.ram.document;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.util.Locale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RequirementDocumentSpecsTest {

    @Test
    @ResourceLock("java.util.Locale.default")
    @SuppressWarnings("unchecked")
    void titleSearchUsesTheSamePatternUnderTurkishAndEnglishLocales() {
        Root<RequirementDocument> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        Path<String> title = mock(Path.class);
        Expression<String> lowerTitle = mock(Expression.class);
        when(root.<String>get("title")).thenReturn(title);
        when(builder.lower(title)).thenReturn(lowerTitle);

        Locale original = Locale.getDefault();
        try {
            for (Locale locale : new Locale[]{Locale.ENGLISH, Locale.forLanguageTag("tr-TR")}) {
                Locale.setDefault(locale);
                RequirementDocumentSpecs.hasTitleLike("INTERFACE").toPredicate(root, query, builder);
            }
            verify(builder, org.mockito.Mockito.times(2)).like(lowerTitle, "%interface%");
        } finally {
            Locale.setDefault(original);
        }
    }
}

package czar.evaluaciones.repositories.test;

import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;

import czar.evaluaciones.repositories.impl.EvaluationAnswerCustomRepositoryImpl;

public class EvaluationAnswerCustomRepositoryTest {

    private EvaluationAnswerCustomRepositoryImpl evaluationAnswerCustomRepositoryImpl;

    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Before
    public void init() {
        em = mock(EntityManager.class);
        evaluationAnswerCustomRepositoryImpl = new EvaluationAnswerCustomRepositoryImpl();
        evaluationAnswerCustomRepositoryImpl.setEntityManager(em);

        Query query = mock(Query.class);
        when(query.setParameter(anyString(), anySetOf(Long.class))).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1.0F);
        when(em.createQuery(anyString())).thenReturn(query);
        TypedQuery<String> typedQuery = mock(TypedQuery.class);
        when(typedQuery.setParameter(anyString(), anySetOf(Long.class))).thenReturn(typedQuery);
        when(em.createQuery(anyString(), eq(String.class))).thenReturn(typedQuery);
        
        when(em.createNativeQuery(anyString())).thenReturn(query);
    }

    @Test
    public void deleteAnswersTest() {
        Set<Long> idsEvalQuestion = new HashSet<>();
        
        evaluationAnswerCustomRepositoryImpl.deleteAnswers(idsEvalQuestion);
        
        verify(em, atLeastOnce()).createQuery(anyString());
    }
    
    @Test
    public void updateAnswersCorrectTest() {
        Long idEvaluation = 1L;
        
        evaluationAnswerCustomRepositoryImpl.updateAnswersCorrect(idEvaluation);
        
        verify(em, atLeastOnce()).createQuery(anyString());
    }
    
    @Test
    public void loadAnswersTest() {
        Set<Long> idsEvalQuestion = new HashSet<>();
        
        evaluationAnswerCustomRepositoryImpl.loadAnswers(idsEvalQuestion);
        
        verify(em, atLeastOnce()).createQuery(anyString(), eq(String.class));
    }
    
    @Test
    public void getResultTest() {
        Long idEvaluation = 1L;
        
        evaluationAnswerCustomRepositoryImpl.getResult(idEvaluation);
    }
}

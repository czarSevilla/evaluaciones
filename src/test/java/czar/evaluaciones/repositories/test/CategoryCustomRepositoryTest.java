package czar.evaluaciones.repositories.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

import czar.evaluaciones.repositories.impl.CategoryCustomRepositoryImpl;

public class CategoryCustomRepositoryTest {

	private CategoryCustomRepositoryImpl categoryCustomRepositoryImpl;
	
	private EntityManager em;
	
	@Before
	public void init() {
		em = mock(EntityManager.class);
		categoryCustomRepositoryImpl = new CategoryCustomRepositoryImpl();
		categoryCustomRepositoryImpl.setEntityManager(em);
		
		Query query = mock(Query.class);
		when(query.getSingleResult()).thenReturn(new BigInteger("1"));
		List<Object[]> rows = new ArrayList<>();
		rows.add(new Object[]{"category", BigDecimal.ONE});
		when(query.getResultList()).thenReturn(rows);
		
		when(em.createNativeQuery(anyString())).thenReturn(query);
	}
	
	@Test
	public void getMinCountCategoryTest() {
		
	    categoryCustomRepositoryImpl.getMinCountCategory();
	    
	    verify(em, atLeastOnce()).createNativeQuery(anyString());
	}
	
	@Test
	public void getMaxCountCategoryTest() {
	    
	    categoryCustomRepositoryImpl.getMaxCountCategory();
        
        verify(em, atLeastOnce()).createNativeQuery(anyString());
	}
	
	@Test
	public void getCategoryCloudTest() {
	    
	    categoryCustomRepositoryImpl.getCategoryCloud(1, 10, 100);
	    
	    verify(em, atLeastOnce()).createNativeQuery(anyString());
	}
}

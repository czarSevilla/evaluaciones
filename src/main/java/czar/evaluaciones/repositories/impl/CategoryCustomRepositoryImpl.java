package czar.evaluaciones.repositories.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import czar.evaluaciones.repositories.CategoryCustomRepository;

@Repository("categoryCustomRepository")
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {

	
    private EntityManager em;
	
    @PersistenceContext
	public void setEntityManager(EntityManager pEm) {
		this.em = pEm;
	}
	
	@Override
	public int getMinCountCategory() {
		String query = "select min(a.total) total from (select count(*) total from ev_questions_categories group by id_category) a";
		return ((BigInteger) em.createNativeQuery(query).getSingleResult()).intValue();
	}

	@Override
	public int getMaxCountCategory() {
		String query = "select max(a.total) total from (select count(*) total from ev_questions_categories group by id_category) a";
		return ((BigInteger) em.createNativeQuery(query).getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getCategoryCloud(int min, int max, int size) {
		StringBuilder sb = new StringBuilder();
        sb.append("select t2.description, "); 
        sb.append("case when t.total > :minCount then ceil((:maxSize * (t.total - :minCount)) / (:maxCount - :minCount)) else 1 end strength ");
        sb.append("from (select id_category, count(*) total from ev_questions_categories group by id_category) t ");
        sb.append("left join ev_categories t2 on t.id_category = t2.id_category ");
        
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("minCount", min);
        q.setParameter("maxSize", size);
        q.setParameter("maxCount", max);
        
        List<Object[]> lst = q.getResultList();
        Map<String, Integer> map = new HashMap<>();
        for (Object[] row : lst) {
            map.put((String) row[0], ((BigDecimal)row[1]).intValue());
        }
        return map;   
	}

}

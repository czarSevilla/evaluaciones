package czar.evaluaciones.repositories;

import java.util.Map;

public interface CategoryCustomRepository {
	int getMinCountCategory();
	int getMaxCountCategory();
	Map<String, Integer> getCategoryCloud(int min, int max, int size);
}

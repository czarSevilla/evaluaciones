package czar.evaluaciones.services;

import java.util.List;

import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.exceptions.ServiceException;

public interface ConfigService {

	List<Configuration> findAll();
	
	Configuration findById(Long idConfiguration);
	
	void save(Configuration configuration) throws ServiceException;
}

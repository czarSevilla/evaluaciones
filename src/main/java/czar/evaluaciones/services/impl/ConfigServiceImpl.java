package czar.evaluaciones.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.services.ConfigService;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Override
	public List<Configuration> findAll() {
		return configurationRepository.findAll();
	}

	@Override
	public Configuration findById(Long idConfiguration) {
		return configurationRepository.findOne(idConfiguration);
	}
	
	@Override
     public Configuration findByKey(String key) {
          return configurationRepository.findByKey(key);
     }

	@Override
	public void save(Configuration configuration) throws ServiceException {
		try {
			configurationRepository.save(configuration);
		} catch (DataIntegrityViolationException dve) {
    		throw new ServiceException("No fue posible actualizar el par\u00E1metro. Favor de revisar el log del sistema.");
    	}
		
	}

}

package czar.evaluaciones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.Configuration;

@Repository("configurationRepository")
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

    Configuration findByKey(String key);
}

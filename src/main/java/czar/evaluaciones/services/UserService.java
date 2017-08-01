package czar.evaluaciones.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.exceptions.ServiceException;

public interface UserService extends UserDetailsService {
    
	void save(UserDto user) throws ServiceException;
    
    UserDto find(UserDto userDto);
    
    UserDto getById(Long idUser);
    
    void delete(Long idUser) throws ServiceException;
    
    void changePassword(UserDto user) throws ServiceException;
    
    void resetPassword(Long idUser, Long idAdmin) throws ServiceException;
    
    List<UserDto> findByActiveRoles(String... roles);
}
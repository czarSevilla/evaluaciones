package czar.evaluaciones.services.impl;

import static czar.evaluaciones.enums.Config.EXPIRATION_DAY;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.entities.Notification;
import czar.evaluaciones.entities.User;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.enums.NotificationStatus;
import czar.evaluaciones.enums.NotificationType;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.AuthorityRepository;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.NotificationRepository;
import czar.evaluaciones.repositories.UserRepository;
import czar.evaluaciones.services.UserService;
import czar.evaluaciones.utils.DateUtils;
import czar.evaluaciones.utils.SecurityUtils;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ConfigurationRepository configurationRepository;
    
    @Autowired
    private AuthorityRepository authorityRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    private static final String INVALID_MSG = "El password debe tener una longitud de 8 a 20 caracteres, incluir al menos un n\u00FAmero, una letra may\u00FAscula y un caracter especial @#$%";
    
    private static final String NOT_MATCH = "El password no coincide con la verificaci\u00F3n";
    
    private static final String EMAIL_EXISTS = "Ya existe un usuario con el email proporcionado";

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	@Override
	public UserDto find(UserDto form) {
		String dirStr = Direction.DESC.toString();
        int numPage = 0;
        int size = Integer.parseInt(configurationRepository.findByKey(Config.ROWS_X_PAGE.toString()).getValue());
        if (form.getDirection() != null) {
            dirStr = form.getDirection();
        }
        if (form.getPage() > 0) {
            numPage = form.getPage();
        }
        if (form.getSize() > 0) {
            size = form.getSize();
        }
        Direction direction = Direction.valueOf(dirStr);
        Sort sort = new Sort(direction, "idUser");
        Pageable pageable = new PageRequest(numPage, size, sort);
        Page<User> page = null;
        if (form.getName() == null || StringUtils.isEmpty(form.getName())) {
            page = userRepository.findAll(pageable);
        } else {
            page = userRepository.findByNameContainingIgnoreCase(form.getName(), pageable);
        }
        UserDto result = new UserDto();
        result.setUsers(page.getContent());
        result.setName(form.getName());
        result.setDirection(dirStr);
        result.setPage(page.getNumber());
        result.setSize(page.getSize());
        result.setTotalPages(page.getTotalPages());
        result.setTotalItems(page.getTotalElements());
        return result;
	}

	@Override
	public UserDto getById(Long idUser) {
		User user = userRepository.findOne(idUser);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		userDto.setPassword(null);
		userDto.setAuthority(user.getAuthorities().iterator().next().getAuthority());
		return userDto;
	}

	@Override
	public void delete(Long idUser) throws ServiceException {
		try {
			userRepository.delete(idUser);
		} catch (DataIntegrityViolationException dve) {
            throw new ServiceException("El registro tiene dependencias que impiden su eliminaci\u00F3n");
        }
	}

	@Override
	public void save(UserDto userDto) throws ServiceException {
		try {
			User user = null;
			Set<String> authorities = new HashSet<>();
			authorities.add(userDto.getAuthority());
			if (userDto.getIdUser() != null) {
				user = userRepository.findOne(userDto.getIdUser());
				authorityRepository.deleteInBatch(user.getAuthorities());
				user.setActive(userDto.isActive());
				user.setAuthorities(new HashSet<>());
				user.setEmail(userDto.getEmail());
				user.setExpirationDate(userDto.getExpirationDate());
				user.setLocked(userDto.isLocked());
				user.setName(userDto.getName());
			} else {
				user = new User();
				if (userDto.getPassword().compareTo(userDto.getVerifyPassword()) != 0) {
					throw new ServiceException(NOT_MATCH);
				}
				if (!SecurityUtils.isValidPassword(userDto.getPassword())) {
					throw new ServiceException(INVALID_MSG);
				}
				User userByEmail = userRepository.findOneByEmail(userDto.getEmail());
				if (userByEmail != null) {
				   throw new ServiceException(EMAIL_EXISTS);
				}
				BeanUtils.copyProperties(userDto, user);
				user.setActive(true);
				user.setLocked(false);
		    	String encodePass = passwordEncoder.encode(userDto.getPassword());
		        user.setPassword(encodePass);
			}
            for (String auth : authorities) {
                user.addAuthority(auth);
            }
	        userRepository.save(user);
        } catch (DataIntegrityViolationException dve) {
            throw new ServiceException("Ya existe un registro con el mismo nombre de usuario o correo electr\u00F3nico");
        }
	}

	@Override
	public void changePassword(UserDto userDto) throws ServiceException {
		User user = userRepository.findOne(userDto.getIdUser());
		if (!passwordEncoder.matches(userDto.getCurrentPassword(), user.getPassword())) {
			throw new ServiceException("El password actual es inv\u00E1lido");
		}
		if (userDto.getPassword().compareTo(userDto.getVerifyPassword()) != 0) {
			throw new ServiceException(NOT_MATCH);
		}
		if (!SecurityUtils.isValidPassword(userDto.getPassword())) {
			throw new ServiceException(INVALID_MSG);
		}
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException dve) {
            throw new ServiceException("Ocurrio un error al cambiar el password. Favor reportar al Administrador");
        }
	}

	@Override
	public void resetPassword(Long idUser, Long idAdmin) throws ServiceException {
		try {
			String newPassword = SecurityUtils.generateRandomPassword();
			User user = userRepository.findOne(idUser);
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
						
			int expirationDays = Integer.parseInt(configurationRepository.findByKey(EXPIRATION_DAY.toString()).getValue());
			Date date = DateUtils.addDays(new Date(), expirationDays); 
			
			Notification notification = new Notification();
	        notification.setNameApplicant(user.getName());
	        notification.setEmailApplicant(user.getEmail());
	        notification.setPasswordApplicant(newPassword);
	        User manager = new User();
	        manager.setIdUser(idAdmin);
	        notification.setManager(manager);
	        notification.setStatus(NotificationStatus.REGISTRADA);
	        notification.setExpirationDate(date);
	        notification.setType(NotificationType.RESET);
	        notificationRepository.save(notification);
	        
		} catch (DataIntegrityViolationException dve) {
            throw new ServiceException("Ocurrio un error al reiniciar el password. Favor reportar al Administrador");
        }
	}

	@Override
	public List<UserDto> findByActiveRoles(String... roles) {
		Set<User> users = userRepository.findByActiveRoles(roles);
		return users.stream().map(u -> new UserDto(u.getIdUser(), u.getName())).collect(Collectors.toList());
	}
}

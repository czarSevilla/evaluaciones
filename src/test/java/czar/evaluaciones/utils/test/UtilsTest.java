package czar.evaluaciones.utils.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.security.Principal;

import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import czar.evaluaciones.entities.User;
import czar.evaluaciones.utils.DateUtils;
import czar.evaluaciones.utils.RoleUtils;
import czar.evaluaciones.utils.SecurityUtils;

public class UtilsTest {

	@Test
	public void dateUtilsTest() throws Exception {
		
		Constructor<DateUtils> constructor;
		constructor = DateUtils.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		
		DateUtils instance = constructor.newInstance();
		
		assertThat(instance, notNullValue());
	}
	
	@Test
	public void roleUtilsTest() throws Exception {
		Constructor<RoleUtils> constructor;
		constructor = RoleUtils.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		
		RoleUtils instance = constructor.newInstance();
		
		assertThat(instance, notNullValue());
	}
	
	@Test
	public void securityUtilsTest() throws Exception {
		Constructor<SecurityUtils> constructor;
		constructor = SecurityUtils.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		
		SecurityUtils instance = constructor.newInstance();
		
		assertThat(instance, notNullValue());
	}
	
	@Test
	public void hasRoleTest() {
		Principal principal = mock(UsernamePasswordAuthenticationToken.class);
		
		boolean result = SecurityUtils.hasRole("USER", principal);
		
		assertFalse(result);
	}
	
	@Test
	public void hasRoleEa1Test() {
		User user = new User();
		user.addAuthority("ROLE_USER");
		Principal principal = mock(UsernamePasswordAuthenticationToken.class);
		when(((UsernamePasswordAuthenticationToken) principal).getPrincipal()).thenReturn(user);
		
		boolean result = SecurityUtils.hasRole("ROLE_USER", principal);
		
		assertTrue(result);
	}
	
	@Test
    public void hasRoleEa2Test() {
        User user = new User();
        Principal principal = mock(UsernamePasswordAuthenticationToken.class);
        when(((UsernamePasswordAuthenticationToken) principal).getPrincipal()).thenReturn(user);
        
        boolean result = SecurityUtils.hasRole("ROLE_USER", principal);
        
        assertFalse(result);
    }
	
	@Test
	public void humanizeTest() {
		
		String result = RoleUtils.humanize("ROLE_MANAGER");
		
		assertThat(result, equalTo("Evaluador"));
	}
	
	@Test
	public void humanizeEa1Test() {
		
		String result = RoleUtils.humanize("ROLE_ADMIN");
		
		assertThat(result, equalTo("Administrador"));
	}
	
	@Test
	public void humanizeEa2Test() {
		
		String result = RoleUtils.humanize("ROLE_USER");
		
		assertThat(result, equalTo("Aplicante"));
	}
	
	@Test
	public void humanizeEa3Test() {
		
		String result = RoleUtils.humanize("ROLE_OTHER");
		
		assertThat(result, equalTo("Aplicante"));
	}
}

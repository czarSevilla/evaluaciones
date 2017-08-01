package czar.evaluaciones.utils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import czar.evaluaciones.entities.Authority;
import czar.evaluaciones.entities.User;

public class SecurityUtils {
	
	private static final Pattern pwdPattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");
	
	private static final String CHAR_ARRAY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%&*-0123456789";
    
    private static final int PASSWORD_LENGTH = 6;

    private SecurityUtils() {
        
    }
    
    public static User getUser(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        }
        return null;
    }
    
    public static Long getIdUser(Principal principal) {
        User user = getUser(principal);
        if (user != null) {
            return user.getIdUser();
        }
        return null;
    }
    
    public static boolean hasRole(String role, Principal principal) {
        User user = getUser(principal);
        if (user != null && !user.getAuthorities().isEmpty()) {
            for (Authority auth : user.getAuthorities()) {
                if (auth.getAuthority().equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isValidPassword(String password) {
    	return pwdPattern.matcher(password).matches();
    }
    
    public static String generateRandomPassword() {
    	StringBuilder sb = new StringBuilder();
        List<Integer> idxPassword = new ArrayList<>(PASSWORD_LENGTH);
        ThreadLocalRandom.current().ints(0, CHAR_ARRAY.length()).limit(PASSWORD_LENGTH).forEach(idxPassword::add);
        idxPassword.stream().map(idx -> CHAR_ARRAY.charAt(idx)).forEach(sb::append);
        return sb.toString();
    }
}

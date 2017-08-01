package czar.evaluaciones.utils;

public class RoleUtils {

	private RoleUtils() {
		
	}
	
	public static String humanize(String roleName) {
		if ("ROLE_MANAGER".equals(roleName)) {
			return "Evaluador";
		}
		if ("ROLE_ADMIN".equals(roleName)) {
			return "Administrador";
		}
		return "Aplicante";
	}
}

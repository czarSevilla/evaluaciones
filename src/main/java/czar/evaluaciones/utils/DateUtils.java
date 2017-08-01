package czar.evaluaciones.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

	private DateUtils() {
		
	}
	
	public static Date addMinutes(Date ini, int minutes) {
		LocalDateTime fromDateTime = LocalDateTime.ofInstant(ini.toInstant(), ZoneId.systemDefault());
        return Date.from(fromDateTime.plusMinutes(minutes).atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date addDays(Date ini, int days) {
		LocalDate localDateExpiration = ini.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(days);
        return Date.from(localDateExpiration.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}

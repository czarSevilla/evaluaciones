package czar.evaluaciones;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new Converter<String, Date>() {

			private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			@Override
			public Date convert(String date) {
				try {
					return df.parse(date);
				} catch (ParseException pe) {
					return null;
				}
			}
			
		});
		super.addFormatters(registry);
	}
    
    
}

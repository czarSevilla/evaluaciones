package czar.evaluaciones.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.services.ConfigService;

@Controller
@RequestMapping("/admin/configuracion")
public class ConfigController {

	@Autowired
	private ConfigService configService;
	
    @RequestMapping(value = {"", "/"}, method = {POST, GET})
    public String config(Model model) {
		List<Configuration> configs = configService.findAll();
		model.addAttribute("configs", configs);
        return "admin/config";
    }
    
    @RequestMapping(value = "/editar", method = POST)
    public String edit(Model model, @RequestParam(name = "idConfiguration", required = true) Long idConfiguration) {
		Configuration configuration = configService.findById(idConfiguration);
		model.addAttribute("configuration", configuration);
        return "admin/configEdit";
    }
	
	@RequestMapping(value = "/update", method = POST)
    public String update(Model model, Configuration form, RedirectAttributes redirAttrs) {
		try {
			configService.save(form);
			redirAttrs.addFlashAttribute("message", "Se modific\u00F3 el par\u00E1metro correctamente");
	        return "redirect:/admin/configuracion";
		} catch (ServiceException se) {
			model.addAttribute("errorMessage", se.getMessage());
			model.addAttribute("configuration", form);
			return "admin/configEdit";
		}
    }
}

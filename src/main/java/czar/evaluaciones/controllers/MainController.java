package czar.evaluaciones.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.entities.Evaluation;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.services.EvaluationService;
import czar.evaluaciones.services.UserService;
import czar.evaluaciones.utils.RoleUtils;
import czar.evaluaciones.utils.SecurityUtils;

@Controller
public class MainController {

    @Autowired
    private EvaluationService evaluationService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping({"/", "/index"})
    public String index(Model model, Principal principal) {
        if (SecurityUtils.hasRole("ROLE_ADMIN", principal)) {
            return "admin/index";
        }
        if (SecurityUtils.hasRole("ROLE_MANAGER", principal)) {
            return "manager/index";
        }
        Long idUser = SecurityUtils.getIdUser(principal);
        List<Evaluation> evaluations = evaluationService.findPendingEvaluationsByIdUser(idUser);
        model.addAttribute("evaluations", evaluations);
        return "index";
    }
    
    @RequestMapping(value = "/perfil", method = {GET, POST})
    public String perfil(Model model, Principal principal) {
    	Long idUser = SecurityUtils.getIdUser(principal);
    	UserDto user = userService.getById(idUser);
    	model.addAttribute("user", user);
    	model.addAttribute("roles", RoleUtils.class);
    	return "perfil";
    }
    
    @RequestMapping(value = "/cambiarPassword", method = POST)
    public String cambiarPassword(UserDto form, Principal principal, RedirectAttributes redirAttrs) {
    	try {
    		Long idUser = SecurityUtils.getIdUser(principal);
    		form.setIdUser(idUser);
    		userService.changePassword(form);
    		redirAttrs.addFlashAttribute("message", "El password se cambi\u00F3 correctamente");
    	} catch (ServiceException se) {
    		redirAttrs.addFlashAttribute("errorMessage", se.getMessage());
    	}
    	return "redirect:/perfil";
    }
    
}

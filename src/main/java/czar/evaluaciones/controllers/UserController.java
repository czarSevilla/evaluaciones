package czar.evaluaciones.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.services.UserService;
import czar.evaluaciones.utils.RoleUtils;
import czar.evaluaciones.utils.SecurityUtils;

@Controller
@RequestMapping("/admin/usuarios")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private static final String MESSAGE = "message";
    
    private static final String ERROR_MESSAGE = "errorMessage";
    
    private static final String REDIRECT_LIST = "redirect:/admin/usuarios";
    
    private static final String ADD = "admin/usuarios/add";
    
    private static final String EDIT = "admin/usuarios/edit";

	@RequestMapping(value = {"", "/"}, method = {POST, GET})
    public String list(Model model, UserDto form) {
		UserDto userDto = userService.find(form);
		model.addAttribute("userDto", userDto);
		model.addAttribute("roles", RoleUtils.class);
        return "admin/usuarios/list";
    }
	
	@RequestMapping(value = "/agregar", method = GET)
	public String add(Model model) {
		model.addAttribute("user", new UserDto());
		return ADD;
	}
	
	@RequestMapping(value = "/agregar", method = POST)
	public String save(Model model, UserDto form, RedirectAttributes redirAttrs) {
		try {
			userService.save(form);
			redirAttrs.addFlashAttribute(MESSAGE, "Se agreg\u00F3 el usuario correctamente");
			return REDIRECT_LIST;
		} catch (ServiceException se) {
			model.addAttribute(ERROR_MESSAGE, se.getMessage());
			model.addAttribute("user", form);
			return ADD;
		}
	}
	
	@RequestMapping(value = "/editar", method = POST)
	public String edit(Model model, @RequestParam(name = "idUser", required = true) Long idUser) {
		UserDto userDto = userService.getById(idUser);
		model.addAttribute("user", userDto);
		return EDIT;
	}
	
	@RequestMapping(value = "/update", method = POST)
	public String update(Model model, UserDto user, RedirectAttributes redirAttr) {
		try {
			userService.save(user);
			redirAttr.addFlashAttribute(MESSAGE, "Se modific\u00F3 el usuario correctamente");
			return REDIRECT_LIST;
		} catch (ServiceException se) {
			model.addAttribute(ERROR_MESSAGE, se.getMessage());
			model.addAttribute("user", user);
			return EDIT;
		}
	}
	
	@RequestMapping(value = "/borrar", method = POST)
	public String delete(Model model, UserDto form, RedirectAttributes redirAttrs) {
		try {
			userService.delete(form.getIdUser());
			redirAttrs.addFlashAttribute(MESSAGE, "Se elimin\u00F3 el usuario correctamente");
		} catch (ServiceException se) {
			redirAttrs.addFlashAttribute(ERROR_MESSAGE, se.getMessage());
		}
		return REDIRECT_LIST;
	}
	
	@RequestMapping(value = "/reset", method = POST)
	public String reset(Model model, UserDto form, Principal principal, RedirectAttributes redirAttrs) {
		try {
			Long idAdmin = SecurityUtils.getIdUser(principal);
			userService.resetPassword(form.getIdUser(), idAdmin);
			redirAttrs.addFlashAttribute(MESSAGE, "Se reinici\u00F3 el password correctamente");
		} catch (ServiceException se) {
			redirAttrs.addFlashAttribute(ERROR_MESSAGE, se.getMessage());
		}
		return REDIRECT_LIST;
	}
}

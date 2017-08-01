package czar.evaluaciones.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import czar.evaluaciones.dtos.CategoryDto;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.services.CategoryService;

@Controller
@RequestMapping("/manager/categorias")
public class CategoryController {

	@Autowired
    private CategoryService categoryService;
	
	private static final String CATEGORY = "category";
	
	private static final String MESSAGE = "message"; 
	
	private static final String ERROR_MESSAGE = "errorMessage";
	
	private static final String REDIRECT_LIST = "redirect:/manager/categorias";
	
	private static final String EDIT = "manager/categorias/edit";
	
	private static final String ADD = "manager/categorias/add";
	
	@RequestMapping(value = {"", "/"}, method = {POST, GET})
    public String list(Model model, CategoryDto form) {
        CategoryDto categoryDto = categoryService.list(form);
        model.addAttribute("categoryDto", categoryDto);
        return "manager/categorias/list";
    }
	
	@RequestMapping(value = "/agregar", method = GET)
    public String add(Model model) {
		model.addAttribute(CATEGORY, new Category());
        return ADD;
    }
	
	@RequestMapping(value = "/agregar", method = POST)
    public String save(Model model, Category form, RedirectAttributes redirAttrs) {
		try {
			categoryService.save(form);
			redirAttrs.addFlashAttribute(MESSAGE, "Se agreg\u00F3 la categor\u00EDa correctamente");
	        return REDIRECT_LIST;
		} catch (ServiceException se) {
			model.addAttribute(ERROR_MESSAGE, se.getMessage());
			model.addAttribute(CATEGORY, form);
			return ADD;
		}
    }
	
	@RequestMapping(value = "/editar", method = POST)
    public String edit(Model model, @RequestParam(name = "idCategory", required = true) Long idCategory) {
		Category category = categoryService.findById(idCategory);
		model.addAttribute(CATEGORY, category);
        return EDIT;
    }
	
	@RequestMapping(value = "/update", method = POST)
    public String update(Model model, Category form, RedirectAttributes redirAttrs) {
		try {
			categoryService.save(form);
			redirAttrs.addFlashAttribute(MESSAGE, "Se modific\u00F3 la categor\u00EDa correctamente");
	        return REDIRECT_LIST;
		} catch (ServiceException se) {
			model.addAttribute(ERROR_MESSAGE, se.getMessage());
			model.addAttribute(CATEGORY, form);
			return EDIT;
		}
    }
	
	@RequestMapping(value = "/borrar", method = POST)
	public String delete(Model model, Category form, RedirectAttributes redirAttrs) {
		try {
			categoryService.delete(form.getIdCategory());
			redirAttrs.addFlashAttribute(MESSAGE, "Se elimin\u00F3 la categor\u00EDa correctamente");
		} catch (ServiceException se) {
			redirAttrs.addFlashAttribute(ERROR_MESSAGE, se.getMessage());
		}
		return REDIRECT_LIST;
	}
}

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

import czar.evaluaciones.dtos.QuestionDto;
import czar.evaluaciones.enums.QuestionStatus;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.services.CategoryService;
import czar.evaluaciones.services.QuestionService;
import czar.evaluaciones.utils.SecurityUtils;

@Controller
@RequestMapping("/manager/preguntas")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private CategoryService categoryService;
	
	private static final String MESSAGE = "message";
    
    private static final String ERROR_MESSAGE = "errorMessage";
    
	private static final String QUESTION = "question";
	
	private static final String REDIRECT_LIST = "redirect:/manager/preguntas";
	
	private static final String ADD = "manager/preguntas/add";
	
	private static final String ADD_OPENED = "manager/preguntas/addOpened";
	
	private static final String EDIT = "manager/preguntas/edit";
	
    @RequestMapping(value = {"", "/"}, method = {POST, GET})
    public String list(Model model, QuestionDto form) {
    	QuestionDto dto = questionService.find(form);
    	model.addAttribute("questionDto", dto);
    	model.addAttribute("cloud", categoryService.listCategoryCloud());
        return "manager/preguntas/list";
    }
    
    @RequestMapping(value = "/agregar_abierta", method = GET)
    public String addOpenType(Model model) {
     model.addAttribute(QUESTION, new QuestionDto());
     return ADD_OPENED;
    }
    

    
    
    @RequestMapping(value = "/agregar", method = GET)
    public String add(Model model) {
    	model.addAttribute(QUESTION, new QuestionDto());
    	return ADD;
    }
    
    @RequestMapping(value = "/agregar", method = POST)
    public String save(Model model, QuestionDto form, Principal principal, RedirectAttributes redirAttrs) {
        try {
        	Long idCreator = SecurityUtils.getIdUser(principal);
        	questionService.save(form, idCreator);
            redirAttrs.addFlashAttribute(MESSAGE, "Se agreg\u00F3 la pregunta correctamente");
            return REDIRECT_LIST;
        } catch (ServiceException se) {
            model.addAttribute(ERROR_MESSAGE, se.getMessage());
            model.addAttribute(QUESTION, form);
            return ADD;
        }
    }
    
    @RequestMapping(value = "/editar", method = POST)
    public String edit(Model model, @RequestParam(name = "idQuestion", required = true) Long idQuestion) {
        QuestionDto question = questionService.getById(idQuestion);
        model.addAttribute(QUESTION, question);
        return EDIT;
    }
    
    @RequestMapping(value = "/update", method = POST)
    public String update(Model model, QuestionDto form, Principal principal, RedirectAttributes redirAttr) {
        try {
        	Long idCreator = SecurityUtils.getIdUser(principal);
        	questionService.save(form, idCreator);
            redirAttr.addFlashAttribute(MESSAGE, "Se modific\u00FE la pregunta correctamente");
            return REDIRECT_LIST;
        } catch (ServiceException se) {
            model.addAttribute(ERROR_MESSAGE, se.getMessage());
            model.addAttribute(QUESTION, form);
            return EDIT;
        }
    }
    
    @RequestMapping(value = "/cambiar", method = POST)
    public String change(Model model, @RequestParam(name = "idQuestion", required = true) Long idQuestion, 
    		@RequestParam(name = "status") QuestionStatus status, RedirectAttributes redirAttr) {
    	try {
	        questionService.changeStatus(idQuestion, status);
	        redirAttr.addFlashAttribute(MESSAGE, "Se modific\u00F3 la pregunta correctamente");	        
    	} catch (ServiceException se) {
    		redirAttr.addFlashAttribute(ERROR_MESSAGE, se.getMessage());
    	}
    	return REDIRECT_LIST;
    }
}

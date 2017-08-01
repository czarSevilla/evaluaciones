package czar.evaluaciones.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import czar.evaluaciones.dtos.ExamDto;
import czar.evaluaciones.entities.Exam;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.services.ComboService;
import czar.evaluaciones.services.ExamService;


@Controller
@RequestMapping("/manager/examenes")
public class ExamController {
    
    @Autowired
    private ExamService examService;
    
    @Autowired
    private ComboService comboService;
    
    private static final String MESSAGE = "message";
    
    private static final String ERROR_MESSAGE = "errorMessage";
    
    private static final String EXAM = "exam";
    
    private static final String REDIREC_LIST = "redirect:/manager/examenes";
    
    private static final String EDIT = "manager/examenes/edit";
    
    private static final String ADD = "manager/examenes/add";
    
    @RequestMapping(value = {"", "/"}, method = {POST, GET})
    public String list(Model model, ExamDto form) {
        ExamDto examDto = examService.find(form);
        model.addAttribute("examDto", examDto);
        return "manager/examenes/list";
    }
    
    @RequestMapping(value = "/agregar", method = GET)
    public String add(Model model) {
    	model.addAttribute("categories", comboService.getComboCategories());
        model.addAttribute(EXAM, new ExamDto());
        return ADD;
    }
    
    @RequestMapping(value = "/agregar", method = POST)
    public String save(Model model, ExamDto form, RedirectAttributes redirAttrs) {
        try {
            examService.save(form);
            redirAttrs.addFlashAttribute(MESSAGE, "Se agreg\u00F3 el examen correctamente");
            return REDIREC_LIST;
        } catch (ServiceException se) {
            model.addAttribute(ERROR_MESSAGE, se.getMessage());
            model.addAttribute(EXAM, form);
            return ADD;
        }
    }
    
    @RequestMapping(value = "/editar", method = POST)
    public String edit(Model model, @RequestParam(name = "idExam", required = true) Long idExam) {
        ExamDto exam = examService.getById(idExam);
        model.addAttribute(EXAM, exam);
        return EDIT;
    }
    
    @RequestMapping(value = "/update", method = POST)
    public String update(Model model, ExamDto form, RedirectAttributes redirAttr) {
        try {
            examService.save(form);
            redirAttr.addFlashAttribute(MESSAGE, "Se modific\u00FE el examen correctamente");
            return REDIREC_LIST;
        } catch (ServiceException se) {
            model.addAttribute(ERROR_MESSAGE, se.getMessage());
            model.addAttribute(EXAM, form);
            return EDIT;
        }
    }
    
    @RequestMapping(value = "/borrar", method = POST)
    public String delete(Model model, Exam form, RedirectAttributes redirAttrs) {
        try {
            examService.delete(form.getIdExam());
            redirAttrs.addFlashAttribute(MESSAGE, "Se elimin\u00F3 el examen correctamente");
        } catch (ServiceException se) {
            redirAttrs.addFlashAttribute(ERROR_MESSAGE, se.getMessage());
        }
        return REDIREC_LIST;
    }
}

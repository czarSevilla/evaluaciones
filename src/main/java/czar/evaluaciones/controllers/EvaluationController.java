package czar.evaluaciones.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static czar.evaluaciones.enums.SourceEvaluation.CATEGORIES;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import czar.evaluaciones.dtos.EvaluationDto;
import czar.evaluaciones.dtos.GenerateEvalDto;
import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.dtos.ViewEvalDto;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.enums.EvaluationStatus;
import czar.evaluaciones.services.ComboService;
import czar.evaluaciones.services.ConfigService;
import czar.evaluaciones.services.EvaluationService;
import czar.evaluaciones.services.UserService;
import czar.evaluaciones.utils.SecurityUtils;

@Controller
public class EvaluationController {
    
    @Autowired
    private ComboService comboService;
    
    @Autowired
    private EvaluationService evaluationService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ConfigService configService;
    
    @RequestMapping(value = {"/manager/evaluaciones", "/manager/evaluaciones/"}, method = {GET, POST})
    public String list(ViewEvalDto viewEvalDto, Model model) {
    	ViewEvalDto page = evaluationService.findEvaluations(viewEvalDto);
    	List<EvaluationStatus> statuses = Arrays.asList(EvaluationStatus.values());
    	List<UserDto> managers = userService.findByActiveRoles("ROLE_MANAGER", "ROLE_ADMIN");
    	model.addAttribute("evalDto", page);
    	model.addAttribute("comboStatus", statuses);
    	model.addAttribute("comboManager", managers);
        return "manager/evaluaciones/list";
    }
    
    @RequestMapping(value = "/manager/evaluaciones/generar", method = GET)
    public String generarInit() {
        return "manager/evaluaciones/step1";
    }
    
    @RequestMapping(value = "/manager/evaluaciones/generar", method = POST)
    public String generarAction(GenerateEvalDto form, Model model, Principal principal, RedirectAttributes redirAttrs) {
        Long idManager = SecurityUtils.getIdUser(principal);
        if (form.getStep() == 1) {
            if (form.getSource() == CATEGORIES) {
                model.addAttribute("categories", comboService.getComboCategories());
            } else {
                model.addAttribute("exams", comboService.getComboExams());
            }
            Configuration passValueConfig = configService.findByKey(Config.PASS_VALUE.toString());
            Configuration timeExam = configService.findByKey(Config.EXAM_MINUTES.toString());
            form.setPassPercent(Integer.valueOf(passValueConfig.getValue()));
            form.setEvalMinutes(Integer.valueOf(timeExam.getValue()));
            model.addAttribute("form", form);
            return "manager/evaluaciones/step2";
        } else {
            evaluationService.generateEvaluation(form, idManager);
            redirAttrs.addFlashAttribute("message", "Se gener\u00F3 la evaluaci\u00F3n correctamente");
            return "redirect:/manager/evaluaciones";
        }
    }
    
    @RequestMapping(value = "/evaluacion", method = GET)
    public String evaluate() {
        return "redirect:/";
    }
    
    @RequestMapping(value = "/evaluacion", method = POST)
    public String evaluate(EvaluationDto form, Model model, Principal principal) {     
        Long idUser = SecurityUtils.getIdUser(principal);
        EvaluationDto current = evaluationService.loadEvaluation(form.getIdEvaluation(), idUser, form); 
        if (current.isError()) {
            model.addAttribute("evaluationDto", current);
            return "manager/evaluaciones/evaluacionError";
        }
        if (current.isFinish()) {
            return "manager/evaluaciones/finish";
        }
        model.addAttribute("evaluationDto", current);
        return "manager/evaluaciones/evaluacion";
    }
    
    @RequestMapping(value = "/manager/evaluaciones/consultar", method = POST)
    public String view(EvaluationDto form, Model model) {
       EvaluationDto evaluation = evaluationService.loadEvaluation(form.getIdEvaluation());
       model.addAttribute("evaluation", evaluation);
       return "manager/evaluaciones/detail";
    }

}

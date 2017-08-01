package czar.evaluaciones.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import czar.evaluaciones.entities.Category;
import czar.evaluaciones.services.CategoryService;

@RestController
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/manager/categorias/find/{descriptionLike}")
	public List<Category> find(@PathVariable(value = "descriptionLike") String descriptionLike) {
		return categoryService.findByDescription(descriptionLike);
	}
}

package com.example.recipebook.controllers;

import com.example.recipebook.domain.Category;
import com.example.recipebook.domain.UnitOfMeasure;
import com.example.recipebook.repositories.CategoryRepository;
import com.example.recipebook.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/", "/index"})
    public String getIndexPage() {
        Optional<Category> categoryOptional = categoryRepository.findByDescription("Italian");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        System.out.println("Category Id:" + categoryOptional.get().getId());
        System.out.println("UnitOfMeasure Id:" + unitOfMeasureOptional.get().getId());
        return "index";
    }
}

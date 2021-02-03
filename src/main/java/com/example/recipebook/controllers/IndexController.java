package com.example.recipebook.controllers;

import com.example.recipebook.domain.Category;
import com.example.recipebook.domain.UnitOfMeasure;
import com.example.recipebook.repositories.CategoryRepository;
import com.example.recipebook.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
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
        log.debug("Getting index page");
        return "redirect:/recipes/list";
    }
}

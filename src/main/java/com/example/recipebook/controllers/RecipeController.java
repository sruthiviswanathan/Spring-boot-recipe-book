package com.example.recipebook.controllers;


import com.example.recipebook.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/list")
    public String getRecipes(Model model) {
        log.debug("Getting recipes");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipes/list";
    }
}

package com.example.recipebook.controllers;


import com.example.recipebook.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/list")
    private String getRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipes/list";
    }
}

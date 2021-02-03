package com.example.recipebook.controllers;


import com.example.recipebook.commands.IngredientsCommand;
import com.example.recipebook.services.IngredientService;
import com.example.recipebook.services.RecipeService;
import com.example.recipebook.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Listing ingredients for" + recipeId);
        model.addAttribute("recipe", recipeService.getRecipeById(Long.valueOf(recipeId)));
        return "recipes/ingredient/list";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/show/{ingredientId}")
    public String listIngredientById(@PathVariable String recipeId , @PathVariable String ingredientId, Model model) {
        log.debug("Listing ingredients for recipe: " + recipeId + "and for Ingredient Id" + ingredientId);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipes/ingredient/show";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/delete/{ingredientId}")
    public String deleteIngredientById(@PathVariable String recipeId , @PathVariable String ingredientId) {
        log.debug("Listing ingredients for recipe: " + recipeId + "and for Ingredient Id" + ingredientId);
        ingredientService.deleteIngredientInRecipe(Long.valueOf(ingredientId));
        return "redirect:/recipe/"+ recipeId + "/ingredients";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/update/{ingredientId}")
    public String updateIngredientById(@PathVariable String recipeId , @PathVariable String ingredientId, Model model) {
        log.debug("Listing ingredients for recipe: " + recipeId + "and for Ingredient Id" + ingredientId);
        model.addAttribute("ingredient", ingredientService.findByIngredientId(Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipes/ingredient/create";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String addOrUpdateIngredients(@ModelAttribute IngredientsCommand ingredientsCommand, Model model) {
        IngredientsCommand savedIngredientCommand = ingredientService.saveIngredientsCommand(ingredientsCommand);
        model.addAttribute("ingredient", savedIngredientCommand);
        return "recipes/ingredient/show";
    }
}

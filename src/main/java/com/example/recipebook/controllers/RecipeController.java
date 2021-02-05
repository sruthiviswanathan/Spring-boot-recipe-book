package com.example.recipebook.controllers;


import com.example.recipebook.commands.RecipeCommand;
import com.example.recipebook.converters.RecipeCommandToRecipe;
import com.example.recipebook.converters.RecipeToRecipeCommand;
import com.example.recipebook.domain.Recipe;
import com.example.recipebook.exceptions.NotFoundException;
import com.example.recipebook.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeController(RecipeService recipeService, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeService = recipeService;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @RequestMapping("/list")
    public String getRecipes(Model model) {
        log.debug("Getting recipes");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipes/list";
    }

    @RequestMapping("/show/{id}")
    public String getRecipeById(@PathVariable String id, Model model) {
        log.debug("Getting recipe by Id");
        model.addAttribute("recipe", recipeService.getRecipeById(Long.valueOf(id)));
        return "recipes/show";
    }

    @RequestMapping("/new")
    public String renderNewRecipeForm(Model model) {
        RecipeCommand recipeCommand = new RecipeCommand();
        model.addAttribute("recipe", recipeCommand);
        return "recipes/create";
    }

    @RequestMapping("/update/{id}")
    public String updateExistingRecipeForm(@PathVariable String id, Model model) {
        Recipe recipe = recipeService.getRecipeById(Long.valueOf(id));
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
        model.addAttribute("recipe", recipeCommand);
        return "recipes/create";
    }

    @PostMapping
    @RequestMapping("/add")
    public String addOrUpdateRecipe(@ModelAttribute RecipeCommand recipeCommand) {
        log.debug("Create a new Recipe");
        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipes/show/" + savedRecipe.getId();
    }

    @GetMapping
    @RequestMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Recipe Id to be deleted: " + id);
        recipeService.deleteRecipeById(Long.valueOf(id));
        return "redirect:/recipes/list";
    }

}

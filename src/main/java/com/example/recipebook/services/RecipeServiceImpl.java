package com.example.recipebook.services;

import com.example.recipebook.commands.RecipeCommand;
import com.example.recipebook.converters.RecipeCommandToRecipe;
import com.example.recipebook.converters.RecipeToRecipeCommand;
import com.example.recipebook.domain.Recipe;
import com.example.recipebook.exceptions.NotFoundException;
import com.example.recipebook.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service impl");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (!recipe.isPresent()) {
            throw new NotFoundException("Recipe Not found");
        }
        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
       Recipe convertedRecipe = recipeCommandToRecipe.convert(recipeCommand);
       Recipe savedRecipe = recipeRepository.save(convertedRecipe);
       log.debug("Saved Recipe Id::" + savedRecipe.getId());
       return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }
}

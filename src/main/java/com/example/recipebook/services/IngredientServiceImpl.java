package com.example.recipebook.services;

import com.example.recipebook.commands.IngredientsCommand;
import com.example.recipebook.converters.IngredientCommandToIngredient;
import com.example.recipebook.converters.IngredientToIngredientCommand;
import com.example.recipebook.domain.Ingredient;
import com.example.recipebook.domain.Recipe;
import com.example.recipebook.repositories.IngredientRepository;
import com.example.recipebook.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientsCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(!recipeOptional.isPresent()) {
            log.error("recipe id not found. Id: " + recipeId);
            throw new RuntimeException("recipe id not found. Id:" + recipeId);
        }
        Recipe recipe = recipeOptional.get();
        Optional<IngredientsCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map( ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            log.error("Ingredient id not found: " + ingredientId);
        }
        return ingredientCommandOptional.get();
    }

    @Override
    public void deleteIngredientInRecipe(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    @Override
    public IngredientsCommand findByIngredientId(Long ingredientId) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
        IngredientsCommand ingredientsCommand = ingredientToIngredientCommand.convert(ingredient.get());
        return ingredientsCommand;
    }

    @Override
    public IngredientsCommand saveIngredientsCommand(IngredientsCommand ingredientsCommand) {
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientsCommand);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientToIngredientCommand.convert(savedIngredient);
    }
}

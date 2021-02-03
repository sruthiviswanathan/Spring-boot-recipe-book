package com.example.recipebook.services;

import com.example.recipebook.commands.IngredientsCommand;
import com.example.recipebook.domain.Ingredient;

public interface IngredientService {
    IngredientsCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    void deleteIngredientInRecipe(Long ingredientId);
    IngredientsCommand findByIngredientId(Long ingredientId);
    IngredientsCommand saveIngredientsCommand(IngredientsCommand ingredientsCommand);
}

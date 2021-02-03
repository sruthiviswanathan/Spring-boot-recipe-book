package com.example.recipebook.converters;


import com.example.recipebook.commands.CategoryCommand;
import com.example.recipebook.commands.IngredientsCommand;
import com.example.recipebook.commands.RecipeCommand;
import com.example.recipebook.domain.Recipe;
import com.example.recipebook.repositories.RecipeRepository;
import com.example.recipebook.services.RecipeService;
import org.springframework.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;
    private final RecipeRepository recipeRepository;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory, IngredientCommandToIngredient ingredientCommandToIngredient, NotesCommandToNotes notesCommandToNotes, RecipeRepository recipeRepository) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Synchronized
    @Nullable
    public Recipe convert(RecipeCommand recipeCommand) {
        if (recipeCommand == null) {
            return null;
        }

        Recipe recipe;
        if(recipeCommand.getId() != null) {
            recipe = recipeRepository.findById(recipeCommand.getId()).get();
        } else {
            recipe = new Recipe();
        }

        recipe.setId(recipeCommand.getId());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setServings(recipeCommand.getServings());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setDifficulty(recipeCommand.getDifficulty());
        if (recipeCommand.getNotes() != null) {
            if (recipe.getNotes() != null) {
                recipeCommand.getNotes().setId(recipe.getNotes().getId());
            }
            recipe.setNotes(notesCommandToNotes.convert(recipeCommand.getNotes()));
        }

        if (recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0) {
            recipeCommand.getCategories().forEach((CategoryCommand category) -> recipe.getCategories().add(categoryCommandToCategory.convert(category)));
        }

        if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
            recipeCommand.getIngredients().forEach((IngredientsCommand ingredient) -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredient)));
        }

        return recipe;
    }
}

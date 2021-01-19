package com.example.recipebook.services;

import com.example.recipebook.domain.Recipe;
import com.example.recipebook.repositories.RecipeRepository;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}

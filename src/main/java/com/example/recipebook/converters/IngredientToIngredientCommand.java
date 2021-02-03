package com.example.recipebook.converters;

import com.example.recipebook.commands.IngredientsCommand;
import com.example.recipebook.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientsCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientsCommand convert(Ingredient ingredient) {
        if(ingredient == null) {
            return null;
        }
        IngredientsCommand ingredientsCommand = new IngredientsCommand();
        ingredientsCommand.setDescription(ingredient.getDescription());
        ingredientsCommand.setId(ingredient.getId());
        ingredientsCommand.setAmount(ingredient.getAmount());
        ingredientsCommand.setUom(unitOfMeasureToUnitOfMeasureCommand.convert(ingredient.getUom()));
        return ingredientsCommand;
    }
}

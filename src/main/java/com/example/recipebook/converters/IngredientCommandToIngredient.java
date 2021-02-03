package com.example.recipebook.converters;

import com.example.recipebook.commands.IngredientsCommand;
import com.example.recipebook.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientsCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientsCommand ingredientsCommand) {
        if(ingredientsCommand == null) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription(ingredientsCommand.getDescription());
        ingredient.setId(ingredientsCommand.getId());
        ingredient.setAmount(ingredientsCommand.getAmount());
        ingredient.setUom(uomConverter.convert(ingredientsCommand.getUom()));
        return ingredient;
    }
}

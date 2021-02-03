package com.example.recipebook.converters;

import com.example.recipebook.commands.CategoryCommand;
import com.example.recipebook.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if(categoryCommand == null) {
            return null;
        }
        Category category = new Category();
        category.setDescription(categoryCommand.getDescription());
        category.setId(categoryCommand.getId());
        return category;
    }
}

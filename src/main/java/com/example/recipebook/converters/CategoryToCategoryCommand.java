package com.example.recipebook.converters;

import com.example.recipebook.commands.CategoryCommand;
import com.example.recipebook.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {
    @Override
    public CategoryCommand convert(Category category) {
        if(category == null) {
            return null;
        }
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setDescription(category.getDescription());
        categoryCommand.setId(category.getId());
        return categoryCommand;
    }
}

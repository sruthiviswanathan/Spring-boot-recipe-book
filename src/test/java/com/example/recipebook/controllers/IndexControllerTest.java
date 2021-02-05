package com.example.recipebook.controllers;

import com.example.recipebook.repositories.CategoryRepository;
import com.example.recipebook.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class IndexControllerTest {

    IndexController indexController;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(categoryRepository, unitOfMeasureRepository);
    }

    @Test
    void getIndexPage() {
       String page = indexController.getIndexPage();
       assertEquals(page , "redirect:/recipes/list");
    }
}
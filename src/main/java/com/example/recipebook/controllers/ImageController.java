package com.example.recipebook.controllers;

import com.example.recipebook.commands.RecipeCommand;
import com.example.recipebook.converters.RecipeToRecipeCommand;
import com.example.recipebook.domain.Recipe;
import com.example.recipebook.services.ImageService;
import com.example.recipebook.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@Slf4j
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public ImageController(ImageService imageService, RecipeService recipeService, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.imageService = imageService;
        this.recipeService = recipeService;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @GetMapping("recipe/{id}/image")
    public String ShowImageUploadPage(@PathVariable String id, Model model) {
        Recipe recipe = recipeService.getRecipeById(Long.valueOf(id));
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
        model.addAttribute("recipe", recipeCommand);
        return "recipes/imageForm";
    }


    @PostMapping("recipe/{id}/image")
    public String uploadRecipeImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) throws Exception {
        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipes/show/"+ id;
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        Recipe recipe = recipeService.getRecipeById(Long.valueOf(id));
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        if (recipeCommand.getImage() != null) {
            byte[] byteArray = new byte[recipeCommand.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipeCommand.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}

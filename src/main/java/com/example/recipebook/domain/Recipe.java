package com.example.recipebook.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.xml.catalog.Catalog;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    @Lob
    private String directions;
    // ORDINAL is default Enum type
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;
    @Lob //To store long byte values
    private Byte[] image;
    // CascadeType set to all - to cascade all operations from parent to child
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
    // Bidirectional mapping
    // Owning side - has joinColumns, specify inverseJoinColumn for the other side.
    // Also add mappedBy on the inverse side of the relationship
    @ManyToMany
    @JoinTable(name="recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn( name = "category_id"))
    private Set<Category> categories = new HashSet<Category>();

    // inverse side of the relationship - specify mappedBy
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<Ingredient>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }
    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}

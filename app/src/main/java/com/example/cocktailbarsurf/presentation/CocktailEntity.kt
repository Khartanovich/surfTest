package com.example.cocktailbarsurf.presentation

import androidx.room.*

@Entity(tableName = "cocktail_entity")
data class CocktailEntity(
    @PrimaryKey
    val cocktailName: String,
    val descriptionCocktail: String?,
    val recipeCocktail: String?,
    val imageCocktail: String?,
    )

@Entity
data class Ingredients(
    @PrimaryKey
    val nameIngredient: String
)

@Entity(tableName = "cross_reference",primaryKeys = ["cocktailName", "nameIngredient"])
data class CrossRefTable(
    val cocktailName: String,
    val nameIngredient: String
)

data class CocktailWithIngredient(
    @Embedded
    val cocktail: CocktailEntity,
    @Relation(
        parentColumn = "cocktailName",
        entityColumn = "nameIngredient",
        associateBy = Junction(CrossRefTable::class)
    )
    val listIngredient: List<Ingredients>
)
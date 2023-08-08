package com.example.cocktailbarsurf.presentation

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Query("SELECT * FROM cocktail_entity")
    fun getAllCocktails(): Flow<List<CocktailEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewCocktails(cocktailEntity: CocktailEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewIngredient(newIngredient: Ingredients)

    @Transaction
    @Query("SELECT * FROM cocktail_entity where cocktailName LIKE :nameCocktail")
    suspend fun getCocktailAndListIngredient(nameCocktail: String): CocktailWithIngredient

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRefTable(crossRefTable: CrossRefTable)

}
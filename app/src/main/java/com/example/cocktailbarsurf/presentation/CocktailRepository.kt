package com.example.cocktailbarsurf.presentation

import kotlinx.coroutines.flow.Flow

class CocktailRepository(private val cocktailDao: CocktailDao) {

    val allCocktails: Flow<List<CocktailEntity>> = cocktailDao.getAllCocktails()

    suspend fun addCocktail(cocktail: CocktailEntity){
        cocktailDao.addNewCocktails(cocktail)
    }
}
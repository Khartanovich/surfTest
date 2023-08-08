package com.example.cocktailbarsurf.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateNewCocktailViewModel(
    private val cocktailDao: CocktailDao
) : ViewModel() {

    fun addNewCocktail(cocktail: CocktailEntity){
        viewModelScope.launch(Dispatchers.IO) {
            cocktailDao.addNewCocktails(cocktail)
        }
    }

    fun addIngredient(ingredient: String){
        viewModelScope.launch(Dispatchers.IO) {
            cocktailDao.addNewIngredient(Ingredients(ingredient))
        }
    }

    fun insertCrossRef(crossRefTable: CrossRefTable){
        viewModelScope.launch(Dispatchers.IO) {
            cocktailDao.insertCrossRefTable(crossRefTable)
        }
    }

}
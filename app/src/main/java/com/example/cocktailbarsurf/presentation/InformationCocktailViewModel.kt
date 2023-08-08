package com.example.cocktailbarsurf.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InformationCocktailViewModel(private val cocktailDao: CocktailDao) : ViewModel() {
    private val _information = MutableStateFlow<CocktailWithIngredient?>(null)
    val information = _information.asStateFlow()

    fun getInformationCocktail(nameCocktail: String){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                cocktailDao.getCocktailAndListIngredient(nameCocktail)
            }.fold(
                onSuccess = {_information.value = it},
                onFailure = {

                }
            )

        }
    }

}
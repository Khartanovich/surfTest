package com.example.cocktailbarsurf.presentation

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CocktailEntity::class,
                     Ingredients::class,
                     CrossRefTable::class], version = 1, exportSchema = false)
abstract class CocktailDataBase: RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}
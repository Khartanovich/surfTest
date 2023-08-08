package com.example.cocktailbarsurf.presentation

import android.app.Application
import androidx.room.Room

class App: Application() {

    lateinit var cocktailsDataBase: CocktailDataBase
    override fun onCreate() {
        super.onCreate()

        cocktailsDataBase = Room.databaseBuilder(applicationContext,
        CocktailDataBase::class.java, "db")
            .build()
    }
}
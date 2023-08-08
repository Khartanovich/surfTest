package com.example.cocktailbarsurf.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailbarsurf.databinding.ItemIngredientBinding

class InformationIngredientAdapter: RecyclerView.Adapter<InformationIngredientAdapter.InfIngrVieHolder>() {

    private var ingredients: List<Ingredients> = emptyList()
    fun setIngredient(listIngr: List<Ingredients>){
        this.ingredients = listIngr
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfIngrVieHolder {
        return InfIngrVieHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: InfIngrVieHolder, position: Int) {
        var ingredient = ingredients.getOrNull(position)
        with(holder){
            binding.nameIngredient.text = ingredient?.nameIngredient
        }
    }

    override fun getItemCount(): Int = ingredients.size

    class InfIngrVieHolder(val binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root)
}
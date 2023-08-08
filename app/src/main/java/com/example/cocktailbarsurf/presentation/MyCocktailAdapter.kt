package com.example.cocktailbarsurf.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktailbarsurf.R
import com.example.cocktailbarsurf.databinding.ItemCocktailBinding

class MyCocktailAdapter(private val onClickCocktail: OnClickCocktail) : RecyclerView.Adapter<MyCocktailAdapter.MyCocktailViewHolder>() {

    private var cocktails: List<CocktailEntity> = emptyList()
    fun setCocktails(listCocktails: List<CocktailEntity>){
        this.cocktails = listCocktails
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCocktailViewHolder {
        return MyCocktailViewHolder(
            ItemCocktailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyCocktailViewHolder, position: Int) {
        val cocktail = cocktails.getOrNull(position)
        with(holder.binding){
           nameCocktail.text = cocktail?.cocktailName
            if (cocktail?.imageCocktail == null){
                imageCocktail.setImageResource(R.drawable.image_placeholder_cocktail)
            } else {
                Glide.with(imageCocktail.context).load(cocktail?.imageCocktail).into(imageCocktail)
            }
        }
        holder.binding.root.setOnClickListener {
            cocktail?.let {
                onClickCocktail.onClickCocktail(it)
            }
        }
    }

    override fun getItemCount(): Int = cocktails.size

    class MyCocktailViewHolder(val binding: ItemCocktailBinding): RecyclerView.ViewHolder(binding.root)
}

interface OnClickCocktail{
    fun onClickCocktail(cocktail: CocktailEntity)
}
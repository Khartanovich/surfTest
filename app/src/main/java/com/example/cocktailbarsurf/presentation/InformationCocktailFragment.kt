package com.example.cocktailbarsurf.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.cocktailbarsurf.databinding.FragmentInformationCocktailBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class InformationCocktailFragment : Fragment() {

    companion object {
        fun newInstance() = InformationCocktailFragment()
    }
    private var _binding: FragmentInformationCocktailBinding? = null
    private val binding get() = _binding!!
    private val informationIngredientAdapter = InformationIngredientAdapter()

    private  val viewModel: InformationCocktailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val cocktailDao =
                    (requireContext().applicationContext as App).cocktailsDataBase.cocktailDao()
                return InformationCocktailViewModel(cocktailDao) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInformationCocktailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameCocktail = arguments?.getString("nameCocktail")

        if (nameCocktail != null){
            viewModel.getInformationCocktail(nameCocktail)
        }
        binding.recyclerListIngredient.adapter = informationIngredientAdapter
        setInformation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setInformation(){
        viewModel.information.onEach {
            Log.d("Mylog", "${it?.listIngredient?.size}")
            binding.nameCocktail.text = it?.cocktail?.cocktailName
            binding.description.text = it?.cocktail?.descriptionCocktail
            binding.textRecipe.text = it?.cocktail?.recipeCocktail
            it?.listIngredient?.let { listIngr -> informationIngredientAdapter.setIngredient(listIngr) }

        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}
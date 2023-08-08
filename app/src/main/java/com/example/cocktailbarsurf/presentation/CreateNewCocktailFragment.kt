package com.example.cocktailbarsurf.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.cocktailbarsurf.databinding.FragmentCreateNewCocktailBinding
import com.example.cocktailbarsurf.databinding.IngredientChipBinding
import com.google.android.material.chip.Chip

class CreateNewCocktailFragment : Fragment() {

    companion object {
        fun newInstance() = CreateNewCocktailFragment()
    }

    private var _binding: FragmentCreateNewCocktailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateNewCocktailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val cocktailDao =
                    (requireContext().applicationContext as App).cocktailsDataBase.cocktailDao()
                return CreateNewCocktailViewModel(cocktailDao) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNewCocktailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAddIngredient.setOnClickListener {
            showDialogAddNewIngredient()
        }
        setupDialogListener()
        binding.buttonSave.setOnClickListener {
            saveCocktail()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createCocktailEntity(): CocktailEntity {
        val name = binding.addNameCocktail.text.toString()
        val description = binding.addDescriptionCocktail.text.toString()
        val recipe = binding.addRecipeCocktail.text.toString()

        return CocktailEntity(name, description, recipe, null)
    }

    private fun showDialogAddNewIngredient() {
        val dialogFragment = DialogFragmentAddIngredient()
        dialogFragment.show(parentFragmentManager, DialogFragmentAddIngredient.TAG)
    }

    private fun setupDialogListener() {
        parentFragmentManager.setFragmentResultListener(
            DialogFragmentAddIngredient.REQUEST_KEY,
            viewLifecycleOwner,
            FragmentResultListener { requestKey, result ->
                val nameIngredient = result.getString(DialogFragmentAddIngredient.KEY_RESPONSE)
                if (nameIngredient != null) {
                    //через вью модель добовляем ингредиенты в таблицу
                    setupChipIngredient(nameIngredient)
                    viewModel.addIngredient(nameIngredient)
                }
            }
        )
    }

    private fun createChipIngredient(nameIngredient: String): Chip {
        val chip = IngredientChipBinding.inflate(layoutInflater).root
        chip.text = nameIngredient
        return chip
    }

    private fun setupChipIngredient(nameIngredient: String) {
        val chip = createChipIngredient(nameIngredient)
        binding.chipGroup.addView(chip)
    }

    private fun addListIngredient(): List<String> {
        val ingredients = binding.chipGroup.children.map {
            (it as Chip).text.toString()
        }.toList()
        return ingredients
    }

    private fun saveCocktail(){
        viewModel.addNewCocktail(createCocktailEntity())
        val listIngr = addListIngredient()
        val cocktailName = binding.addNameCocktail.text.toString()
        for (i in listIngr){
            val crossRef = CrossRefTable(cocktailName, i)
            viewModel.insertCrossRef(crossRef)
        }
    }

}

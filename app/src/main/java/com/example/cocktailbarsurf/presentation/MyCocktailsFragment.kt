package com.example.cocktailbarsurf.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cocktailbarsurf.R
import com.example.cocktailbarsurf.databinding.FragmentMyCoctailsBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MyCocktailsFragment : Fragment(), OnClickCocktail {

    companion object {
        fun newInstance() = MyCocktailsFragment()
    }

    private val myCocktailAdapter = MyCocktailAdapter(this)
    private var _binding: FragmentMyCoctailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyCocktailsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val cocktailDao =
                    (requireContext().applicationContext as App).cocktailsDataBase.cocktailDao()
                val repository = CocktailRepository(cocktailDao)
                return MyCocktailsViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyCoctailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerCocktails.adapter = myCocktailAdapter
        initFragment()

        binding.floatingBtnAddNewCocktail.setOnClickListener {
            findNavController().navigate(R.id.action_myCocktailsFragment_to_createNewCocktailFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickCocktail(cocktail: CocktailEntity) {
        val bundle = bundleOf("nameCocktail" to cocktail.cocktailName)
        findNavController().navigate(
            R.id.action_myCocktailsFragment_to_informationCocktailFragment,
            bundle
        )
    }

    private fun initFragment() {
        viewModel.allCocktails.onEach {
            if (it.isNotEmpty()) {
                myCocktailAdapter.setCocktails(it)
                binding.imagePlaceholderFirst.visibility = View.GONE
                binding.arrowDownward.visibility = View.GONE
                binding.addFirstCocktail.visibility = View.GONE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}
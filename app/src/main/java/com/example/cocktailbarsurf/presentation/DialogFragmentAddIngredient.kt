package com.example.cocktailbarsurf.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.cocktailbarsurf.databinding.ItemAddIngredientBinding

class DialogFragmentAddIngredient : DialogFragment() {
    private var newIngredient: String? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bindingView = ItemAddIngredientBinding.inflate(layoutInflater)

        val dialogNewIngredient = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setView(bindingView.root)
            .create()

        dialogNewIngredient.setOnShowListener {
            bindingView.buttonAddIngredient.setOnClickListener {
                newIngredient = bindingView.addNameIngredient.text.toString()
                if (newIngredient!!.isBlank()) {
                    return@setOnClickListener
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Добавлен ингредиент $newIngredient",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(KEY_RESPONSE to newIngredient)
                )
                dismiss()
            }

            bindingView.buttonCanselIngredient.setOnClickListener {
                dismiss()
            }

        }
        return dialogNewIngredient
    }


    companion object {
        val TAG = DialogFragmentAddIngredient::class.java.simpleName
        val REQUEST_KEY = "$TAG RequestKey"
        val KEY_RESPONSE = "KeyResponse"
    }
}
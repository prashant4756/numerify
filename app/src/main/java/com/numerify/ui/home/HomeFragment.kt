package com.numerify.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.numerify.databinding.FragmentHomeBinding
import com.numerify.ui.MainActivityViewModel

class HomeFragment : Fragment() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModels()
        setUpViewsListeners()
        observerViewModels()
        mainActivityViewModel.showEditConfNav(true)
    }

    private fun setUpViewModels() {
        mainActivityViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }

    private fun setUpViewsListeners() {
        binding.textInputField.editText?.doOnTextChanged { inputText, _, _, _ ->
            mainActivityViewModel.convertToNumerals(inputText?.trim())
        }
    }

    private fun observerViewModels() {
        mainActivityViewModel.sumLiveData.observe(viewLifecycleOwner) {
            binding.textSum.text = it?.toString() ?: "0"
        }

        mainActivityViewModel.squareLiveData.observe(viewLifecycleOwner) {
            binding.textSquare.text = it?.toString() ?: "0"
        }

        mainActivityViewModel.resetToDefaultLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.textInputField.editText?.text?.let {
                    mainActivityViewModel.convertToNumerals(it.trim())
                }
            }
        }
    }
}
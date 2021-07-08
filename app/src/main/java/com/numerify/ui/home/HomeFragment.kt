package com.numerify.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.numerify.R
import com.numerify.ui.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
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
        textInputField.editText?.doOnTextChanged { inputText, _, _, _ ->
            mainActivityViewModel.convertToNumerals(inputText?.trim())
        }
    }

    private fun observerViewModels() {
        mainActivityViewModel.sumLiveData.observe(viewLifecycleOwner, Observer {
            textSum.text = it?.toString() ?: "0"
        })

        mainActivityViewModel.squareLiveData.observe(viewLifecycleOwner, Observer {
            textSquare.text = it?.toString() ?: "0"
        })

        mainActivityViewModel.resetToDefaultLiveData.observe(viewLifecycleOwner, Observer{
            if(it) {
                textInputField.editText?.text?.let {
                    mainActivityViewModel.convertToNumerals(it.trim())
                }
            }
        })
    }
}
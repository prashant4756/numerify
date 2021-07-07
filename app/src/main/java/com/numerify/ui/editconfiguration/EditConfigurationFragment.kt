package com.numerify.ui.editconfiguration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.numerify.R
import com.numerify.model.EditConfModel
import com.numerify.ui.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_edit_configuration.*
import java.util.ArrayList

class EditConfigurationFragment : Fragment() {

    private var editConfArray = arrayListOf<EditConfModel>()

    private var editConfAdapter : EditConfRecyclerAdapter? = null

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivityViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_edit_configuration, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivityViewModel.showEditConfNav(false)
        setUpRecyclerView()
        setUpObservables()
    }

    private fun setUpObservables() {
        mainActivityViewModel.resetToDefaultLiveData.observe(viewLifecycleOwner, Observer {
            if(it) {
                editConfArray = getInitialData()
                editConfAdapter?.updateList(editConfArray)
            }
        })
    }

    private fun setUpRecyclerView() {
        configList.apply {
            editConfArray = getInitialData()
            editConfAdapter = EditConfRecyclerAdapter(editConfArray, ::onChangeConf)
            this.adapter = editConfAdapter
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun getInitialData(): ArrayList<EditConfModel> {
        val resultArray = arrayListOf<EditConfModel>()
        val items = mainActivityViewModel.getPrefNumeralsAsMap()

        for ((k, v) in items) {
            resultArray.add(EditConfModel(k, v))
        }
        return resultArray
    }

    private fun onChangeConf(model: EditConfModel, newVal: CharSequence?) {
        mainActivityViewModel.updatePref(model, newVal)
    }
}
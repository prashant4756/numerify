package com.numerify.ui.editconfiguration

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.numerify.R
import com.numerify.model.EditConfModel

class EditConfRecyclerAdapter(
    private var dataSet: java.util.ArrayList<EditConfModel>,
    private val callback: (model: EditConfModel, newVal: CharSequence?) -> Unit
) :
    RecyclerView.Adapter<EditConfRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            model: EditConfModel,
            callback: (model: EditConfModel, newVal: CharSequence?) -> Unit
        ) {

            keyView.text = ""
            valEdit.removeTextChangedListener(textWatcher)

            keyView.text = model.key.toString()
            valEdit.setText(model.value.toString())
            textWatcher = valEdit.doOnTextChanged { text, start, before, count ->
                callback.invoke(model, text)
            }
        }

        var textWatcher: TextWatcher? = null
        val keyView: AppCompatTextView = view.findViewById(R.id.textKey)
        val valEdit: AppCompatEditText = view.findViewById(R.id.editVal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.conf_row_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == -1) return
        holder.bind(dataSet.get(position), callback)
    }

    fun updateList(editConfArray: java.util.ArrayList<EditConfModel>, notifyDataSetChanged: Boolean = true) {
        dataSet = editConfArray
        if(notifyDataSetChanged) notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataSet.size
}
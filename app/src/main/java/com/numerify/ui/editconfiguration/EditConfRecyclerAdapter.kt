package com.numerify.ui.editconfiguration

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.numerify.R
import com.numerify.model.EditConfModel
class EditConfRecyclerAdapter(private val callback: (model: EditConfModel, newVal: CharSequence?) -> Unit) :
        ListAdapter<EditConfModel, EditConfRecyclerAdapter.ViewHolder>(DiffCallback())  {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: EditConfModel, callback: (model: EditConfModel, newVal: CharSequence?) -> Unit) {

            keyView.text = ""
            valEdit.text?.clear()

            keyView.text = model.key.toString()
            valEdit.setText(model.value.toString())
            valEdit.doOnTextChanged { text, start, before, count ->
                callback.invoke(model, text)
            }
        }

        val keyView: AppCompatTextView = view.findViewById(R.id.textKey)
        val valEdit: AppCompatEditText = view.findViewById(R.id.editVal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.conf_row_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == -1) return
        holder.bind(getItem(position), callback)
    }

    fun updateList(editConfArray: java.util.ArrayList<EditConfModel>) {
        submitList(editConfArray)
    }

    private class DiffCallback : DiffUtil.ItemCallback<EditConfModel>() {

        override fun areItemsTheSame(oldItem: EditConfModel, newItem: EditConfModel) =
                oldItem.key == newItem.key

        override fun areContentsTheSame(oldItem: EditConfModel, newItem: EditConfModel) =
                oldItem == newItem
    }
}
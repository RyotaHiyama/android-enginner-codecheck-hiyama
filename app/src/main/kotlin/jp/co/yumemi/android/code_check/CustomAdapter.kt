package jp.co.yumemi.android.code_check

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<Item, ViewHolder>(diff_util) {

    interface OnItemClickListener {
        fun itemClick(item: Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        // ViewHolderのTextViewにitemのnameを設定する
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text = item.name
        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}
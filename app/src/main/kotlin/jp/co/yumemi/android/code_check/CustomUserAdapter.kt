package jp.co.yumemi.android.code_check

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CustomUserAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<User, CustomUserAdapter.ViewHolder>(diff_util_user) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        // ViewHolderのTextViewにitemのnameを設定する
        holder.itemView.findViewById<TextView>(R.id.repositoryNameView)?.text = user.name
        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(user)
        }
    }
}

val diff_util_user = object : DiffUtil.ItemCallback<User>() {
    // Itemのnameが同じならtrue
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.name == newItem.name
    }

    // Itemが同じならtrue
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}
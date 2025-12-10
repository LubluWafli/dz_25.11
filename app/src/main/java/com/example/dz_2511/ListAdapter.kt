package com.example.dz_2511

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_NUMBER = 1
        private const val TYPE_IMAGE = 2
    }

    private val items = mutableListOf<ListItem>()

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: ListItem, currentDescription: String)
        fun onTitleClick(position: Int, item: ListItem, currentTitle: String)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)

        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    fun updateItemTitle(position: Int, newTitle: String) {
        if (position in 0 until items.size) {
            val oldItem = items[position]
            val updatedItem = oldItem.copy(title = newTitle)
            items[position] = updatedItem
            notifyItemChanged(position)
        }
    }

    fun updateItemDescription(position: Int, newDescription: String) {
        if (position in 0 until items.size) {
            val oldItem = items[position]
            val updatedItem = oldItem.copy(description = newDescription)
            Log.d("description", newDescription)
            items[position] = updatedItem
            notifyItemChanged(position)
        }
    }

    fun addItem(item: ListItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addNumberItem() {
        val newId = items.size + 1
        val newItem = ListItem(
            id = newId,
            title = "Предмет $newId (добавлен)",
            type = ItemType.NUMBER
        )
        addItem(newItem)

    }

    fun setItems(newItems: List<ListItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NUMBER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_number, parent, false)
                NumberViewHolder(view)
            }
            TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image, parent, false)
                ImageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder) {
            is NumberViewHolder -> {
                holder.tvTitle.text = item.title
                holder.tvDescription.text = item.description
                if (item.description.isEmpty()) {
                    holder.tvDescription.text = "Описание..."
                }

                holder.itemView.setOnClickListener {
                    itemClickListener?.onItemClick(position, item, item.description)
                }

                holder.tvTitle.setOnClickListener {
                    itemClickListener?.onTitleClick(position, item, holder.tvTitle.text.toString())
                }
            }
            is ImageViewHolder -> {
                holder.tvTitle.text = item.title
                holder.tvDescription.text = item.description
                if (item.description.isEmpty()) {
                    holder.tvDescription.text = "Описание..."
                }

                holder.itemView.setOnClickListener {
                    itemClickListener?.onItemClick(position, item, item.description)
                }

                holder.tvTitle.setOnClickListener {
                    itemClickListener?.onTitleClick(position, item, holder.tvTitle.text.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            ItemType.NUMBER -> TYPE_NUMBER
            ItemType.IMAGE -> TYPE_IMAGE
        }
    }
}
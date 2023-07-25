package id.novian.flowablecash.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

const val VIEW_TYPE_HEADER = 0
const val VIEW_TYPE_ITEM = 1
const val VIEW_TYPE_FOOTER = 2

open class BaseTableAdapter<T: Any>:
    ListAdapter<T, BaseTableAdapter.BaseTableViewHolder<T>>(BaseItemCallBack()) {

    open class BaseTableViewHolder<T>(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), BindRecyclerView<T> {
        override fun onBindHeader(position: Int) {}

        override fun onBindFooter(data: List<T>, position: Int) {}

        override fun onBindItem(data: T, position: Int) {}
    }

    override fun getItemCount(): Int {
        return if (currentList.isEmpty()) 1 else currentList.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isPositionHeader(position) -> VIEW_TYPE_HEADER
            isPositionFooter(position) -> VIEW_TYPE_FOOTER
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTableViewHolder<T> {
        return when(viewType) {
            VIEW_TYPE_HEADER -> {
                val headerView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                BaseTableViewHolder(headerView)
            }

            VIEW_TYPE_FOOTER -> {
                val footerView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                BaseTableViewHolder(footerView)
            }

            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                BaseTableViewHolder(itemView)
            }
        }
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    private fun isPositionFooter(position: Int): Boolean {
        return position == itemCount - 1
    }

    override fun onBindViewHolder(holder: BaseTableViewHolder<T>, position: Int) {
        when(holder.itemViewType) {

            VIEW_TYPE_HEADER -> {
                holder.onBindHeader(position)
            }

            VIEW_TYPE_FOOTER -> {
                holder.onBindFooter(currentList, position)
            }

            else -> {
                val itemPosition = position - 1
                holder.onBindItem(currentList[itemPosition], itemPosition)
            }
        }
    }

    interface BindRecyclerView<T> {
        fun onBindHeader(position: Int)
        fun onBindItem(data: T, position: Int)
        fun onBindFooter(data: List<T>, position: Int)
    }
}
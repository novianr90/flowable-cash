package id.novian.flowablecash.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter<T : Any> :
    ListAdapter<T, BaseAdapter.BaseViewHolder<T>>(BaseItemCallBack()) {

    open class BaseViewHolder<R>(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), BindRecyclerViewHolder<R> {
        override fun onBind(data: R) {}
        override fun onBind(data: R, position: Int) {}
    }

    override fun getItemCount(): Int = currentList.size

    interface BindRecyclerViewHolder<R> {
        fun onBind(data: R)
        fun onBind(data: R, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(currentList[position])
        holder.onBind(currentList[position], position)
    }
}
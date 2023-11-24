package com.example.useralbums.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


abstract class BaseAdapter< DB : ViewDataBinding,T>(initialData: List<T>):
    RecyclerView.Adapter<BaseViewHolder<DB>>() {
    private var _data: List<T> = initialData
    var data: List<T>
        get() = _data
        set(value) {
            val diffResult = DiffUtil.calculateDiff(BaseDiffCallback(_data, value))
            _data = value
            diffResult.dispatchUpdatesTo(this)
        }
//    var data: List<T> = emptyList()
    var itemClickListener: OnItemClickListener<T>? = null
    abstract fun bind(binding: DB,item: T)
//    fun setData(newData: List<T>) {
//        val diffResult = DiffUtil.calculateDiff(BaseDiffCallback(data, newData))
//        data = newData
//        diffResult.dispatchUpdatesTo(this)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DB> {
        val binding = DataBindingUtil.inflate<DB>(
            LayoutInflater.from(parent.context),
            getLayoutId(),
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<DB>, position: Int) {
        val item = data[position]
        bind(holder.binding,item)
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(item)

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    abstract fun getLayoutId(): Int



    private class BaseDiffCallback<T>(private val oldList: List<T>, private val newList: List<T>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}
interface OnItemClickListener<T> {
    fun onItemClick(item: T)
}
@FragmentScoped
class BaseViewHolder<DB : ViewDataBinding> @Inject constructor(val binding: DB) :
    RecyclerView.ViewHolder(binding.root) {
   // abstract fun bind(item: T)
}

package com.example.useralbums.ui.bindingAdapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.useralbums.domain.models.State
import com.example.useralbums.ui.base.BaseAdapter


//@BindingAdapter("setAdapter")
//fun <DB : ViewDataBinding,T> setAdapter(
//    recyclerView: RecyclerView,
//    adapter: BaseAdapter<DB,T>?
//) {
//    adapter?.let {
//        recyclerView.adapter = it
//    }
//}
//
//@BindingAdapter("submitList")
//fun < T, DB : ViewDataBinding>submitList(recyclerView: RecyclerView, list: List<T>?) {
//    val adapter = recyclerView.adapter as BaseAdapter<DB,T >?
//    if (list != null) {
//        adapter?.setData(list)
//    }
//}

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, image: Int) {
    Glide.with(imageView.context)
        .load(image)
        .into(imageView)
}
@BindingAdapter("showLoading")
fun <T> showLoading(view:View,state: State<T>?){
    if (state is State.Loading){
        view.visibility = View.VISIBLE
    }
    else{
        view.visibility = View.GONE
    }
}
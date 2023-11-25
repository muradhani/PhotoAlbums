package com.example.useralbums.ui.adapter

import com.bumptech.glide.Glide
import com.example.useralbums.R
import com.example.useralbums.data.Dto.albums.AlbumsResponseItem
import com.example.useralbums.data.Dto.photos.PhotosResponseItem
import com.example.useralbums.databinding.PhotosRvItemBinding
import com.example.useralbums.ui.base.BaseAdapter

class PhotosAdapter(
    private val list : List<PhotosResponseItem>,
    private val photoListener: PhotoListener
):BaseAdapter<PhotosRvItemBinding,PhotosResponseItem>(list) {
    override fun bind(binding: PhotosRvItemBinding, item: PhotosResponseItem) {
       Glide.with(binding.root).load(item.thumbnailUrl).into(binding.tvImage)
    }

    override fun getLayoutId(): Int {
        return R.layout.photos_rv_item
    }
}
interface PhotoListener {
    fun onPhotoClicked(album:AlbumsResponseItem)
}
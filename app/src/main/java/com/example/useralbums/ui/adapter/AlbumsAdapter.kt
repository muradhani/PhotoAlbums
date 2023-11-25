package com.example.useralbums.ui.adapter

import android.view.View
import com.example.useralbums.R
import com.example.useralbums.data.Dto.albums.AlbumsResponseItem
import com.example.useralbums.databinding.AlbumsRvItemBinding
import com.example.useralbums.ui.base.BaseAdapter
import com.example.useralbums.ui.base.BaseViewHolder

class AlbumsAdapter(
    private val list : List<AlbumsResponseItem>,
    private val albumListener: AlbumListener
):BaseAdapter<AlbumsRvItemBinding,AlbumsResponseItem>(list) {
    override fun bind(binding: AlbumsRvItemBinding, item: AlbumsResponseItem) {
        binding.name = item.title
        binding.root.setOnClickListener {
            albumListener.onAlbumClicked(item)
        }
    }

    override fun getLayoutId(): Int {
       return R.layout.albums_rv_item
    }

}

interface AlbumListener {
    fun onAlbumClicked(album:AlbumsResponseItem)
}
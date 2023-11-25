package com.example.useralbums.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.useralbums.R
import com.example.useralbums.data.Dto.albums.AlbumsResponseItem
import com.example.useralbums.databinding.FragmentAlbumsBinding
import com.example.useralbums.domain.models.State
import com.example.useralbums.ui.adapter.PhotoListener
import com.example.useralbums.ui.adapter.PhotosAdapter
import com.example.useralbums.ui.base.BaseFragment
import com.example.useralbums.ui.viewmodels.AlbumsFragmentViewModel


class AlbumsFragment : BaseFragment<FragmentAlbumsBinding, AlbumsFragmentViewModel>() ,PhotoListener{
    val args:AlbumsFragmentArgs by navArgs()
    private val adapter: PhotosAdapter by lazy { PhotosAdapter(emptyList(), this) }
    override fun createViewModel(): AlbumsFragmentViewModel {
        return ViewModelProvider(this).get(AlbumsFragmentViewModel::class.java)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAlbumsBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            photosRv.adapter = adapter
            toolbar.title = args.albumName
        }
        Log.i("main",args.albumId.toString())
        viewModel.getPhotos(args.albumId,context)
        observePhotos()
        initEditText(context)
    }
    private fun initEditText(context: Context?) {
        binding.searchEt.addTextChangedListener {
            if (it!!.isNotEmpty()){
                viewModel.search(it.toString(),context)

            }
            else{
                viewModel.getPhotos(args.albumId,context)
            }
        }
    }
    private fun observePhotos() {
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            if (it is State.Success){
                adapter.setData(it.data)
            }
        })
    }

    override fun onPhotoClicked(album: AlbumsResponseItem) {

    }
}
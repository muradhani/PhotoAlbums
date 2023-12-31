package com.example.useralbums.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.useralbums.R
import com.example.useralbums.data.Dto.albums.AlbumsResponseItem
import com.example.useralbums.data.Dto.photos.PhotosResponseItem
import com.example.useralbums.databinding.FragmentAlbumsBinding
import com.example.useralbums.domain.models.State
import com.example.useralbums.ui.adapter.PhotoListener
import com.example.useralbums.ui.adapter.PhotosAdapter
import com.example.useralbums.ui.base.BaseFragment
import com.example.useralbums.ui.viewmodels.AlbumsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        backStack()
        binding.viewModel = viewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            photosRv.adapter = adapter
            toolbar.title = args.albumName
        }
        viewModel.getPhotos(args.albumId)
        observePhotos()
        initEditText()
    }

    private fun backStack() {
        val onBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle back press here
                // For example, navigate to a different destination or perform some action
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

    }

    private fun initEditText() {
        binding.searchEt.addTextChangedListener {
            if (it!!.isNotEmpty()){
                viewModel.search(it.toString())

            }
            else{
                viewModel.getPhotos(args.albumId)
            }
        }
    }
    private fun observePhotos() {
        viewModel.photos.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Success -> {
                    adapter.setData(state.data)
                }
                is State.Error -> {
                    // Display a toast with the error message
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        })
    }

    override fun onPhotoClicked(photo: PhotosResponseItem) {
        val action = AlbumsFragmentDirections.actionAlbumsFragmentToImageViewerFragment(photo.thumbnailUrl)
        Navigation.findNavController(requireView()).navigate(action)
    }
}
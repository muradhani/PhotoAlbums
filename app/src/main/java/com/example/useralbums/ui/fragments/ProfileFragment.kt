package com.example.useralbums.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.useralbums.R
import com.example.useralbums.data.Dto.albums.AlbumsResponseItem
import com.example.useralbums.databinding.FragmentProfileBinding
import com.example.useralbums.domain.models.State
import com.example.useralbums.ui.adapter.AlbumListener
import com.example.useralbums.ui.adapter.AlbumsAdapter
import com.example.useralbums.ui.base.BaseAdapter
import com.example.useralbums.ui.base.BaseFragment
import com.example.useralbums.ui.viewmodels.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileFragmentViewModel>(),AlbumListener{
    private val adapter: AlbumsAdapter by lazy { AlbumsAdapter(emptyList(), this) }
    private var scope: CoroutineScope? = null
    override fun createViewModel(): ProfileFragmentViewModel {
        return ViewModelProvider(this).get(ProfileFragmentViewModel::class.java)

    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            albumsRv.adapter = adapter
        }

        observeUserData()
        observeAlbumList()
    }
    private fun observeUserData() {
        viewModel.user.observe(viewLifecycleOwner, Observer { state ->
            if (state is State.Success) {
                binding.userNameTv.text = state.toData()?.name
                binding.userAddressTv.text = state.toData()?.address
            }
        })
    }

    private fun observeAlbumList() {
        viewModel.albumList.observe(viewLifecycleOwner, Observer { state ->
            if (state is State.Success) {
                adapter.setData(state.data)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (scope == null || scope?.isActive == false) {
            scope = CoroutineScope(Dispatchers.Main + Job())
            scope?.launch {
                viewModel.getRandomUser()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        scope?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope?.cancel()
    }
    override fun onAlbumClicked(album: AlbumsResponseItem) {
        val action = ProfileFragmentDirections.actionProfileFragmentToAlbumsFragment(album.id,album.title)
        Navigation.findNavController(requireView()).navigate(action)
    }
}
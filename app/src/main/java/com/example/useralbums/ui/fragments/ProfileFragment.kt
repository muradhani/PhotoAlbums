package com.example.useralbums.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.useralbums.R
import com.example.useralbums.databinding.FragmentProfileBinding
import com.example.useralbums.ui.adapter.AlbumsAdapter
import com.example.useralbums.ui.base.BaseFragment
import com.example.useralbums.ui.viewmodels.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileFragmentViewModel>(){

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
        binding.adapter = AlbumsAdapter(emptyList(),viewModel)
    }
}
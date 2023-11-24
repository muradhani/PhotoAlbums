package com.example.useralbums.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint


abstract class BaseFragment<T : ViewDataBinding, V : ViewModel> : Fragment() {

    lateinit var binding: T
    lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = createBinding(inflater, container)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    abstract fun createViewModel(): V

    abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T
}
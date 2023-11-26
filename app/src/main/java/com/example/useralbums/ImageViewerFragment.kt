package com.example.useralbums

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.davemorrissey.labs.subscaleview.ImageSource
import com.example.useralbums.databinding.FragmentImageViewerBinding
import com.bumptech.glide.request.transition.Transition
import android.content.Intent
import android.widget.ImageButton
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import java.io.File
import java.io.FileOutputStream
class ImageViewerFragment : Fragment() {
   lateinit var binding :FragmentImageViewerBinding
    val args: ImageViewerFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Use Data Binding to inflate the layout
        binding = FragmentImageViewerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    private fun loadImageFromUrl(imageUrl: String) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Set the downloaded bitmap to the SubsamplingScaleImageView
                    binding.subsamplingImageView.setImage(ImageSource.bitmap(resource))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Handle the case where the load is cleared
                }
            })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backStack()
        loadImageFromUrl(args.imageLink)
        binding.shareButton.setOnClickListener {
            shareImage()
        }
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

    private fun shareImage() {
        val imageUrl = args.imageLink

        // Share the image URL using an Intent
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, imageUrl)

        // Start the share activity
        startActivity(Intent.createChooser(shareIntent, "Share Image URL"))
    }
}
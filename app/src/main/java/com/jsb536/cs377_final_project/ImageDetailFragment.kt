package com.jsb536.cs377_final_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jsb536.cs377_final_project.databinding.FragmentImageDetailBinding
import com.jsb536.cs377_final_project.ui.ImageViewModel

class ImageDetailFragment : Fragment() {

    private var _binding: FragmentImageDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ImageDetailFragmentArgs by navArgs()
    private val imageViewModel: ImageViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageData = args.imageData
        imageViewModel.checkIfImageIsSaved(imageData)

        Glide.with(this)
            .load(imageData.imageUrlRegular)
            .fitCenter()
            .into(binding.detailImageView)
        // TODO: Add logic to check if imageData.id exists via ViewModel/Repo
        if (imageData.isSaved) {
            binding.downloadButton.text = "Unsave"
        } else {
            binding.downloadButton.text = "Save"
        }

        binding.downloadButton.setOnClickListener {
            if (imageData.isSaved) {
                imageViewModel.deleteImageFromDatabase(imageData)
                Toast.makeText(requireContext(), "Image unsaved", Toast.LENGTH_SHORT).show()
                // TODO: Update button text to "Save"
                binding.downloadButton.text = "Save"
                imageData.isSaved = false
            } else {
                imageViewModel.saveImageToDatabase(imageData)
                Toast.makeText(requireContext(), "Image saved!", Toast.LENGTH_SHORT).show()
                // TODO: Update button text to "Unsave"
                binding.downloadButton.text = "Unsave"
                imageData.isSaved = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
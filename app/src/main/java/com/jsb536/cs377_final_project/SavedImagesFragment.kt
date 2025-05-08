package com.jsb536.cs377_final_project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jsb536.cs377_final_project.databinding.FragmentSavedImagesBinding
import com.jsb536.cs377_final_project.ui.ImageViewModel

class SavedImagesFragment : Fragment(), OnImageClickListener {

    private var _binding: FragmentSavedImagesBinding? = null
    private val binding get() = _binding!!

    private val imageViewModel: ImageViewModel by viewModels()

    private lateinit var imageGridAdapter: ImageGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        imageGridAdapter = ImageGridAdapter(emptyList(), this)

        binding.imageGridRecyclerView.apply {
            adapter = imageGridAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        imageViewModel.savedResults.observe(viewLifecycleOwner, Observer { images ->
            Log.d("SavedImagesFragment", "Received ${images.size} images")
            imageGridAdapter.updateData(images)
        })

        imageViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            Log.d("SavedImagesFragment", "Loading state: $isLoading")
        })

        imageViewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            errorMsg?.let {
                Log.e("SavedImagesFragment", "Error: $it")
            }
        })
    }

    override fun onImageClick(imageData: ImageData) {
        Log.d("SavedImagesFragment", "Image clicked: ${imageData.description}")

        val action = SavedImagesFragmentDirections.actionSavedImagesFragmentToImageDetailFragment(imageData = imageData)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
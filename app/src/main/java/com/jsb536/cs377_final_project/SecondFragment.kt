package com.jsb536.cs377_final_project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jsb536.cs377_final_project.databinding.FragmentSecondBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jsb536.cs377_final_project.ui.ImageViewModel

class SecondFragment : Fragment(), OnImageClickListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val imageViewModel: ImageViewModel by viewModels()

    private lateinit var imageGridAdapter: ImageGridAdapter
    private var currentQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentQuery = it.getString("searchQuery")
        }
        // TODO: Replace above with Safe Args:
        // val args: SecondFragmentArgs by navArgs()
        // currentQuery = args.searchQuery
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        currentQuery?.let {
            if (it.isNotEmpty()) {
                binding.textViewSecond.text = "Results for: $it"
                imageViewModel.searchImages(it)
            }
        } ?: run {
            binding.textViewSecond.text = "No search query provided."
        }
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
        imageViewModel.searchResults.observe(viewLifecycleOwner, Observer { images ->
            Log.d("SecondFragment", "Received ${images.size} images")
            imageGridAdapter.updateData(images)
        })

        imageViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            Log.d("SecondFragment", "Loading state: $isLoading")
        })

        imageViewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            errorMsg?.let {
                Log.e("SecondFragment", "Error: $it")
            }
        })
    }

    override fun onImageClick(imageData: ImageData) {
        Log.d("SecondFragment", "Image clicked: ${imageData.description}")

        val action = SecondFragmentDirections.actionSecondFragmentToImageDetailFragment(imageData = imageData)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
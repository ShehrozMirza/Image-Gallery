package com.app.image_gallery.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.app.image_gallery.R
import com.app.image_gallery.models.PhotosModel
import com.app.image_gallery.databinding.FragmentPhotosBinding
import com.app.image_gallery.ui.viewmodels.PhotosViewModel
import com.app.image_gallery.ui.adapters.PhotosAdapter
import com.app.image_gallery.ui.adapters.PhotoLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : Fragment(R.layout.fragment_photos), PhotosAdapter.OnItemClickListener {

    private val viewModel by viewModels<PhotosViewModel>()
    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!

    val adapter = PhotosAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPhotosBinding.bind(view)
        setupRecylerView()

        viewModel.photos.observe(viewLifecycleOwner) { data: PagingData<PhotosModel> ->
            adapter.submitData(viewLifecycleOwner.lifecycle, data)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnReload.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading
                        && loadState.append.endOfPaginationReached && adapter.itemCount < 1)
                {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }

            }
        }

        setHasOptionsMenu(true)
    }

    private fun setupRecylerView() {
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = PhotoLoadStateAdapter { adapter.retry() },
                    footer = PhotoLoadStateAdapter { adapter.retry() }
            )

            btnReload.setOnClickListener{
                adapter.retry()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_photos, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchQuery: String?): Boolean {
                if (searchQuery != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(searchQuery)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(photo: PhotosModel) {
        val action = PhotosFragmentDirections.actionPhotosFragmentToPhotoViewFragment(photo)
        findNavController().navigate(action)
    }
}
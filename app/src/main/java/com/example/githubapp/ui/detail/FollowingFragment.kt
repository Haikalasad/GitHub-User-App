package com.example.githubapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val adapter = FollowingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        binding.rvListFollwing.layoutManager = LinearLayoutManager(context)
        binding.rvListFollwing.adapter = adapter

        val username = arguments?.getString("username")
        if (!username.isNullOrEmpty()) {
            viewModel.getListFollower(username)
        }

        viewModel.listFollowing.observe(viewLifecycleOwner, Observer { listFollowingResponse ->
            listFollowingResponse?.let { listFollowingResponse ->
                adapter.submitList(listFollowingResponse)
            }

        })

        viewModel.isFollowingLoading.observe(viewLifecycleOwner, Observer { isFollowingLoading ->
            showLoading(isFollowingLoading)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) { binding.progressBarFollowing.visibility = if (state) View.VISIBLE else View.GONE }


}
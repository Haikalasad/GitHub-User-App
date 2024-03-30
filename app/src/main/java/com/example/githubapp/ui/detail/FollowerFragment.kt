package com.example.githubapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val followerAdapter = FollowerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        binding.rvListFollower.layoutManager = LinearLayoutManager(context)
        binding.rvListFollower.adapter = followerAdapter

        val username = arguments?.getString("username")
        if (!username.isNullOrEmpty()) {
            viewModel.getListFollower(username)
        }

        viewModel.listFollower.observe(viewLifecycleOwner, Observer { listFollowerResponse ->
            listFollowerResponse?.let { followerResponse ->
                followerAdapter.submitList(followerResponse)
            }
        })

        viewModel.isFollowerLoading.observe(viewLifecycleOwner, Observer { isFollowerLoading ->
            showLoading(isFollowerLoading)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showLoading(state: Boolean) { binding.progressBarFollower.visibility = if (state) View.VISIBLE else View.GONE }
}

package com.chalchap7.challengechapter7.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chalchap7.challengechapter7.viewmodel.FavoriteViewModel
import com.chalchap7.challengechapter7.viewmodel.ViewModel
import com.chalchap7.challengechapter7.R
import com.chalchap7.challengechapter7.databinding.FragmentHomeBinding
import com.chalchap7.challengechapter7.view.adapters.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    lateinit var vidioAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivPerson.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        binding.ivFav.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
        setUpMovie()
    }

    private fun setUpMovie(){
        val viewModel = ViewModelProvider(requireActivity())[ViewModel::class.java]
        viewModel.getliveDataVidio().observe(viewLifecycleOwner) {

            if (it != null) {
                binding.rvItem.layoutManager = GridLayoutManager(context, 2)
                vidioAdapter = MovieAdapter(it.results)
                binding.rvItem.adapter = vidioAdapter


                vidioAdapter.onAddFavorites = {
                    binding.homeProgressBar.visibility = View.VISIBLE
                    val favViewModel =
                        ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
                    favViewModel.callPostMovie(
                        it.posterPath,
                        it.originalTitle,
                        it.voteAverage.toString(),
                        it.releaseDate,
                        it.originalLanguage,
                        it.overview
                    )
                    favViewModel.postFavMovie().observe(viewLifecycleOwner) {
                        if (it != null) {
                            binding.homeProgressBar.visibility = View.GONE
                            Toast.makeText(
                                requireActivity(),
                                context?.getString(R.string.tambah_film_fav),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        binding.homeProgressBar.visibility = View.GONE

                    }
                }
            } else {
                Toast.makeText(requireActivity(), "Data Tidak Tampil", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.CallApiVidio()
    }
}
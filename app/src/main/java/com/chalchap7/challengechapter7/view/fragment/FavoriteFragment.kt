package com.chalchap7.challengechapter7.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chalchap7.challengechapter7.viewmodel.FavoriteViewModel
import com.chalchap7.challengechapter7.R
import com.chalchap7.challengechapter7.databinding.FragmentFavoriteBinding
import com.chalchap7.challengechapter7.view.adapters.FavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    lateinit var binding : FragmentFavoriteBinding
    lateinit var favViewModel : FavoriteViewModel
    lateinit var adapter : FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favViewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
        setVMFavAdapter()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setVMFavAdapter(){
        favViewModel.getLDMovie().observe(viewLifecycleOwner) {

            if (it != null) {
                binding.rvFavorites.layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
                )

                adapter = FavoriteAdapter(it)
                binding.rvFavorites.adapter = adapter

                adapter.onDeleteFavorites = {
                    val favViewModel =
                        ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
                    favViewModel.callDeleteFavMovie(it)
                    favViewModel.delFavMovie().observe(viewLifecycleOwner) {
                        if (it != null) {
                            Toast.makeText(
                                requireActivity(),
                                context?.getString(R.string.hapus_film_fav),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                adapter.notifyDataSetChanged()

            } else {
                Toast.makeText(
                    requireActivity(),
                    context?.getString(R.string.no_data),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        favViewModel.callApiFilm()
    }

}
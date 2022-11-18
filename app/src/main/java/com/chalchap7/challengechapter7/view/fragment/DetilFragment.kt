@file:Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate"
)

package com.chalchap7.challengechapter7.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.chalchap7.challengechapter7.R
import com.chalchap7.challengechapter7.databinding.FragmentDetilBinding

@Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate"
)
class DetilFragment : Fragment() {

    lateinit var binding : FragmentDetilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetilBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundle()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_detilFragment_to_homeFragment)
        }
    }

    fun getBundle(){
        val gambar = arguments?.getString("gambar")
        val judul = arguments?.getString("judul")
        val rating = arguments?.getString("rating")
        val tanggal = arguments?.getString("tanggal")
        val bahasa = arguments?.getString("bahasa")
        val detail = arguments?.getString("detail")

        Glide.with(requireActivity()).load("https://image.tmdb.org/t/p/w185$gambar").into(binding.imgPoster)
        binding.movieTitle.text = judul
        binding.movieRating.text = rating
        binding.movieReleaseDate.text = tanggal
        binding.movieLang.text = bahasa
        binding.detaiLFilm.text = detail
    }
}
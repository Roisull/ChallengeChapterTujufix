package com.chalchap7.challengechapter7.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.chalchap7.challengechapter7.R
import com.chalchap7.challengechapter7.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    lateinit var binding : FragmentSplashBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater
        )
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val currentUser = auth.currentUser

//        Handler(Looper.myLooper()!!).postDelayed({
//            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
//        }, 3000)

        Handler(Looper.myLooper()!!).postDelayed({
            if (currentUser != null) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }, 3000)
    }
}
@file:Suppress("KDocUnresolvedReference", "KDocUnresolvedReference")

package com.chalchap7.challengechapter7.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.chalchap7.challengechapter7.R
import com.chalchap7.challengechapter7.databinding.FragmentRegisterBinding
import com.chalchap7.challengechapter7.model.data.User


class RegisterFragment : Fragment() {

    lateinit var binding : FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        firestore = Firebase.firestore
        formValidation()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegist.setOnClickListener {
            binding.registerLoadingScreen.root.visibility = View.VISIBLE
            registerAction()
        }
    }

    private fun formValidation() {
        binding.etConfirmPassword.addTextChangedListener { confirmPassword ->
            if (confirmPassword.toString() != binding.etPassword.text.toString()) {
                binding.btnRegist.isClickable = false
                binding.confirmPasswordTextInputLayout.isEndIconVisible = false
                binding.etConfirmPassword.error = "Confirm password is not match"
            } else {
                binding.btnRegist.isClickable = true
                binding.confirmPasswordTextInputLayout.isEndIconVisible = true
            }
        }

        binding.etPassword.addTextChangedListener { password ->
            if (password!!.length < 6) {
                binding.etPassword.error = "Password must min 6 character"
                binding.passwordTextInputLayout.isEndIconVisible = false
            } else {
                binding.passwordTextInputLayout.isEndIconVisible = true
            }
        }
    }

    private fun registerAction() {
        // accomodate all user credential
        val fullName = binding.etFullName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        val user = User(fullName, email)

        /**
         * Create user action using Firebase Auth
         * @param email is val user email
         * @param password is password user
         */

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.registerLoadingScreen.root.visibility = View.GONE
                val currentUser = auth.currentUser
                val profile = userProfileChangeRequest {
                    displayName = fullName
                }
                currentUser!!.updateProfile(profile)
                firestore.collection("Users").document(fullName).set(user)
                updateUI(currentUser)
            } else {
                binding.registerLoadingScreen.root.visibility = View.GONE
                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
//                Toast.makeText(requireActivity(), task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    /**
     * Update UI when user registered
     * @param currentUser current user instance from create user action
     */

    private fun updateUI(currentUser: FirebaseUser) {
        if (currentUser != null) {
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)

        }
    }


}
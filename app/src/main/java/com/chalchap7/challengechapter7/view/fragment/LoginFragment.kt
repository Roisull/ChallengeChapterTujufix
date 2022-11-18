@file:Suppress("PropertyName", "FunctionName")

package com.chalchap7.challengechapter7.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.chalchap7.challengechapter7.R
import com.chalchap7.challengechapter7.databinding.FragmentLoginBinding

@Suppress("FunctionName")
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code:Int=123
    var firebaseAuth= FirebaseAuth.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        formValidation()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient= GoogleSignIn.getClient(requireActivity(),gso)

        firebaseAuth= FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            loginAction()
        }

        binding.tvSignUp.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.ivGoogle.setOnClickListener {
            signInGoogle()
        }

    }
    private  fun signInGoogle(){

        val signInIntent:Intent=mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,Req_Code)
    }
    // onActivityResult() function : this is where we provide the task and data for the Google Account
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Req_Code){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException){
            Toast.makeText(requireContext(),e.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
                //SavedPreference.setEmail(this,account.email.toString())
                // SavedPreference.setUsername(this,account.displayName.toString())
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(requireContext())!=null){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun formValidation() {
        when {
            binding.etEmail.text?.isEmpty()!! -> binding.btnLogin.isClickable = false
            binding.etPassword.text?.isEmpty()!! -> binding.btnLogin.isClickable = false
            else -> binding.btnLogin.isClickable = true
        }
    }

    private fun loginAction() {
        // Acomodate all user credential
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.toString()
        /**
         * Login action to Firebase Auth
         * @param email is val user email
         * @param password is password user
         */
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }


}
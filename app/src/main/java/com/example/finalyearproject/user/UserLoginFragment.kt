package com.example.finalyearproject.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalyearproject.MainActivity
import com.example.finalyearproject.R
import com.example.finalyearproject.data.AuthViewModel
import com.example.finalyearproject.user.db.Users
import com.example.finalyearproject.databinding.FragmentUserLoginBinding
import com.example.finalyearproject.util.errorDialog
import com.example.finalyearproject.util.hideKeyboard
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class UserLoginFragment : Fragment() {
    private lateinit var binding: FragmentUserLoginBinding
    private val nav by lazy { findNavController() }
    private val auth: AuthViewModel by activityViewModels()
    private val db = Firebase.firestore
    private var fail_count = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentUserLoginBinding.inflate(inflater, container, false)


        binding.btnLogin.setOnClickListener {
            val email = binding.edtLoginEmail.text.toString().trim()
            val password = binding.edtLoginPassword.text.toString().trim()

            login(email,password) }
//        binding.txtForgotPassword.setOnClickListener { nav.navigate(R.id.forgetPasswordFragment) }

        return binding.root
    }

    private fun login(email: String, password: String) {
        hideKeyboard()

        val ctx = requireContext()
        val email = binding.edtLoginEmail.text.toString().trim()
        val password = binding.edtLoginPassword.text.toString().trim()

        lifecycleScope.launch {
            val success = auth.login(ctx, email, password,)
            if (success) {

                db.collection("USERS").whereEqualTo("user_email", email).get()
                    .addOnSuccessListener {
                        if (!it.isEmpty) {
                            val thisUsers = it.toObjects(Users::class.java)
                            val user = thisUsers.first()
                            fail_count = user!!.login_fail_count
                        }
                    }
                if (fail_count < 3) {

                    db.collection("USERS").whereEqualTo("user_email",email).get()
                        .addOnSuccessListener {
                            if (!it.isEmpty) {
                                val thisUsers = it.toObjects(Users::class.java)
                                val user = thisUsers.first()
                                db.collection("USERS").document(user.user_id)
                                    .update("login_fail_count", 0)

                                val intent = Intent(activity, UserHomeFragment::class.java)
                                    .putExtra("userID", user.user_id)
                                startActivity(intent)
                                activity?.finish()

                            }
                        }
                }
                else {

                    login_fail()

                }
            }
            else {
                db.collection("USERS").whereEqualTo("user_email", email).get()
                    .addOnSuccessListener {
                        if (!it.isEmpty) {
                            db.collection("USERS").whereEqualTo("user_email",email)
                                .addSnapshotListener{ value, _ ->
                                    val thisUsers = value?.toObjects(Users::class.java)
                                    val user = thisUsers?.first()
                                    fail_count = user!!.login_fail_count
                                }

                            when (fail_count) {
                                0 -> {
                                    errorDialog("You still got 2 more attempt")
                                }
                                1 -> {
                                    errorDialog("You still got 1 more attempt")
                                }
                                else -> {
                                    errorDialog("Your Account is blocked!!")
                                }
                            }

                        }
                        else {
                            login_fail()
                        }
                    }




                db.collection("USERS").whereEqualTo("user_email",email).get()
                    .addOnSuccessListener {
                        if(!it.isEmpty){
                            val thisUsers = it.toObjects(Users::class.java)
                            val user = thisUsers.first()
                            db.collection("USERS").document(user.user_id)
                                .update("login_fail_count", FieldValue.increment(1))
                        }

                    }

            }
        }

    }

    private fun login_fail(){
        errorDialog("Invalid Login credentials")
    }

}
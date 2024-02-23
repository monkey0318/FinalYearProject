//package com.example.finalyearproject.login
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.navigation.fragment.navArgs
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//import com.example.finalyearproject.data.User
//import com.example.finalyearproject.databinding.FragmentResetPasswordBinding
//import androidx.navigation.fragment.findNavController
//import com.google.android.material.snackbar.Snackbar
//
//class ResetPasswordFragment : Fragment() {
//
//    private lateinit var binding: FragmentResetPasswordBinding
//    private val db = Firebase.firestore
//    private val args: ResetPasswordFragmentArgs by navArgs()
//    private val nav by lazy { findNavController() }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
//
//        val email = args.email
//
//
//        binding = FragmentResetPasswordBinding.inflate(inflater, container,false)
//
//        binding.btnPassword.setOnClickListener {
//            val newpassword = binding.edtNewPassword.text.toString().trim()
//            val confirmpassword = binding.edtConfirmPassword.text.toString().trim()
//
//            if (!newpassword.isEmpty()) {
//
//                if (newpassword == confirmpassword) {
//
//                    db.collection("STAFF").whereEqualTo("user_email", email).get()
//                        .addOnSuccessListener {
//                            if (!it.isEmpty) {
//                                val thisUsers = it.toObjects(User::class.java)
//                                val user = thisUsers.first()
//                                db.collection("STAFF").document(user.user_id)
//                                    .update("user_password", confirmpassword)
//
//                                db.collection("STAFF").document(user.user_id)
//                                    .update("login_fail_count", 0)
//
//                                nav.navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment2())
//                            }
//                        }
//                } else {
//                    snackbar("Confirmation Password Incorrect")
//                    binding.edtNewPassword.requestFocus()
//                }
//            }
//            else {
//                snackbar("Please enter new password")
//                binding.edtNewPassword.requestFocus()
//            }
//        }
//
//
//        return binding.root
//    }
//
//    private fun snackbar(text: String) {
//        Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
//    }
//
//}
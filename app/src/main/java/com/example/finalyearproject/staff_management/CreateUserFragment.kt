package com.example.finalyearproject.staff_management

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalyearproject.data.User
import com.example.finalyearproject.data.UserViewModel
import com.example.finalyearproject.databinding.FragmentCreateUserBinding
import com.example.finalyearproject.util.cropToBlob
import com.example.finalyearproject.util.errorDialog
import com.example.finalyearproject.util.snackbar
import com.google.firebase.auth.FirebaseAuth

class CreateUserFragment : Fragment() {

    private lateinit var binding: FragmentCreateUserBinding
    private val nav by lazy { findNavController() }
    private val vm: UserViewModel by activityViewModels()
    private lateinit var mAuth : FirebaseAuth


    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            binding.imgPhoto.setImageURI(it.data?.data)
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentCreateUserBinding.inflate(inflater, container, false)

        reset()
        binding.imgPhoto.setOnClickListener { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener {
            val email   = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            submit(email, password) }


        return binding.root
    }

    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)

    }

    private fun submit(email: String, password: String) {
        val u = User(
            user_id       = binding.edtId.text.toString().trim().uppercase(),
            user_name     = binding.edtName.text.toString().trim(),
            user_email    = binding.edtEmail.text.toString().trim(),
            user_password = binding.edtPassword.text.toString().trim(),
            user_address  = binding.edtAddress.text.toString().trim(),
            user_photo    = binding.imgPhoto.cropToBlob(300,300),
            user_role     = binding.spRole.selectedItem as String
        )


        val err = vm.validate(u)
        if (err != "") {
            errorDialog(err)
            return
        }

        vm.set(u)
        snackbar("Staff added successfully")
        nav.navigateUp()
    }

    private fun reset() {
        binding.edtEmail.text.clear()
        binding.edtName.text.clear()
        binding.edtPassword.text.clear()
        binding.imgPhoto.setImageDrawable(null)
        binding.edtId.requestFocus()
    }

}
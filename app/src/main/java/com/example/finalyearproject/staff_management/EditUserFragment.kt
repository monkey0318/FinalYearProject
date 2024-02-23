package com.example.finalyearproject.staff_management

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.finalyearproject.data.User
import com.example.finalyearproject.data.UserViewModel
import com.example.finalyearproject.databinding.FragmentEditUserBinding
import com.example.finalyearproject.util.cropToBlob
import com.example.finalyearproject.util.errorDialog
import com.example.finalyearproject.util.snackbar
import com.example.finalyearproject.util.toBitmap
import java.text.SimpleDateFormat
import java.util.*

class EditUserFragment : Fragment() {

    private lateinit var binding: FragmentEditUserBinding
    private val nav by lazy { findNavController() }
    private val vm: UserViewModel by activityViewModels()
    private val db = Firebase.firestore

    private val id by lazy { requireArguments().getString("id") ?: "" }
    private val formatter = SimpleDateFormat("dd MMMM yyyy '-' hh:mm:ss a", Locale.getDefault())

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgPhoto.setImageURI(it.data?.data)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentEditUserBinding.inflate(inflater,container,false)

        reset()
        binding.imgPhoto.setOnClickListener { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnConfirm.setOnClickListener { submit() }
        binding.btnDelete.setOnClickListener { delete() }

        return binding.root
    }



    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        val u = vm.get(id)
        if (u == null) {
            nav.navigateUp()
            return
        }
        load(u)
    }

    private fun load(u: User) {
        binding.txtId.text = u.user_id
        binding.txtUserRole.text = u.user_role
        binding.edtPassword.setText(u.user_password)
        binding.edtName.setText(u.user_name)
        binding.edtEmail.setText(u.user_email)
        binding.edtAddress.setText(u.user_address)
        binding.imgPhoto.setImageBitmap(u.user_photo.toBitmap())
        binding.txtDate.text = formatter.format(u.date)
        binding.edtName.requestFocus()
    }

    private fun submit() {

        val u = User(
            user_id = id,
            user_name = binding.edtName.text.toString().trim(),
            user_email = binding.edtEmail.text.toString().trim(),
            user_password = binding.edtPassword.text.toString(),
            user_role = binding.txtUserRole.text.toString(),
            user_address = binding.edtAddress.text.toString().trim(),
            user_photo = binding.imgPhoto.cropToBlob(300,300)
        )
        val err = vm.validate(u,false)
        if (err != "") {
            errorDialog(err)
            return
        }

        vm.set(u)
        snackbar("Staff updated successfully")
        nav.navigateUp()

    }

    private fun delete() {
        vm.delete(id)
        nav.navigateUp()

    }
}
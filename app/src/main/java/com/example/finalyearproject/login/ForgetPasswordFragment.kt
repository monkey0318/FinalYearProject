package com.example.finalyearproject.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalyearproject.databinding.FragmentForgetPasswordBinding
import com.example.finalyearproject.util.PasswordRecoveryEmail
import com.example.finalyearproject.util.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat


class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private val nav by lazy { findNavController() }

    private val n = (0..999999).random()
    private val fmt = DecimalFormat("000000")
    private val veriCode = fmt.format(n)

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {


        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        binding.btnSend.setOnClickListener { send() }
        binding.btnVerify.setOnClickListener { verify() }


        return binding.root

    }

    private fun verify() {
        hideKeyboard()
        val vcode = binding.edtVerifyCode.text.toString().trim()
        if (vcode != veriCode){
            snackbar("Invalid Code")
            binding.btnVerify.requestFocus()
            return
        }
        else {
            val email = binding.edtEmail.text.toString().trim()
            nav.navigate(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToResetPasswordFragment(email))
        }
    }

    private fun send() {
        hideKeyboard()
        val email = binding.edtEmail.text.toString().trim()
        if (!isEmail(email)) {
            snackbar("Invalid email")
            binding.btnSend.requestFocus()
            return
        }


        val subject = "Password Verification Code - ${System.currentTimeMillis()}"
        val content = """
            <p> Your Verification Code is: </p>
            <h1 style ="color: black">$veriCode</h1>
            <p> Please contact the Human Resource Department if the action is not requested by you.</p>
        """.trimIndent()

        PasswordRecoveryEmail()
            .to(email)
            .subject(subject)
            .content(content)
            .isHtml()
            .send() {
                snackbar("Verification Code Sent")
                binding.btnSend.isEnabled = true
                binding.btnSend.requestFocus()
                binding.textView10.isVisible = true
                binding.edtVerifyCode.isVisible = isVisible
                binding.edtVerifyCode.isEnabled = true
                binding.btnVerify.isEnabled = true
                binding.btnVerify.isVisible = true
            }

        snackbar("Sending...")
        binding.btnSend.isEnabled = false
    }

    private fun isEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun snackbar(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
    }




}
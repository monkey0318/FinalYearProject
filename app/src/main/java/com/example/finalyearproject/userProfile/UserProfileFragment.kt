package com.example.finalyearproject.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.finalyearproject.data.ActivityViewModel
import com.example.finalyearproject.databinding.FragmentUserProfileBinding
import com.example.finalyearproject.util.toBitmap

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private val actVM: ActivityViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        binding.imgProfilePhoto.setImageBitmap(actVM.userMutableLiveData.value?.user_photo?.toBitmap())
        binding.nameTextview.text = actVM.userMutableLiveData.value?.user_name
        binding.txtProfileId.text = actVM.userMutableLiveData.value?.user_id
        binding.emailTextview.text = actVM.userMutableLiveData.value?.user_email
        binding.addressTextview.text = actVM.userMutableLiveData.value?.user_address
        binding.txtProfileRole.text = actVM.userMutableLiveData.value?.user_role

        return binding.root
    }

}
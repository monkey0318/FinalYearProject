//package com.example.finalyearproject.communication
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.SearchView
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.DividerItemDecoration
//import com.example.finalyearproject.data.ActivityViewModel
//import com.example.finalyearproject.data.AuthViewModel
//import com.example.finalyearproject.data.LiveChatViewModel
//import com.example.finalyearproject.data.LiveChatViewModelFactory
//import com.example.finalyearproject.databinding.FragmentLiveChatBinding
//import com.example.finalyearproject.util.LiveChatUserAdapter
//
//
//class LiveChatFragment : Fragment() {
//
//    private lateinit var binding: FragmentLiveChatBinding
//    private val nav by lazy { findNavController() }
//    private val auth: ActivityViewModel by activityViewModels()
//    private lateinit var viewModel: LiveChatViewModel
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
//
//        binding = FragmentLiveChatBinding.inflate(inflater, container, false)
//
//        val adapter = LiveChatUserAdapter() { holder, customer ->
//            holder.root.setOnClickListener {
//                nav.navigate(LiveChatFragmentDirections.actionLiveChatFragmentToChatFragment(customer.id))
//            }
//        }
//        binding.rvChatUser.adapter = adapter
//        binding.rvChatUser.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
//
//
//
//        val viewModelFactory = LiveChatViewModelFactory(auth.userMutableLiveData.value!!.user_id)
//
//
//        viewModel = ViewModelProvider(this, viewModelFactory).get(LiveChatViewModel::class.java)
//
//
//
//        viewModel.getResult().observe(viewLifecycleOwner) { customers ->
//            adapter.submitList(customers)
//            binding.txtCount.text = "${customers.size} Record(s)"
//        }
//
//        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(name: String) = true
//            override fun onQueryTextChange(name: String): Boolean {
//
//                viewModel.search(name)
//                return true
//            }
//        })
//
//        return binding.root
//    }
//
//}

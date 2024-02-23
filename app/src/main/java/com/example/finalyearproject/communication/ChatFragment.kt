//package com.example.finalyearproject.communication
//
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.navArgs
//import androidx.recyclerview.widget.DividerItemDecoration
//import com.example.finalyearproject.data.ActivityViewModel
//import com.example.finalyearproject.data.ChatViewModel
//import com.example.finalyearproject.data.ChatViewModelFactory
//import com.example.finalyearproject.databinding.FragmentChatBinding
//import com.example.finalyearproject.util.ChatMessageAdapter
//
//
//class ChatFragment : Fragment() {
//
//    private lateinit var binding: FragmentChatBinding
//    private val auth: ActivityViewModel by activityViewModels()
//    private val args: ChatFragmentArgs by navArgs()
//    private lateinit var viewModel: ChatViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
//
//        binding = FragmentChatBinding.inflate(inflater, container, false)
//
//        val adapter = ChatMessageAdapter(auth.userMutableLiveData.value!!.user_id) { holder, customer ->
//        }
//
//        binding.rvChatDialog.adapter = adapter
//        binding.rvChatDialog.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
//
//        val viewModelFactory = ChatViewModelFactory(auth.userMutableLiveData.value!!.user_id,args.customerId)
//
//        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)
//        viewModel.messages.observe(viewLifecycleOwner){
//            adapter.submitList(it)
//        }
//
//        binding.flactbtnSend.setOnClickListener{
//            viewModel.addMessage(binding.edtChat.text.toString())
//            reset()}
//        return binding.root
//    }
//
//    private fun reset() {
//        binding.edtChat.text.clear()
//    }
//
//}
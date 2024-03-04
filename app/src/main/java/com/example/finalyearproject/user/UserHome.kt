package com.example.finalyearproject.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.finalyearproject.R
import com.example.finalyearproject.user.db.UserActivityViewModel
import com.example.finalyearproject.user.db.AuthViewModel
import com.example.finalyearproject.user.db.Users
import com.example.finalyearproject.databinding.ActivityUserHomeBinding
import com.example.finalyearproject.login.LoginActivity
import com.example.finalyearproject.util.toBitmap

class UserHome : AppCompatActivity() {
    private lateinit var binding: ActivityUserHomeBinding
    private val nav by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host)!!.findNavController()
    }
    private val auth: AuthViewModel by viewModels()
    private val actVM: UserActivityViewModel by viewModels()

    private lateinit var abc: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userID = intent.getStringExtra("userID")

        abc = AppBarConfiguration(
            setOf(
                R.id.staffManagementFragment,
                R.id.sponsorshipFragment,
                R.id.userProfileFragment,
                R.id.eventManagementFragment2,
//                R.id.reportFragment,
                R.id.voucherFragment,
                R.id.liveChatFragment,
                R.id.faqFragment,
//                R.id.QRScannerFragment,
                R.id.feedbackFragment,
                R.id.invitationFragment,
                R.id.paymentAcknowledgeFragment,

                ),
            binding.drawerLayout
        )

        setupActionBarWithNavController(nav, abc)
        binding.navView.setupWithNavController(nav)

        actVM.userMutableLiveData.observe(this) { user ->
            binding.navView.menu.clear()
            val h = binding.navView.getHeaderView(0)
            binding.navView.removeHeaderView(h)

            binding.navView.inflateMenu(R.menu.drawer_login)
            binding.navView.inflateHeaderView(R.layout.login_header)
            binding.navView.getHeaderView(0).setOnClickListener {
                nav.navigate(R.id.userProfileFragment)
            }
            if (user.user_role == "Staff") {
                binding.navView.menu.findItem(R.id.staffManagementFragment).isVisible = false
            }
            setHeader(user)
            binding.navView.menu.findItem(R.id.logout)
                ?.setOnMenuItemClickListener { logout(); true }
        }
        actVM.getUser(userID!!)
    }

    private fun setHeader(user: Users) {
        val h = binding.navView.getHeaderView(0)

        val photo: ImageView = h.findViewById(R.id.photo)
        val name: TextView = h.findViewById(R.id.name)
        val email: TextView = h.findViewById(R.id.email)

        photo.setImageBitmap(user.user_photo?.toBitmap())
        name.text = user.user_name
        email.text = user.user_email
    }

    private fun logout() {
        auth.logout(this)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

        binding.drawerLayout.close()

    }

    override fun onSupportNavigateUp(): Boolean {

        return nav.navigateUp(abc) || super.onSupportNavigateUp()
    }
}

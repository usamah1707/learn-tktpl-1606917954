package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        binding.logoutButton.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(applicationContext, MainActivity::class.java))
            Toast.makeText(applicationContext, "Berhasil Logout", Toast.LENGTH_SHORT).show()
            finish()
        })
    }
}
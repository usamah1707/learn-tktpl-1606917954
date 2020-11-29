package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.ActivityDashboardBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.auth.AuthViewModel
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.auth.LoginActivity


class DashboardActivity : AppCompatActivity() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDashboardBinding>(
            this,
            R.layout.activity_dashboard
        )
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        auth = Firebase.auth
        fStore = FirebaseFirestore.getInstance()
        var drawerLayout = binding.drawerLayout


        binding.logoutButton.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            Toast.makeText(applicationContext, "Berhasil Logout", Toast.LENGTH_SHORT).show()
            finish()
        })

        var documentReference : DocumentReference = fStore.collection("users").document(auth.currentUser!!.uid)
        documentReference.addSnapshotListener(EventListener<DocumentSnapshot>
        { documentSnapshot: DocumentSnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
            var name: String? = documentSnapshot?.getString("full_name")
            binding.greeting.text = getString(R.string.greeting, name)
        })

        val navHostFragment= supportFragmentManager.findFragmentById(R.id.fragment_profile) as NavHostFragment?
        val navController = navHostFragment?.navController

        navController?.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

    }
}

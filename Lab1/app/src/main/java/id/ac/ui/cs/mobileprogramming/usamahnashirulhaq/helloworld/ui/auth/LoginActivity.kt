package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.auth

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.ActivityLoginBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.home.DashboardActivity
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.util.toast


class LoginActivity : AppCompatActivity(), AuthListener {
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var binding: ActivityLoginBinding
    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        // Initialize Firebase Auth
        auth = Firebase.auth
        // Initialize AuthViewModel
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.authViewModel = authViewModel

        authViewModel.authListener = this


        email = binding.loginEmailField
        password = binding.loginPasswordField
        progressBar = binding.loginProgressBar

        if (auth.currentUser != null) {
            startActivity(Intent(applicationContext, DashboardActivity::class.java))
        }

        coordinatorLayout = binding.coordinator
        animationDrawable = coordinatorLayout.background as AnimationDrawable

        animationDrawable.setEnterFadeDuration(5000)
        animationDrawable.setExitFadeDuration(2000)

        binding.register.setOnClickListener(View.OnClickListener {
            RegisterFragment().show(supportFragmentManager, "Dialog")
        })

    }

    override fun onPause() {
        super.onPause()
        if (animationDrawable.isRunning) {
            animationDrawable.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!animationDrawable.isRunning) {
            animationDrawable.start()
        }

    }

    override fun loginFieldCheck(email:String?, password:String?): Boolean {
        if (email.isNullOrEmpty()) {
            this.email.setError("Email Tidak Boleh Kosong.")
            return true
        }
        if (password.isNullOrEmpty()) {
            this.password.setError("Password Tidak Boleh Kosong.")
            return true
        }
        return false
    }

    override fun onStarted(email:String, password:String) {

        progressBar.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure("Log in gagal, Email atau Password yang anda masukan salah.")
                }
            }
    }

    override fun onSuccess() {
        Log.d("Log In Activity", "LogInWithPhoneNumber:success")
        toast("Log In Berhasil")
        startActivity(Intent(this, DashboardActivity::class.java))
        progressBar.visibility = View.INVISIBLE
    }

    override fun onFailure(message: String) {
        Log.w("Log In Activity", "LogInWithEmail:failure")
        toast(message)
        progressBar.visibility = View.INVISIBLE
    }

    //Not Implemented here
    override fun registerFieldCheck(email: String?, password: String?, name: String?): Boolean {
        TODO("No Need to Implemented here")
    }
}


package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var binding: ActivityMainBinding
    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Initialize Firebase Auth
        auth = Firebase.auth
        email = binding.loginEmailField
        password = binding.loginPasswordField
        progressBar = binding.loginProgressBar
        loginButton = binding.buttonLogin

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


        loginButton.setOnClickListener(View.OnClickListener {
            var email: String = this.email.text.toString().trim()
            var password: String = this.password.text.toString().trim()
            if (TextUtils.isEmpty(email)) {
                this.email.setError("Email Tidak Boleh Kosong.")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                this.password.setError("Password Tidak Boleh Kosong.")
                return@OnClickListener
            }
            if (password.length < 6) {
                this.password.setError("Password Minimal 6 Karakter.")
                return@OnClickListener
            }
            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        // Log In success, update UI with the signed-in user's information
                        Log.d("Log In Activity", "LogInWithPhoneNumber:success")
                        Toast.makeText(
                            this, "Log in berhasil",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this, DashboardActivity::class.java))
                        progressBar.visibility = View.INVISIBLE
                    } else {
                        // If Log In fails, display a message to the user.
                        Log.w("Log In Activity", "LogInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this, "Log in gagal, Email atau Password yang anda masukan salah.",
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBar.visibility = View.INVISIBLE
                    }
                }
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

}


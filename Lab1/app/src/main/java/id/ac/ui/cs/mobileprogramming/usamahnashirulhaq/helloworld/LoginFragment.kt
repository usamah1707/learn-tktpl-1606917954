package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.BottomSheetLoginBinding

class LoginFragment : BottomSheetDialogFragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<BottomSheetLoginBinding>(
            inflater,
            R.layout.bottom_sheet_login,
            container,
            false
        )
        auth = Firebase.auth

        email = binding.loginEmailField
        password = binding.loginPasswordField
        progressBar = binding.loginProgressBar

        binding.login.setOnClickListener { View.OnClickListener {
            var email: String = this.email.text.toString().trim()
            var password: String = this.password.text.toString().trim()
            if (TextUtils.isEmpty(email)) {
                this.email.setError("Nomer Telepon Tidak Boleh Kosong.")
                Toast.makeText(context, "Works phone", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                this.password.setError("Password Tidak Boleh Kosong.")
                Toast.makeText(context, "Works password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (password.length < 6) {
                this.password.setError("Password Minimal 6 Karakter.")
                Toast.makeText(context, "Works password length", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // Log In success, update UI with the signed-in user's information
                    Log.d("Log In Activity", "LogInWithPhoneNumber:success")
                    Toast.makeText(
                        context, "Log in berhasil",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(context, DashboardActivity::class.java))
                    progressBar.visibility = View.INVISIBLE
                } else {
                    // If Log In fails, display a message to the user.
                    Log.w("Log In Activity", "LogInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Log in gagal.",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = View.INVISIBLE
                }
            }
        } }

        return binding.root
    }


}
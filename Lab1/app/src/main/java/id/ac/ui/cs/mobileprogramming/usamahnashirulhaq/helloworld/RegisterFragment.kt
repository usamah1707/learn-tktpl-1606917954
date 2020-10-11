package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.content.Intent
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
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.BottomSheetRegisterBinding


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : BottomSheetDialogFragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var name: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<BottomSheetRegisterBinding>(
            inflater,
            R.layout.bottom_sheet_register,
            container,
            false
        )
        auth = Firebase.auth

        if(auth.currentUser != null){
            startActivity(Intent(context, DashboardActivity::class.java))
        }

        email = binding.emailRegisterField
        password = binding.passwordRegisterField
        name = binding.nameRegisterField
        progressBar = binding.registerProgressBar

        binding.registerButton.setOnClickListener(View.OnClickListener {
            var email: String = this.email.text.toString().trim()
            var name: String = this.name.text.toString().trim()
            var password: String = this.password.text.toString().trim()
            if (TextUtils.isEmpty(name)) {
                this.name.setError("Nama Tidak Boleh Kosong.")
                Toast.makeText(context, "Works name", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
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


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Register Activity", "createUserWithPhoneNumber:success")
                        startActivity(Intent(context, DashboardActivity::class.java))
                        progressBar.visibility = View.INVISIBLE


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Register Activity", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            context, "Authentication gagal.",
                            Toast.LENGTH_SHORT
                        ).show()
                       progressBar.visibility = View.INVISIBLE
                    }
                }


        })

        return binding.root

    }
}

package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.auth

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
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.BottomSheetRegisterBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.home.DashboardActivity
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.util.toast
import kotlin.collections.HashMap


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : BottomSheetDialogFragment(), AuthListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var name: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var fStore: FirebaseFirestore
    private lateinit var userID: String
    private lateinit var authViewModel: AuthViewModel

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
        fStore = FirebaseFirestore.getInstance()
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.authViewModel = authViewModel

        authViewModel.authListener = this

        email = binding.emailRegisterField
        password = binding.passwordRegisterField
        name = binding.nameRegisterField
        progressBar = binding.registerProgressBar

        return binding.root

    }

    override fun registerFieldCheck(email: String?, password: String?, name: String?): Boolean {
        if (name.isNullOrEmpty()) {
            this.name.setError("Nama Tidak Boleh Kosong.")
            return true
        }
        if (email.isNullOrEmpty()) {
            this.email.setError("Email Tidak Boleh Kosong.")
            return true
        }

        if (password.isNullOrEmpty()) {
            this.password.setError("Password Tidak Boleh Kosong.")
            return true
        }
        if (password.length < 6) {
            this.password.setError("Password harus lebih dari 6 huruf.")
            return true
        }
        return false
    }

    override fun onStarted(email: String, password: String) {
        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure("Registrasi gagal. Harap coba lagi.")
                }
            }
    }

    override fun onSuccess() {
        // Sign in success, update UI with the signed-in user's information
        Log.d("Register Activity", "createUserWithEmail:success")

        userID = auth.currentUser!!.uid
        var documentReference: DocumentReference = fStore.collection("users").document(userID)
        var user: HashMap<String, String> = HashMap<String, String>()

        user.put("full_name", this.name.text.toString())
        user.put("email", this.email.text.toString())
        user.put("password", this.password.text.toString())

        documentReference.set(user).addOnSuccessListener {
            Log.d("Storing Data Activity", "Succes to storing user data")
            context?.toast("success creating user with ID: " + userID)
        }

        startActivity(Intent(context, DashboardActivity::class.java))
        progressBar.visibility = View.INVISIBLE
    }

    override fun onFailure(message: String) {
        // If sign in fails, display a message to the user.
        Log.w("Register Activity", "createUserWithEmail:failure")
        context?.toast(message)
        progressBar.visibility = View.INVISIBLE
    }

    //Not Implemented here
    override fun loginFieldCheck(email: String?, password: String?): Boolean {
        TODO("No Need to Implemented here")
    }
}

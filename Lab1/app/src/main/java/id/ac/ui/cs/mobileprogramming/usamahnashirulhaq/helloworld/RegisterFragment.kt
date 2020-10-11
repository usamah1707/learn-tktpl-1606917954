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
import androidx.fragment.app.FragmentManager
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.BottomSheetRegisterBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap


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
    private lateinit var fStore : FirebaseFirestore
    private lateinit var userID : String

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
                return@OnClickListener
            }
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


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Register Activity", "createUserWithEmail:success")

                        userID = auth.currentUser!!.uid
                        var documentReference: DocumentReference = fStore.collection("users").document(userID)
                        var user : HashMap<String, String> = HashMap<String, String>()

                        user.put("full_name", this.name.text.toString())
                        user.put("email", this.email.text.toString())
                        user.put("password", this.password.text.toString())

                        documentReference.set(user).addOnSuccessListener {
                            Log.d("Storing Data Activity", "Succes to storing user data")
                            Toast.makeText(context, "success creating user with ID: "+userID, Toast.LENGTH_LONG+Toast.LENGTH_LONG).show()
                            Toast.makeText(context, "long: " + Toast.LENGTH_LONG as String, Toast.LENGTH_LONG)
                        }

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

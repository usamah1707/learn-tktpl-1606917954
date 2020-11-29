package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.FragmentProfileBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.util.toast


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(){
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater, R.layout.fragment_profile, container, false
        )
        auth = Firebase.auth
        fStore = FirebaseFirestore.getInstance()

        var documentReference: DocumentReference =
            fStore.collection("users").document(auth.currentUser!!.uid)
        documentReference.addSnapshotListener(EventListener<DocumentSnapshot>
        { documentSnapshot: DocumentSnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
            var name: String? = documentSnapshot?.getString("full_name")
            var email: String? = documentSnapshot?.getString("email")
            binding.tvProfileName.text = name
            binding.tvProfileEmail.text = email

        })

        binding.surpriseButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_fragment_profile_to_fragment_suprise)
        }

        return binding.root
    }
}
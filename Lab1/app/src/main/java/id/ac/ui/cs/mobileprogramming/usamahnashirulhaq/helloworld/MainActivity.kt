package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.R.attr.password
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Initialize Firebase Auth
        auth = Firebase.auth

        coordinatorLayout = binding.coordinator
        animationDrawable = coordinatorLayout.background as AnimationDrawable

        animationDrawable.setEnterFadeDuration(5000)
        animationDrawable.setExitFadeDuration(2000)

        //get the bottom sheet view
        var linearLayout: LinearLayout = findViewById(R.id.bottom_sheet_login)

        //init the bottom sheet view
        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(linearLayout)

        binding.login.setOnClickListener(View.OnClickListener() {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        })

        val linearlayoutRegister: LinearLayout = findViewById(R.id.bottom_sheet_register)
        linearlayoutRegister.setBackgroundResource(R.drawable.shape_bottom_sheet_register)

        binding.register.setOnClickListener(View.OnClickListener {
            CustomBottomSheetDialogFragment().show(
                supportFragmentManager,
                "Dialog"
            )
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
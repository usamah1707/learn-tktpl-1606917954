package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.R
import androidx.databinding.DataBindingUtil
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ivIntro: ImageView
    private lateinit var tvIntroUpper: TextView
    private lateinit var tvIntroLower: TextView
    private lateinit var btnStarted: Button
    private lateinit var btnExit: ImageButton
    private lateinit var imageViewAnim: Animation
    private lateinit var textViewAnim: Animation
    private lateinit var btnGetStartedAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ivIntro = binding.ivIntro
        tvIntroUpper = binding.tvIntroUpper
        tvIntroLower = binding.tvIntroLower
        btnStarted = binding.btnStarted
        btnExit = binding.btnexit

        //load animations
        imageViewAnim = AnimationUtils.loadAnimation(this, R.anim.image_view_anim)
        textViewAnim = AnimationUtils.loadAnimation(this, R.anim.textview_anim)
        btnGetStartedAnim = AnimationUtils.loadAnimation(this, R.anim.btn_getstarted_anim)

        //implement animation
        ivIntro.startAnimation(imageViewAnim)
        tvIntroUpper.startAnimation(textViewAnim)
        tvIntroLower.startAnimation(textViewAnim)
        btnStarted.startAnimation(btnGetStartedAnim)

        //button action
        btnStarted.setOnClickListener(View.OnClickListener {
            var intent: Intent = Intent(this, StopWatchAct::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            Log.i("MainActivity","Button Clicked")
        })

        btnExit.setOnClickListener(View.OnClickListener {
            Log.i("MainActivity","Exit Button Clicked")
            finish()
            System.exit(0)
        })




        //import font
        var MLight: Typeface =  Typeface.createFromAsset(assets, "fonts/MLight.ttf")
        var MRegular: Typeface =  Typeface.createFromAsset(assets, "fonts/MRegular.ttf")

        //implement font
        tvIntroUpper.setTypeface(MRegular)
        tvIntroLower.setTypeface(MLight)
        btnStarted.setTypeface(MRegular)


    }

    override fun onBackPressed() {
        Toast.makeText(this,"Back button can\'t be used", Toast.LENGTH_SHORT).show()
        Log.i("MainActivity","Back is Pressed")
    }


}
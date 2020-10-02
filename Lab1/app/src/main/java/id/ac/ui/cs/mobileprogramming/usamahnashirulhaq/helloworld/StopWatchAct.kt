package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.R
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.databinding.ActivityStopwatchAppBinding


class StopWatchAct : AppCompatActivity() {
    private lateinit var binding: ActivityStopwatchAppBinding
    private lateinit var ivCircle: ImageView
    private lateinit var ivAnchor: ImageView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnExit: ImageButton
    private lateinit var roundingAnim: Animation
    private lateinit var introAnim: Animation
    private lateinit var btnAnim: Animation
    private lateinit var chronometer: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("StopWatchAct", "StopWatch Created")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stopwatch_app)

        ivCircle = binding.ivCircle
        ivAnchor = binding.ivAnchor
        btnStart = binding.btnStarted
        btnStop = binding.btnStop
        chronometer = binding.chronometer
        btnExit = binding.btnexit

        //load animations
        introAnim = AnimationUtils.loadAnimation(this, R.anim.image_view_anim)
        roundingAnim = AnimationUtils.loadAnimation(this, R.anim.rounding_anim)
        btnAnim = AnimationUtils.loadAnimation(this, R.anim.btn_time_anim)

        //import font
        var MRegular: Typeface =  Typeface.createFromAsset(assets, "fonts/MRegular.ttf")

        //implement font
        btnStart.setTypeface(MRegular)
        btnStop.setTypeface(MRegular)

        //implement animation
        ivCircle.startAnimation(introAnim)
        ivAnchor.startAnimation(introAnim)
        chronometer.startAnimation(introAnim)
        btnStart.startAnimation(btnAnim)
        btnStop.alpha = 0F



        btnStart.setOnClickListener(View.OnClickListener {
            //implement animation
            btnStart.alpha = 0F
            btnStart.isEnabled = false
            ivAnchor.startAnimation(roundingAnim)
            btnStop.alpha = 1F
            btnStop.startAnimation(btnAnim)


            //start time
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
        })

        btnStop.setOnClickListener(View.OnClickListener {
            //implement animation
            btnStart.alpha = 1F
            btnStart.isEnabled = true
            ivAnchor.clearAnimation()
            btnStop.alpha = 0F
            btnStart.startAnimation(btnAnim)


            //start time
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.stop()
        })


        btnExit.setOnClickListener(View.OnClickListener {
            Log.i("StopWatchAct", "Exit Button Clicked")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
        })
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Back button can\'t be used", Toast.LENGTH_SHORT).show()
        Log.i("MainActivity", "Back is Pressed")
    }
}
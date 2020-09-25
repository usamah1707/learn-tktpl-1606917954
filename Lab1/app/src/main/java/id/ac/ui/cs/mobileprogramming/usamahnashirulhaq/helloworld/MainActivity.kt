package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.R
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dateView.setText(getTime())
    }

    private val FORMAT: SimpleDateFormat = SimpleDateFormat("'Its' HH:mm, EEEE, d MMM yyyy")

    fun getTime(): String? {
        return FORMAT.format(Calendar.getInstance().getTime()).toString()
    }
}
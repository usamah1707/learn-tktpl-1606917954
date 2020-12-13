package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var ctr = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var counter: TextView = counter

        floatingActionButton.setOnClickListener {
            ctr++
            counter.text = ctr.toString()
        }
    }
}
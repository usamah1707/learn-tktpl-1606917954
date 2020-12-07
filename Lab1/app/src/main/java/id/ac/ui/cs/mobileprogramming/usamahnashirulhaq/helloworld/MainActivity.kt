package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.R
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var indeks:Long = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.addButton.setOnClickListener {
            indeks = Increment(indeks)
            binding.tvCounter.text = indeks.toString()
        }

        binding.minusButton.setOnClickListener {
            indeks = Decrement(indeks)
            binding.tvCounter.text = indeks.toString()
        }

        binding.multiplyButton.setOnClickListener {
            indeks = MultiplyBy2(indeks)
            binding.tvCounter.text = indeks.toString()
        }

        binding.resetButton.setOnClickListener {
            indeks = 0L
            binding.tvCounter.text = indeks.toString()
        }

    }

    external fun Increment(x: Long): Long
    external fun Decrement(x: Long): Long
    external fun MultiplyBy2(x: Long): Long

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
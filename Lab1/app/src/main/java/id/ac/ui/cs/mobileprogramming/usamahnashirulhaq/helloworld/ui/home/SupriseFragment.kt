package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.FragmentSupriseBinding


class SupriseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // bind the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSupriseBinding>(
            inflater, R.layout.fragment_suprise, container, false)

        Glide
            .with(context) // replace with 'this' if it's in activity
            .load("https://media.giphy.com/media/W35DnRbN4oDHIAApdk/giphy.gif")
            .asGif()
            .error(R.drawable.not_found_image) // show error drawable if the image is not a gif
            .into(binding.ivGif);

        return binding.root
    }

}
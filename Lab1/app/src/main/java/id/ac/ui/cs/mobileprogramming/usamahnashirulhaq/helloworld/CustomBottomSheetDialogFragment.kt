package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.databinding.BottomSheetRegisterBinding


/**
 * A simple [Fragment] subclass.
 * Use the [CustomBottomSheetDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() {

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

        binding.register.setOnClickListener(View.OnClickListener {
            var phone_number: String = binding.phoneRegisterField.text.toString().trim()
            var name: String = binding.nameRegisterField.text.toString().trim()
            var password: String = binding.passwordRegisterField.text.toString().trim()
            if (TextUtils.isEmpty(phone_number)) {
                binding.phoneRegisterField.setError("Nomer Telepon Tidak Boleh Kosong.")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(name)) {
                binding.nameRegisterField.setError("Nama Tidak Boleh Kosong.")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                binding.passwordRegisterField.setError("Password Tidak Boleh Kosong.")
                return@OnClickListener
            }
        })

        return binding.root

    }

}
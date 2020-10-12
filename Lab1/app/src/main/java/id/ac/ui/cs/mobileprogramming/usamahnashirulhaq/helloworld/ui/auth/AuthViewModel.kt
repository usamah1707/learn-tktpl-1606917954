package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel


class AuthViewModel : ViewModel() {
    var name: String? = null
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun onLoginButtonClick(view: View){
        if (authListener?.loginFieldCheck(email,password)!!){
            return
        }
        authListener?.onStarted(email!!,password!!)
    }

    fun onRegisterButtonClick(view: View){
        if (authListener?.registerFieldCheck(email,password,name)!!){
            return
        }
        authListener?.onStarted(email!!,password!!)
    }
}
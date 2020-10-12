package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld.ui.auth

interface AuthListener {
    fun registerFieldCheck(email:String?, password: String?, name: String?):Boolean
    fun loginFieldCheck(email:String?, password: String?):Boolean
    fun onStarted(email:String, password:String)
    fun onSuccess()
    fun onFailure(message: String)
}
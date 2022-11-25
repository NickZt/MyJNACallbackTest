package ua.zt.mezon.myjnacallbacktest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel(), JNIListener {

    init {
        nsubscribeListener(this)
    }

    val stringMessage = MutableLiveData<String>().apply { postValue("EMPTY") }
    val intMessage = MutableLiveData<Int>().apply { postValue(0) }
    fun onDestroyMain() {
        ndismissListener()
    }

    override fun onAcceptMessage(string: String) {
        stringMessage.postValue(string)
    }

    override fun onAcceptMessageVal(msgInt: Int) {
        intMessage.postValue(msgInt)
    }

    fun sendDataToJna(msg: String) {
        nonNextListener(msg)
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application. Its right p
     */
    private external fun nsubscribeListener(jnilistener: JNIListener)
    private external fun nonNextListener(message: String)
    private external fun ndismissListener()

}
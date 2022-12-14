package ua.zt.mezon.myjnacallbacktest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel(), JNAMediator {
    var stringMessage by mutableStateOf("EMPTY")
    init {
        nsubscribeListener(this)
    }

    var intMessage by mutableStateOf(0)
    fun onDestroyMain() {
        ndismissListener()
    }

    override fun onAcceptMessage(string: String) {
        stringMessage= string
    }

    override fun onAcceptMessageVal(msgInt: Int) {
        intMessage=msgInt
    }

    fun sendDataToJna(msg: String) {
        nonNextListener(msg)
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application. Its right p
     */
    private external fun nsubscribeListener(jnilistener: JNAMediator)
    private external fun nonNextListener(message: String)
    private external fun ndismissListener()

}
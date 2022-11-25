package ua.zt.mezon.myjnacallbacktest

interface JNIListener {
    fun onAcceptMessage(string: String)
    fun onAcceptMessageVal(messVal: Int)
}
package ua.zt.mezon.myjnacallbacktest

interface JNAMediator {
    fun onAcceptMessage(string: String)
    fun onAcceptMessageVal(messVal: Int)
}
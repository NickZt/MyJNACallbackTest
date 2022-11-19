package ua.zt.mezon.myjnacallbacktest;

import java.lang.ref.WeakReference;

class MainActivityPresenterImpl implements MainActivityPresenter,JNIListener {
    private final WeakReference<MainActivity> mMainActivity;

    public MainActivityPresenterImpl(MainActivity mainActivity) {
        mMainActivity = new WeakReference<>(mainActivity);
    }


    @Override
    public void onAcceptMessage(String string) {
        mMainActivity.get().printTextPresenter(string);
    }

    @Override
    public void onAcceptMessageVal(int messVal) {
// not needed for this demo and using in a same way as in a message listener
    }
}
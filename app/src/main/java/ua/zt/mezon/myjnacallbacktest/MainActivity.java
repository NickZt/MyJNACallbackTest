package ua.zt.mezon.myjnacallbacktest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private MainActivityPresenter mPresenter;
    private TextView tvPresenter;
    private TextView tvAct;
    private EditText mEditText;
    private JNIListener nlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainActivityPresenterImpl(this);
        nsubscribeListener((MainActivityPresenterImpl) mPresenter);

        mEditText = findViewById(R.id.edit_text);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nonNextListener(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tvPresenter = (TextView) findViewById(R.id.sample_text_from_Presenter);

        tvAct = (TextView) findViewById(R.id.sample_text_from_act);

        nlistener = new JNIListener() {
            @Override
            public void onAcceptMessage(String string) {
                printTextfrActObj(string);
            }

            @Override
            public void onAcceptMessageVal(int messVal) {

            }
        };
        nsubscribeListener(nlistener);
    }

    public void printTextPresenter(String txt) {
        tvPresenter.setText(txt);
    }

    public void printTextfrActObj(String txt) {
        tvAct.setText(txt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ndismissListener();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    private native void nsubscribeListener(JNIListener JNIListener);

    private native void nonNextListener(String message);

    private native void ndismissListener();

}


package ua.zt.mezon.myjnacallbacktest;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    private MainActivityPresenter mPresenter; //    Task with * move it to a local variable and write in the comment in the article why and what is happening
    private TextView tvPresenter;
    private TextView tvAct;
    private JNIListener nlistener;//    Task with * move it to a local variable and write in the comment in the article why and what is happening
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainActivityPresenterImpl(this);
        nsubscribeListener((JNIListener) mPresenter);

        EditText editText = findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//not used
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nonNextListener(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
//not used
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
// not needed for this demo and using in a same way as in a message listener
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

    private native void nsubscribeListener(JNIListener jnilistener);

    private native void nonNextListener(String message);

    private native void ndismissListener();

}


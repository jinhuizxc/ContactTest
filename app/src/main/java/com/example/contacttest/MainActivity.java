package com.example.contacttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Android添加@联系人功能
 * https://blog.csdn.net/w804518214/article/details/56014715
 *
 * 仿微信@功能
 * https://www.jianshu.com/p/96f9136b76df
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView tips1, tips2;
    private EditText editText;
    private String testStr = "@测试@user:13666660002123功能";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tips1 = (TextView) findViewById(R.id.tips1);
        tips2 = (TextView) findViewById(R.id.tips2);
        editText = (EditText) findViewById(R.id.editText);

        tips2.setMovementMethod(LinkMovementMethod.getInstance());
        editText.setMovementMethod(LinkMovementMethod.getInstance());

        // 设置测试数据
        AtUtils.setText(editText, testStr);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged", "start=" + start + " before=" + before + " count=" + count);
                if (start == s.length() - 1 && s.toString().endsWith("@")) {
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivityForResult(intent, 123);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        String et_text = editText.getText().toString();
        if (TextUtils.isEmpty(et_text)){
            Toast.makeText(this, "editText 不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
      /*  tips1.setText(et_text);
        AtUtils.setText(tips2, et_text);
        Log.e(TAG, "onClick: "
                + "tips1: " + tips1.getText().toString()
                + "tips2: " + tips2.getText().toString());
        // E/MainActivity: onClick: tips1: @13666660002,tips2: @13666660002,
        Toast.makeText(this, tips1.getText().toString(), Toast.LENGTH_SHORT).show();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            User user = (User) data.getSerializableExtra("user");
            if (user != null) {
                appendAt(user);
            }
        }
    }

    private void appendAt(User user) {
        editText.getText().delete(editText.getText().length() - 1, editText.getText().length());
        editText.append(AtUtils.getSpan(editText, /*"@" + */user.name));
//        editText.append(",");
        Log.e(TAG, "appendAt: " + editText.getText().toString());
        // E/MainActivity: appendAt: @唐僧,
    }
}

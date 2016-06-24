package org.android.immortal.wedio.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.android.immortal.wedio.R;
import org.android.immortal.wedio.model.MySelfInfo;
import org.android.immortal.wedio.presenters.LoginHelper;
import org.android.immortal.wedio.presenters.viewinface.LoginView;
import org.android.immortal.wedio.utils.SxbLog;
import org.android.immortal.wedio.views.customviews.BaseActivity;

/**
 * 登录类
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {
    private static final String TAG = LoginActivity.class.getSimpleName();
    TextView mBtnLogin, mBtnRegister;
    EditText mPassWord, mUserName;
    private LoginHelper mLoginHeloper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SxbLog.i(TAG, "LoginActivity onCreate");
        mLoginHeloper = new LoginHelper(this, this);
        //获取个人数据本地缓存
        MySelfInfo.getInstance().getCache(getApplicationContext());
        if (needLogin() == true) {//本地没有账户需要登录
            setContentView(R.layout.activity_independent_login);
            mBtnLogin = (TextView) findViewById(R.id.btn_login);
            mUserName = (EditText) findViewById(R.id.username);
            mPassWord = (EditText) findViewById(R.id.password);
            mBtnRegister = (TextView) findViewById(R.id.registerNewUser);
            mBtnRegister.setOnClickListener(this);
            mBtnLogin.setOnClickListener(this);
        } else {
            //有账户登录直接IM登录
            SxbLog.i(TAG, "LoginActivity onCreate");
            mLoginHeloper.imLogin(MySelfInfo.getInstance().getId(), MySelfInfo.getInstance().getUserSig());
        }
    }

    @Override
    protected void onDestroy() {
        mLoginHeloper.onDestory();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.registerNewUser) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            finish();
        }
        if (view.getId() == R.id.btn_login) {//登录账号系统TLS
            if (mUserName.getText().equals("")) {
                Toast.makeText(LoginActivity.this, "name can not be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mPassWord.getText().equals("")) {
                Toast.makeText(LoginActivity.this, "password can not be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
//            tlsLogin(mUserName.getText().toString(),mPassWord.getText().toString());
            mLoginHeloper.tlsLogin(mUserName.getText().toString(), mPassWord.getText().toString());
        }
    }


    /**
     * 判断是否需要登录
     *
     * @return true 代表需要重新登录
     */
    public boolean needLogin() {
        if (MySelfInfo.getInstance().getId() != null) {
            return false;//有账号不需要登录
        } else {
            return true;//需要登录
        }

    }


    /**
     * 直接跳转主界面
     */
    private void jumpIntoHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void loginSucc() {
        Toast.makeText(LoginActivity.this, "" + MySelfInfo.getInstance().getId() + " login ", Toast.LENGTH_SHORT).show();
        jumpIntoHomeActivity();
    }

    @Override
    public void loginFail() {

    }
}

package com.jeegoo.o2o.tehuiduo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.jeegoo.o2o.tehuiduo.model.Shop;
import com.jeegoo.o2o.tehuiduo.model.User;
import com.jeegoo.o2o.tehuiduo.model.UserLab;
import com.jeegoo.o2o.tehuiduo.util.FileUtils;
import com.jeegoo.o2o.tehuiduo.util.HttpUtils;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_SHOP_ID = "shop_id";
    private TextView login_textview1, login_textview2, login_textview3;
    private EditText login_et_username, login_et_password;
    private CircularProgressButton circularButton1;
    private String clientId = "fOzIRjAfzFuVOOfwI21Bgb0L";
    private String mUserName;
    private String mShopId;
    private boolean isForceLogin = false;
    private boolean isConfirmLogin = true;
    private Intent intent;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x0:
                    circularButton1.setProgress(0);
                    break;
                case 0x1:
                    circularButton1.setProgress(50);
                    break;
                case 0x2:
                    circularButton1.setProgress(100);
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT)
                            .show();

                    intent.putExtra(EXTRA_USERNAME, mUserName);
                    intent.putExtra(EXTRA_SHOP_ID,mShopId);
                    startActivity(intent);
                    finish();
                    break;
                case 0x3:
                    circularButton1.setProgress(-1);
                    Toast.makeText(LoginActivity.this, "用户或密码错误,请重新输入",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 0x4:
                    break;
                default:
                    break;
            }
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initview();
    }

    public void initview() {
        intent = new Intent(LoginActivity.this, ProductListActivity.class);
        circularButton1 = (CircularProgressButton) findViewById(R.id.login_btn_login);
        circularButton1.setIndeterminateProgressMode(true);
        login_et_username = (EditText) findViewById(R.id.login_et_username);
        login_et_password = (EditText) findViewById(R.id.login_et_password);
        login_textview1 = (TextView) findViewById(R.id.login_textview1);
        login_textview2 = (TextView) findViewById(R.id.login_textview2);
        //   login_textview3 = (TextView) findViewById(R.id.login_textview3);
        login_textview1.setOnClickListener(this);
        login_textview2.setOnClickListener(this);
        //  login_textview3.setOnClickListener(this);
        circularButton1.setOnClickListener(this);

    }

    private boolean validate() {
        String username = login_et_username.getText().toString();
        if (username.equals("")) {
            Toast.makeText(this, "用户名称是必填项!", Toast.LENGTH_SHORT).show();
            return false;
        }
        String password = login_et_password.getText().toString();
        if (password.equals("")) {
            Toast.makeText(this, "用户密码是必填项!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String query(String username, String password) {
        String queryresult = null;
        String queryString = "username=" + username + "&password=" + password;
        String url = HttpUtils.BASE_URL + "servlet/LoginServlet?" + queryString;
        queryresult = HttpUtils.queryStringForPost(url);
        return queryresult;
    }

    private boolean login() {
        String username = login_et_username.getText().toString();
        String password = login_et_password.getText().toString();
        String result = query(username, password);
        if (username.equals(password)) {
//            mUserName = username;
//            mShopId = UserLab.get(LoginActivity.this).getUser(mUserName).getShop();
            User user = new User("test","test");
            Shop shop = new Shop("test");
            UserLab.get(LoginActivity.this).addUser(user);
            UserLab.get(LoginActivity.this).getUser("test").addShop(shop);
            mUserName = "test";
            mShopId = "test";
            FileUtils.createFile(mUserName);
            UserLab.get(LoginActivity.this).setCurrentUser(user);
            UserLab.get(LoginActivity.this).setCurrentShop(shop);
            return true;
        }
        if (result != null && result.equals("1")) {
            mUserName = username;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_btn_login:
                if (circularButton1.getProgress() != 0) {
                    Message msg = new Message();
                    msg.what = 0x0;
                    handler.sendMessage(msg);
                }
                if (validate()) {

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Message msg = new Message();
                            msg.what = 0x1;
                            handler.sendMessage(msg);
                            if (login()) {
                                Message msg2 = new Message();
                                msg2.what = 0x2;
                                handler.sendMessage(msg2);

                            } else {
                                Message msg3 = new Message();
                                msg3.what = 0x3;
                                handler.sendMessage(msg3);
                            }
                        }
                    }).start();
                }
                break;
            case R.id.login_textview1:

                break;
            case R.id.login_textview2:

                break;
            // case R.id.login_textview3:
//                baidu = new Baidu(clientId, LoginActivity.this);
//                baidu.authorize(LoginActivity.this, isForceLogin, isConfirmLogin,
//                        new BaiduDialogListener() {
//
//                            @Override
//                            public void onError(BaiduDialogError arg0) {
//                                // TODO Auto-generated method stub
//                                Toast.makeText(LoginActivity.this, "登录失败",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onComplete(Bundle arg0) {
//                                // TODO Auto-generated method stub
//                                Toast.makeText(LoginActivity.this, "登录成功",
//                                        Toast.LENGTH_SHORT).show();
//
//                                new Thread(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//                                        String url = "https://openapi.baidu.com/rest/2.0/passport/users/getLoggedInUser"
//                                                + "?"
//                                                + "access_token="
//                                                + baidu.getAccessToken();
//                                        String urlinfo = "https://openapi.baidu.com/rest/2.0/passport/users/getInfo"
//                                                + "?"
//                                                + "access_token="
//                                                + baidu.getAccessToken();
//                                        try {
//                                            String usermessage = baidu.request(url,
//                                                    null, "GET");
//                                            String userinfo = baidu.request(
//                                                    urlinfo, null, "GET");
//                                            Intent intent = new Intent(
//                                                    LoginActivity.this,
//                                                    ProductListActivity.class);
//                                            intent.putExtra("flag", "baidu");
//                                            intent.putExtra("usermessage",
//                                                    usermessage);
//                                            intent.putExtra("userinfo", userinfo);
//                                            startActivity(intent);
//                                            finish();
//                                        } catch (IOException e) {
//                                            // TODO Auto-generated catch block
//                                            e.printStackTrace();
//                                        } catch (BaiduException e) {
//                                            // TODO Auto-generated catch block
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }).start();
//
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                // TODO Auto-generated method stub
//
//                            }
//
//                            @Override
//                            public void onBaiduException(BaiduException arg0) {
//                                // TODO Auto-generated method stub
//
//                            }
//                        });
            //break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

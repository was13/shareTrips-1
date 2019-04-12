package com.example.yongs.sharetrips.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yongs.sharetrips.R;
import com.example.yongs.sharetrips.api.ApiCallback;
import com.example.yongs.sharetrips.api.users.RetrofitUsers;
import com.example.yongs.sharetrips.model.User;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.id_edit)
    EditText id;
    @BindView(R.id.password_edit)
    EditText password;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.join_button)
    Button joinButton;

    static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;

    String mId;
    String mPassword;

    RetrofitUsers mRetrofitUsers;

    private static final String TAG = LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        getPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

        mRetrofitUsers = RetrofitUsers.getInstance(this).createBaseApi();

        setEdit();

        setButton();

    }

    private void getPermission(String permission){
        int requestCode;

        switch(permission){
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                requestCode = READ_EXTERNAL_STORAGE_PERMISSION;
                break;
            default:
                requestCode = -1;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), permission);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{permission},requestCode);
        }
    }

    private void setEdit(){
        id.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    private void setButton(){
        loginButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                getValue();
                if(mId==null) {
                    Toast.makeText(LoginActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(mPassword==null){
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    HashMap<String, Object> parameters = new HashMap<>() ;

                    parameters.put("id",mId);
                    parameters.put("password",mPassword);

                    mRetrofitUsers.postLogin(parameters, new ApiCallback() {
                        @Override
                        public void onError(Throwable t) {
                            Log.e(TAG,t.toString());
                        }

                        @Override
                        public void onSuccess(int code, Object receiveData) {
                            Log.i(TAG,String.valueOf(code));
                            User user = (User)receiveData;
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("username",user.getUsername());
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int code) {
                            Log.i(TAG,String.valueOf(code));
                            Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        joinButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getValue(){
        if(id.getText().toString().length()!=0){
            mId=id.getText().toString();
        }
        if(password.getText().toString().length()!=0){
            mPassword=password.getText().toString();
        }
    }
}
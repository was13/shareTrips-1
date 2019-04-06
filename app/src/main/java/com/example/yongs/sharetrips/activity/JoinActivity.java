package com.example.yongs.sharetrips.activity;

import android.content.Intent;
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

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class JoinActivity extends AppCompatActivity {

    @BindView(R.id.id_edit)
    EditText id;
    @BindView(R.id.password_edit)
    EditText password;
    @BindView(R.id.password_check_edit)
    EditText passwordCheck;
    @BindView(R.id.username_edit)
    EditText username;
    @BindView(R.id.email_edit)
    EditText email;
    @BindView(R.id.join_button)
    Button joinButton;

    RetrofitUsers mRetrofitUsers;

    User mUser = new User();

    String mId;
    String mPassword;
    String mPasswordCheck;
    String mUserName;
    String mEmail;

    private static final String TAG = JoinActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);

        mRetrofitUsers = RetrofitUsers.getInstance(this).createBaseApi();

        setEdit();

        setButton();
    }

    private void setEdit(){
        id.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        passwordCheck.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        username.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    private void setButton(){
        joinButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){

                getValue();

                if(!mUser.checkInput()||mPasswordCheck==null){
                    Toast.makeText(JoinActivity.this,"입력하지 않은 내용이 있습니다.",Toast.LENGTH_SHORT).show();
                }else if(!mPassword.equals(mPasswordCheck)){
                    Toast.makeText(JoinActivity.this,"비밀번호가 서로 다릅니다.",Toast.LENGTH_SHORT).show();
                }else{
                    HashMap<String, Object> parameters = new HashMap<>() ;

                    parameters.put("id",mUser.getId());
                    parameters.put("password",mUser.getPassword());
                    parameters.put("username",mUser.getUsername());
                    parameters.put("email",mUser.getEmail());

                    mRetrofitUsers.postJoin(parameters,new ApiCallback(){

                        @Override
                        public void onError(Throwable t) {
                            Log.e(TAG,t.toString());
                        }

                        @Override
                        public void onSuccess(int code, Object receiveData) {
                            Log.i(TAG,String.valueOf(code));
                            Intent intent = new Intent(JoinActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int code) {
                            Log.i(TAG,String.valueOf(code));
                        }
                    });
                }
            }
        });
    }

    private void getValue(){
        if(id.getText().toString().length()!=0){
            mId = id.getText().toString();
            mUser.setId(mId);
        }
        if(password.getText().toString().length()!=0){
            mPassword = password.getText().toString();
            mUser.setPassword(mPassword);
        }
        if(passwordCheck.getText().toString().length()!=0){
            mPasswordCheck = passwordCheck.getText().toString();
        }
        if(username.getText().toString().length()!=0){
            mUserName = username.getText().toString();
            mUser.setUsername(mUserName);
        }
        if(email.getText().toString().length()!=0){
            mEmail = email.getText().toString();
            mUser.setEmail(mEmail);
        }
    }
}

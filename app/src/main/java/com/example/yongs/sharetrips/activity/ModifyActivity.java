package com.example.yongs.sharetrips.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yongs.sharetrips.R;
import com.example.yongs.sharetrips.api.ApiCallback;
import com.example.yongs.sharetrips.api.reports.RetrofitReports;
import com.example.yongs.sharetrips.api.users.RetrofitUsers;
import com.example.yongs.sharetrips.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.modify_title)
    TextView modifyTitle;

    @BindView(R.id.modify_edit)
    EditText modifyContent;

    private static final String TAG = ModifyActivity.class.getSimpleName();

    RetrofitUsers mRetrofitUsers;

    Intent mIntent;

    String mTitle;
    String mContent;
    String mId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        ButterKnife.bind(this);

        mRetrofitUsers = RetrofitUsers.getInstance(this).createBaseApi();

        mId = getIntent().getStringExtra("id");

        setToolbar();

        modifyTitle.setText(mTitle);

        setEdit();

    }

    private void setToolbar(){
        mIntent = getIntent();
        mTitle = mIntent.getStringExtra("content");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mTitle+"변경");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setEdit()
    {
        modifyContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_save:
                if(modifyContent.getText().toString().length()!=0){
                    mContent = modifyContent.getText().toString();
                }

                if(mContent==null){
                    Toast.makeText(this, "변경할 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User();
                    if(mTitle.equals("이름")){
                        user.setUsername(mContent);
                        mRetrofitUsers.patchUsername(mId, user, new ApiCallback() {
                            @Override
                            public void onError(Throwable t) {
                                Log.e(TAG,t.toString());
                            }

                            @Override
                            public void onSuccess(int code, Object receiveData) {
                                Log.i(TAG,String.valueOf(code));
                                finish();
                            }

                            @Override
                            public void onFailure(int code) {
                                Log.i(TAG,String.valueOf(code));
                            }
                        });
                    }else if(mTitle.equals("이메일")){
                        user.setEmail(mContent);
                        mRetrofitUsers.patchEmail(mId, user, new ApiCallback() {
                            @Override
                            public void onError(Throwable t) {
                                Log.e(TAG,t.toString());
                            }

                            @Override
                            public void onSuccess(int code, Object receiveData) {
                                Log.i(TAG,String.valueOf(code));
                                finish();
                            }

                            @Override
                            public void onFailure(int code) {
                                Log.i(TAG,String.valueOf(code));
                            }
                        });
                    }

                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

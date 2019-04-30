package com.example.yongs.sharetrips.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yongs.sharetrips.R;
import com.example.yongs.sharetrips.activity.ModifyActivity;
import com.example.yongs.sharetrips.api.ApiCallback;
import com.example.yongs.sharetrips.api.users.RetrofitUsers;
import com.example.yongs.sharetrips.model.User;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.email)
    TextView email;

    RetrofitUsers mRetrofitUsers;

    User mUser;

    private String mId;

    private static final String TAG = ProfileFragment.class.getSimpleName();

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);

        mRetrofitUsers = RetrofitUsers.getInstance(getActivity().getBaseContext()).createBaseApi();

        setProfile();

        setClickEvent();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setProfile();
    }

    private void setProfile(){
        mId = getActivity().getIntent().getStringExtra("id");

        mRetrofitUsers.getUser(mId, new ApiCallback() {
            @Override
            public void onError(Throwable t) {
                Log.e(TAG,t.toString());
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.i(TAG,String.valueOf(code));
                mUser = (User)receiveData;
                username.setText(mUser.getUsername());
                email.setText(mUser.getEmail());
            }

            @Override
            public void onFailure(int code) {
                Log.i(TAG,String.valueOf(code));
            }
        });
    }

    private void setClickEvent(){
        username.setOnClickListener(new TextView.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ModifyActivity.class);
                intent.putExtra("content","이름");
                intent.putExtra("id",mId);
                startActivity(intent);
            }
        });

        email.setOnClickListener(new TextView.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ModifyActivity.class);
                intent.putExtra("content","이메일");
                intent.putExtra("id",mId);
                startActivity(intent);
            }
        });
    }
}

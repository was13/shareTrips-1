package com.example.yongs.sharetrips.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yongs.sharetrips.R;
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

    private String mUsername;
    private String mEmail;

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

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,getActivity().toString());
        setProfile();
    }

    private void setProfile(){
        mUsername = getActivity().getIntent().getStringExtra("username");

        mRetrofitUsers.getUser(mUsername, new ApiCallback() {
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

}

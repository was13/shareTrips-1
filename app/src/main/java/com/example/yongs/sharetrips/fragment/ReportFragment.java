package com.example.yongs.sharetrips.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yongs.sharetrips.R;
import com.example.yongs.sharetrips.adapter.ReportAdapter;
import com.example.yongs.sharetrips.api.ApiCallback;
import com.example.yongs.sharetrips.api.reports.RetrofitReports;
import com.example.yongs.sharetrips.model.Report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {

    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.empty_list)
    TextView emptyListItem;

    ReportAdapter mReportAdapter;
    ArrayList<Report> mReportArrayList;
    RetrofitReports mRetrofitReports;

    Report mReport;

    private static final String TAG = ReportFragment.class.getSimpleName();

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this,view);

        mRetrofitReports = RetrofitReports.getInstance(getActivity().getBaseContext()).createBaseApi();

        setList();

        return view;
    }

    private void setList(){
        mReportArrayList = new ArrayList<Report>();
        mReportAdapter = new ReportAdapter(this.getActivity(), mReportArrayList);
        list.setAdapter(mReportAdapter);
        mReportAdapter.notifyDataSetChanged();

        list.setEmptyView(emptyListItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        listRenewal();
    }

    private void listRenewal(){
        Log.d(TAG,getActivity().getIntent().getStringExtra("username"));
        mRetrofitReports.getReport(getActivity().getIntent().getStringExtra("username"), new ApiCallback() {
            @Override
            public void onError(Throwable t) {
                Log.e(TAG,t.toString());
            }

            @Override
            public void onSuccess(int code, Object receiveData) {
                Log.i(TAG,String.valueOf(code));
                final Report report = (Report)receiveData;

                mRetrofitReports.getImage(getActivity().getIntent().getStringExtra("username"),new ApiCallback() {

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG,t.toString());
                    }

                    @Override
                    public void onSuccess(int code, Object receiveData) {
                        Log.i(TAG,String.valueOf(code));
                        ResponseBody body = (ResponseBody)receiveData;
                        Bitmap bitmap=null;
                        try{

                            InputStream inputStream = null;

                            inputStream = body.byteStream();

                            bitmap = BitmapFactory.decodeStream(inputStream);
                            if(inputStream != null){
                                inputStream.close();
                            }

                        }catch(IOException e){
                            e.printStackTrace();
                        }

                        mReport = new Report(report.getUsername(),report.getTitle(),report.getLocation(),report.getContent(),report.getDate(),report.getView(),bitmap);


                        mReportAdapter.reportArrayList.clear();
                        mReportAdapter.reportArrayList.add(mReport);
                        list.setAdapter(mReportAdapter);
                        mReportAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(int code) {
                        Log.i(TAG,String.valueOf(code));
                    }
                });
            }

            @Override
            public void onFailure(int code) {
                Log.i(TAG,String.valueOf(code));
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}

package com.example.yongs.sharetrips.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yongs.sharetrips.R;
import com.example.yongs.sharetrips.api.ApiCallback;
import com.example.yongs.sharetrips.api.reports.RetrofitReports;
import com.example.yongs.sharetrips.model.Report;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.title_edit)
    EditText title;
    @BindView(R.id.image_button)
    Button button;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.location_edit)
    EditText location;
    @BindView(R.id.content_edit)
    EditText content;

    private static final String TAG = ReportActivity.class.getSimpleName();
    private static final int SELECT_PICTURE = 1;

    RetrofitReports mRetrofitReports;

    String mTitle;
    String mLocation;
    String mContent;

    Uri mUri;
    String mImagePath;

    Report mReport = new Report();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        mRetrofitReports = RetrofitReports.getInstance(this).createBaseApi();

        setToolbar();

        setEdit();

        setButton();
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("여행 기록하기");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setEdit(){
        title.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        location.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        content.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    private void setButton(){
        button.setOnClickListener(this);
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
                getValue();

                if(!mReport.checkInput()) {
                    if (mTitle == null) {
                        Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (mImagePath == null) {
                        Toast.makeText(this, "사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (mLocation == null) {
                        Toast.makeText(this, "위치를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    HashMap<String,Object> parameters = new HashMap<>();

                    parameters.put("title",mTitle);
                    parameters.put("location",mLocation);
                    parameters.put("content",mContent);
                    parameters.put("writer",getIntent().getStringExtra("user"));

                    mRetrofitReports.postReport(mImagePath, parameters, new ApiCallback() {
                        @Override
                        public void onError(Throwable t) {
                            Log.e(TAG,t.toString());
                        }

                        @Override
                        public void onSuccess(int code, Object receiveData) {
                            Log.i(TAG,String.valueOf(code));
                            Log.i(TAG,String.valueOf(receiveData));
                            finish();
                        }

                        @Override
                        public void onFailure(int code) {
                            Log.i(TAG,String.valueOf(code));
                        }
                    });
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_PICTURE){
                Uri mUri = data.getData();
                Log.d(TAG,"Image Uri:"+mUri);
                if(null != mUri){
                    mImagePath = getPathFromURI(mUri);
                    Log.d(TAG,"Image Path:"+mImagePath);
                    image.setImageURI(mUri);
                    button.setVisibility(View.GONE);
                }
            }
        }
    }

    private String getPathFromURI(Uri contentUri){
        String id = DocumentsContract.getDocumentId(contentUri).split(":")[1];
        String[] columns = { MediaStore.Files.FileColumns.DATA };
        String selection = MediaStore.Files.FileColumns._ID + " = " + id;
        Cursor cursor = getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, selection, null, null);
        try {
            int columnIndex = cursor.getColumnIndex(columns[0]);
            if (cursor.moveToFirst()) { return cursor.getString(columnIndex); }
        } finally {
            cursor.close();
        }

        return null;
    }

    private void getValue(){
        if(title.getText().toString().length() != 0){
            mTitle = title.getText().toString();
            mReport.setTitle(mTitle);
        }
        if(mImagePath != null){
            mReport.setImagePath(mImagePath);
        }
        if(location.getText().toString().length() != 0){
            mLocation = content.getText().toString();
            mReport.setLocation(mLocation);
        }
        if(content.getText().toString().length() != 0){
            mContent = content.getText().toString();
            mReport.setContent(mContent);
        }
    }
}

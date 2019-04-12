package com.example.yongs.sharetrips.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.nio.ByteBuffer;

public class Report implements Parcelable {

    String username;
    String title;
    String location;
    String content;
    String date;
    int view;
    Bitmap bitmap;

    public Report(){
        this.username = null;
        this.title = null;
        this.location = null;
        this.content = null;
        this.date = null;
        this.view = 0;
        this.bitmap = null;
    }

    public Report(String username, String title, String location, String content, String date, int view, Bitmap bitmap){
            this.username = username;
            this.title = title;
            this.location = location;
            this.content = content;
            this.date = date;
            this.view = view;
            this.bitmap = bitmap;
    }

    protected Report(Parcel in) {
        username = in.readString();
        title = in.readString();
        location = in.readString();
        content = in.readString();
        date = in.readString();
        view = in.readInt();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public String getUsername() {return username;}

    public void setUsername(String username){this.username = username;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean checkInput(){
        if(getTitle()==null
                ||getLocation()==null
                )
            return false;

        return true;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(title);
        dest.writeString(location);
        dest.writeString(content);
        dest.writeString(date);
        dest.writeInt(view);
        this.bitmap.writeToParcel(dest,flags);
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };
}

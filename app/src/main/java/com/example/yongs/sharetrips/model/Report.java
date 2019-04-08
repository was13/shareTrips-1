package com.example.yongs.sharetrips.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Report implements Parcelable {

    String title;
    String imagePath;
    String location;
    String content;

    public Report(){
        this.title = null;
        this.imagePath = null;
        this.location = null;
        this.content = null;
    }

    public Report(String title, String imagePath, String location, String content){
            this.title = title;
            this.imagePath =imagePath;
            this.location = location;
            this.content = content;
    }

    protected Report(Parcel in) {
        title = in.readString();
        imagePath = in.readString();
        location = in.readString();
        content = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean checkInput(){
        if(getTitle()==null
                ||getImagePath()==null
                ||getLocation()==null
                )
            return false;

        return true;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imagePath);
        dest.writeString(location);
        dest.writeString(content);
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

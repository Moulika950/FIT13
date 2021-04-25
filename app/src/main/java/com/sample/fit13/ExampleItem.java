package com.sample.fit13;

import android.os.Parcel;
import android.os.Parcelable;

public class ExampleItem implements Parcelable {

    private String title, duration, date, description;
    private int pos;
    private int weightImg;
    private int closeImg;

    ExampleItem() {

    }

    protected ExampleItem(Parcel in) {
        title = in.readString();
        duration = in.readString();
        date = in.readString();
        description = in.readString();
        pos = in.readInt();
        weightImg = in.readInt();
        closeImg = in.readInt();
    }

    public static final Creator<ExampleItem> CREATOR = new Creator<ExampleItem>() {
        @Override
        public ExampleItem createFromParcel(Parcel in) {
            return new ExampleItem(in);
        }

        @Override
        public ExampleItem[] newArray(int size) {
            return new ExampleItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getWeightImg() {
        return weightImg;
    }

    public void setWeightImg(int weightImg) {
        this.weightImg = weightImg;
    }

    public int getCloseImg() {
        return closeImg;
    }

    public void setCloseImg(int closeImg) {
        this.closeImg = closeImg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(duration);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeInt(pos);
        dest.writeInt(weightImg);
        dest.writeInt(closeImg);

    }
}



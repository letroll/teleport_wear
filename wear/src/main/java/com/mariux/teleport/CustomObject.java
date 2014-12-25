package com.mariux.teleport;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by michaltajchert on 22/12/14.
 */
public class CustomObject implements Parcelable {
    private String name;
    private int number;

    public CustomObject(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(number);
    }

    public CustomObject(Parcel in){
        this.name = in.readString();
        this.number = in.readInt();
    }

    @Override
    public String toString() {
        return "CustomObject{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}

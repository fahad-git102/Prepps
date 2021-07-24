package com.fahaddev.prepps.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProgramsModel implements Parcelable {
    public int id;
    public String name;
    public int college_navigator_id;
    public String created_at;
    public String updated_at;

    protected ProgramsModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        college_navigator_id = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<ProgramsModel> CREATOR = new Creator<ProgramsModel>() {
        @Override
        public ProgramsModel createFromParcel(Parcel in) {
            return new ProgramsModel(in);
        }

        @Override
        public ProgramsModel[] newArray(int size) {
            return new ProgramsModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCollege_navigator_id() {
        return college_navigator_id;
    }

    public void setCollege_navigator_id(int college_navigator_id) {
        this.college_navigator_id = college_navigator_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(college_navigator_id);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
    }
}

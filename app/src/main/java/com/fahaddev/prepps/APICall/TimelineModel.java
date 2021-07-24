package com.fahaddev.prepps.APICall;

import android.os.Parcel;
import android.os.Parcelable;

import com.fahaddev.prepps.models.User_detail;

public class TimelineModel implements Parcelable {
        public int id;
        public String user_id;
        public String user_type;
        public String title;
        public String image;
        public String video_link;
        public String created_at;
        public String updated_at;
        public User_detail user_detail;

    protected TimelineModel(Parcel in) {
        id = in.readInt();
        user_id = in.readString();
        user_type = in.readString();
        title = in.readString();
        image = in.readString();
        video_link = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        user_detail = in.readParcelable(User_detail.class.getClassLoader());
    }

    public static final Creator<TimelineModel> CREATOR = new Creator<TimelineModel>() {
        @Override
        public TimelineModel createFromParcel(Parcel in) {
            return new TimelineModel(in);
        }

        @Override
        public TimelineModel[] newArray(int size) {
            return new TimelineModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
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

    public User_detail getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(User_detail user_detail) {
        this.user_detail = user_detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(user_id);
        parcel.writeString(user_type);
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(video_link);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
        parcel.writeParcelable(user_detail, i);
    }
}

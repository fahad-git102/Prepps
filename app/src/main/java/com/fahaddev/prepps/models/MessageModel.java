package com.fahaddev.prepps.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageModel implements Parcelable {
    public int id;
    public int user_id;
    public int read;
    public int session_id;
    public int receive_id;
    public String message;
    public String created_at;
    public String updated_at;
    public String f_date;
    public String s_date;

    protected MessageModel(Parcel in) {
        id = in.readInt();
        user_id = in.readInt();
        read = in.readInt();
        session_id = in.readInt();
        receive_id = in.readInt();
        message = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        f_date = in.readString();
        s_date = in.readString();
    }

    public static final Creator<MessageModel> CREATOR = new Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel in) {
            return new MessageModel(in);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getReceive_id() {
        return receive_id;
    }

    public void setReceive_id(int receive_id) {
        this.receive_id = receive_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getF_date() {
        return f_date;
    }

    public void setF_date(String f_date) {
        this.f_date = f_date;
    }

    public String getS_date() {
        return s_date;
    }

    public void setS_date(String s_date) {
        this.s_date = s_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(user_id);
        parcel.writeInt(read);
        parcel.writeInt(session_id);
        parcel.writeInt(receive_id);
        parcel.writeString(message);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
        parcel.writeString(f_date);
        parcel.writeString(s_date);
    }
}

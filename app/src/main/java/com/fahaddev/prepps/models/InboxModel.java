package com.fahaddev.prepps.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InboxModel implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("type")
    private String type;
    @SerializedName("about_us")
    private String aboutUs;
    @SerializedName("is_verified")
    private int isVerified;
    @SerializedName("email_verified_at")
    private String emailVerifiedAt;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("user_detail")
    private User_detail userDetail;
    @SerializedName("message")
    private MessageModel message;

    protected InboxModel(Parcel in) {
        id = in.readInt();
        email = in.readString();
        type = in.readString();
        aboutUs = in.readString();
        isVerified = in.readInt();
        emailVerifiedAt = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<InboxModel> CREATOR = new Creator<InboxModel>() {
        @Override
        public InboxModel createFromParcel(Parcel in) {
            return new InboxModel(in);
        }

        @Override
        public InboxModel[] newArray(int size) {
            return new InboxModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User_detail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(User_detail userDetail) {
        this.userDetail = userDetail;
    }

    public MessageModel getMessage() {
        return message;
    }

    public void setMessage(MessageModel message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(email);
        parcel.writeString(type);
        parcel.writeString(aboutUs);
        parcel.writeInt(isVerified);
        parcel.writeString(emailVerifiedAt);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
    }
}

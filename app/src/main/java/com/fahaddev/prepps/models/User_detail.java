package com.fahaddev.prepps.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User_detail implements Parcelable {
    private float id;
    private float user_id;
    private String name;
    private String city;
    private String image;
    private String state;
    private String high_school;
    private String height;
    private String award;
    private String weight;
    private String position;
    private String gpa;
    private String hobbies;
    private String activity;
    private String career_interested;
    private String college_address = null;
    private String created_at;
    private String updated_at;
    private String sat_score;
    private String sport_played;

    // Getter Methods


    public String getAward() {
        return award;
    }

    public String getSport_played() {
        return sport_played;
    }

    public void setSport_played(String sport_played) {
        this.sport_played = sport_played;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getImage() {
        return image;
    }

    public String getSat_score() {
        return sat_score;
    }

    public void setSat_score(String sat_score) {
        this.sat_score = sat_score;
    }

    public void setImage(String image) {
        this.image = image;
    }

    protected User_detail(Parcel in) {
        id = in.readFloat();
        user_id = in.readFloat();
        name = in.readString();
        city = in.readString();
        state = in.readString();
        high_school = in.readString();
        height = in.readString();
        weight = in.readString();
        position = in.readString();
        gpa = in.readString();
        hobbies = in.readString();
        activity = in.readString();
        career_interested = in.readString();
        college_address = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<User_detail> CREATOR = new Creator<User_detail>() {
        @Override
        public User_detail createFromParcel(Parcel in) {
            return new User_detail(in);
        }

        @Override
        public User_detail[] newArray(int size) {
            return new User_detail[size];
        }
    };

    public float getId() {
        return id;
    }

    public float getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getHigh_school() {
        return high_school;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getPosition() {
        return position;
    }

    public String getGpa() {
        return gpa;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getActivity() {
        return activity;
    }

    public String getCareer_interested() {
        return career_interested;
    }

    public String getCollege_address() {
        return college_address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setUser_id(float user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setHigh_school(String high_school) {
        this.high_school = high_school;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setCareer_interested(String career_interested) {
        this.career_interested = career_interested;
    }

    public void setCollege_address(String college_address) {
        this.college_address = college_address;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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
        parcel.writeFloat(id);
        parcel.writeFloat(user_id);
        parcel.writeString(name);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(high_school);
        parcel.writeString(height);
        parcel.writeString(weight);
        parcel.writeString(position);
        parcel.writeString(gpa);
        parcel.writeString(hobbies);
        parcel.writeString(activity);
        parcel.writeString(career_interested);
        parcel.writeString(college_address);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
    }
}

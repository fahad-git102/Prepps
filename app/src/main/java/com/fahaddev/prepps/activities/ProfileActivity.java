package com.fahaddev.prepps.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fahaddev.prepps.APICall.ResponseUserDetail;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.adapters.PostAdapter;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.User;
import com.fahaddev.prepps.models.User_detail;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.fahaddev.prepps.helpers.StaticClass.currentUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    Call<ResponseUserDetail> responseUserDetailCall;
//    CircleImageView profilePic;
    ImageButton goBack;
    ImageView coverPic;
    String picturepath;
    Bitmap bitmap;
    boolean imageSelected = false;
    public final static int REQUEST_CAMERA_WRITE_EXTERNAL_STORAGE = 1919;
    public static final int PICK_IMAGE = 1001;
    public static final int TAKE_PHOTO = 1000;
    ImageButton btnUpload;
    TextView tvBio, tvTown, tvSchool, tvGrade, tvAwards, tvExtraActivities, tvInterested, tvUserName, tvHeight, tvweight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        profilePic = findViewById(R.id.profilePic);
        tvBio = findViewById(R.id.tvBio);
        tvTown = findViewById(R.id.tvTown);
        goBack = findViewById(R.id.goBack);
        tvHeight = findViewById(R.id.tvHeight);
        tvweight = findViewById(R.id.tvWeight);
        btnUpload = findViewById(R.id.changeCover);
        btnUpload.setOnClickListener(this);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvSchool = findViewById(R.id.tvSchool);
        coverPic = findViewById(R.id.cover_pic);
        tvGrade = findViewById(R.id.tvGrade);
        tvAwards = findViewById(R.id.tvAwards);
        tvExtraActivities = findViewById(R.id.tvExtraActivities);
        tvInterested = findViewById(R.id.tvInterested);
        tvUserName = findViewById(R.id.tvUserName);
        String id = getIntent().getExtras().getString("userId");
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        getProfileData(Float.parseFloat(id));
    }

    private void getProfileData(float id){
        if (id>0){
            responseUserDetailCall = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).getUserDetail(
                    "Bearer "+currentUser.getToken(), id);
            responseUserDetailCall.enqueue(new Callback<ResponseUserDetail>() {
                @Override
                public void onResponse(Call<ResponseUserDetail> call, Response<ResponseUserDetail> response) {
                    if (response.isSuccessful()){
                        User user = response.body().getData();
                        User_detail user_detail = user.getUser_detail();
                        if (user_detail!=null){

                            if (user.getAbout_us()!=null){
                                tvBio.setVisibility(View.VISIBLE);
                                tvBio.setText(user.getAbout_us());
                            }else {
                                tvBio.setVisibility(View.GONE);
                            }

                            if (user_detail.getId()!=currentUser.getId()){
                                btnUpload.setVisibility(View.GONE);
                            }else {
                                btnUpload.setVisibility(View.VISIBLE);
                            }
                            if (user_detail.getName()!=null){
                                tvUserName.setText(user_detail.getName());
                            }
                            if (user_detail.getCity()!=null){
                                tvTown.setText(user_detail.getCity() + ", "+ user_detail.getState());
                            }
                            if (user_detail.getGpa()!=null){
                                tvGrade.setText(user_detail.getGpa());
                            }
                            if (user_detail.getHigh_school()!=null){
                                tvSchool.setText(user_detail.getHigh_school());
                            }
                            if (user_detail.getActivity()!=null){
                                tvExtraActivities.setText(user_detail.getActivity());
                            }
                            if (user_detail.getCareer_interested()!=null){
                                tvInterested.setText(user_detail.getCareer_interested());
                            }
                            if (user_detail.getAward()!=null){
                                tvAwards.setText(user_detail.getAward());
                            }
                            if (user_detail.getImage()!=null){
                                Picasso.with(ProfileActivity.this).load(user_detail.getImage()).placeholder(R.drawable.default_user_image).fit().into(coverPic);
                            }
                            if (user_detail.getHeight()!=null){
                                tvHeight.setText(user_detail.getHeight());
                            }
                            if (user_detail.getWeight()!=null){
                                tvweight.setText(user_detail.getWeight());
                            }
                        }

                    }else {
                        Toast.makeText(ProfileActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseUserDetail> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onClick(View view1) {
        if (view1.getId()==R.id.changeCover){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.image_action_dialog, null);
            Button camera = view.findViewById(R.id.camera);
            Button gallery = view.findViewById(R.id.gallery);
            builder.setView(view);
            final AlertDialog alertDialog = builder.create();
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ((ContextCompat.checkSelfPermission(ProfileActivity.this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            && ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{
                                        WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA},
                                1919);
                    } else{
                        dispatchTakePictureIntent();
                    }
                    alertDialog.dismiss();
                }
            });
            gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ((ContextCompat.checkSelfPermission(ProfileActivity.this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            && ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{
                                        WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA},
                                REQUEST_CAMERA_WRITE_EXTERNAL_STORAGE);
                    } else{
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , PICK_IMAGE);
                    }
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(ProfileActivity.this,
                        "com.fahaddev.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturepath = cursor.getString(columnIndex);
                    cursor.close();
                    bitmap = BitmapFactory.decodeFile(picturepath);
                    coverPic.setImageBitmap(bitmap);
                    imageSelected=true;
                }
                break;

            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    galleryAddPic();
                    setPic();
                }

                break;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        picturepath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(picturepath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        int targetW = coverPic.getWidth();
        int targetH = coverPic.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(picturepath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        bitmap = BitmapFactory.decodeFile(picturepath, bmOptions);
        coverPic.setImageBitmap(bitmap);
        imageSelected=true;
    }

}
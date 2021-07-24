package com.fahaddev.prepps.fragments.students;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fahaddev.prepps.APICall.ResponseAboutMe;
import com.fahaddev.prepps.APICall.ResponseUpdateProfile;
import com.fahaddev.prepps.APICall.ResponseUploadCover;
import com.fahaddev.prepps.APICall.ResponseUserDetail;
import com.fahaddev.prepps.APICall.RetrofitApiClient;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.activities.ProfileActivity;
import com.fahaddev.prepps.adapters.PostAdapter;
import com.fahaddev.prepps.helpers.StaticClass;
import com.fahaddev.prepps.models.User;
import com.fahaddev.prepps.models.User_detail;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static com.fahaddev.prepps.activities.ProfileActivity.PICK_IMAGE;
import static com.fahaddev.prepps.activities.ProfileActivity.REQUEST_CAMERA_WRITE_EXTERNAL_STORAGE;
import static com.fahaddev.prepps.activities.ProfileActivity.TAKE_PHOTO;
import static com.fahaddev.prepps.helpers.StaticClass.currentUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerTimeline ;
    ImageButton btnChangeCover, btnUpdateBio;
    RelativeLayout progress;
    TextView tvUpdateBio, tvAboutMe, tvUserName, tvTown, tvSchool, tvGrade, tvAwards,
            tvExtraActivities, tvInterested, tvWeight, tvHeight;
    ImageView coverPic;
    RelativeLayout relativeAbout;
    LinearLayout linearWeight, linearHeight;
    ImageButton btnUpdateProfile;
    String picturepath;
    Bitmap bitmap;
    User user;
    boolean imageSelected = false;
    Call<ResponseUserDetail> responseUserDetailCall;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() { }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerTimeline = view.findViewById(R.id.recyclerTimeline);
        recyclerTimeline.setLayoutManager(new LinearLayoutManager(requireContext()));
        PostAdapter adapter = new PostAdapter();
        tvAboutMe = view.findViewById(R.id.tvAboutMe);
        coverPic = view.findViewById(R.id.cover_pic);
        tvTown = view.findViewById(R.id.tvTown);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvSchool = view.findViewById(R.id.tvSchool);
        btnUpdateProfile = view.findViewById(R.id.btnEditProfile);
        btnUpdateProfile.setOnClickListener(this);
        tvGrade = view.findViewById(R.id.tvGrade);
        tvAwards = view.findViewById(R.id.tvAwards);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvWeight = view.findViewById(R.id.tvWeight);
        linearHeight = view.findViewById(R.id.linear_height);
        linearWeight = view.findViewById(R.id.linear_weight);
        tvInterested = view.findViewById(R.id.tvInterested);
        tvExtraActivities = view.findViewById(R.id.tvExtraActivities);
        relativeAbout = view.findViewById(R.id.relative_1);
        btnChangeCover = view.findViewById(R.id.changeCover);
        btnChangeCover.setOnClickListener(this);
        tvUpdateBio = view.findViewById(R.id.btnUpdateBio);
        tvUpdateBio.setOnClickListener(this);
        String text = "<u>Update Bio</u>";
        tvUpdateBio.setText(Html.fromHtml(text));
        btnUpdateBio = view.findViewById(R.id.btnEditAbout);
        btnUpdateBio.setOnClickListener(this);
        progress = view.findViewById(R.id.progress);
        recyclerTimeline.setAdapter(adapter);
        getProfileData();
        return view;
    }

    private void getProfileData(){
        progress.setVisibility(View.VISIBLE);
        responseUserDetailCall = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).getUserDetail(
                "Bearer "+currentUser.getToken(), currentUser.getId());
        responseUserDetailCall.enqueue(new Callback<ResponseUserDetail>() {
            @Override
            public void onResponse(Call<ResponseUserDetail> call, Response<ResponseUserDetail> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    user = response.body().getData();
                    User_detail user_detail = user.getUser_detail();
                    if (user.getAbout_us()!=null){
                        relativeAbout.setVisibility(View.VISIBLE);
                        tvAboutMe.setText(user.getAbout_us());
                        tvUpdateBio.setVisibility(View.GONE);
                    }else {
                        relativeAbout.setVisibility(View.GONE);
                        tvUpdateBio.setVisibility(View.VISIBLE);
                    }

                    if (user_detail!=null){
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
                        if (user.getCover()!=null){
                            Picasso.with(getActivity()).load(user.getCover()).placeholder(R.drawable.sample2).fit().into(coverPic);
                        }
                        if (user_detail.getHeight()!=null){
                            linearHeight.setVisibility(View.VISIBLE);
                            tvHeight.setText(user_detail.getHeight());
                        }else {
                            linearHeight.setVisibility(View.GONE);
                        }
                        if (user_detail.getWeight()!=null){
                            linearWeight.setVisibility(View.VISIBLE);
                            tvWeight.setText(user_detail.getWeight());
                        }else {
                            linearWeight.setVisibility(View.GONE);
                        }
                    }

                }else {
                    Toast.makeText(getActivity(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUserDetail> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View view1) {
        if (view1.getId()==R.id.changeCover){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.image_action_dialog, null);
            Button camera = view.findViewById(R.id.camera);
            Button gallery = view.findViewById(R.id.gallery);
            builder.setView(view);
            final AlertDialog alertDialog = builder.create();
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ((ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{
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

                    if ((ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{
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
        }else if (view1.getId()==R.id.btnEditAbout){
            showBioDialog();
        }else if (view1.getId() == R.id.btnUpdateBio){
            showBioDialog();
        }else if (view1.getId() == R.id.btnEditProfile){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.profile_update_dialog, null);
            EditText etGPA = view.findViewById(R.id.etGPA);
            EditText etHobbies = view.findViewById(R.id.etHobbies);
            EditText etActivities = view.findViewById(R.id.etActivities);
            EditText etCareersInterested = view.findViewById(R.id.etCareersInterested);
            if (user.getUser_detail()!=null){
                if (user.getUser_detail().getGpa()!=null){
                    etGPA.setText(user.getUser_detail().getGpa());
                }
                if (user.getUser_detail().getHobbies()!=null){
                    etHobbies.setText(user.getUser_detail().getHobbies());
                }
                if (user.getUser_detail().getActivity()!=null){
                    etActivities.setText(user.getUser_detail().getActivity());
                }
                if (user.getUser_detail().getCareer_interested()!=null){
                    etCareersInterested.setText(user.getUser_detail().getCareer_interested());
                }
            }

            Button btnSave = view.findViewById(R.id.btnSave);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String gpa = etGPA.getText().toString();
                    String hobbies = etHobbies.getText().toString();
                    String activities = etActivities.getText().toString();
                    String careers = etCareersInterested.getText().toString();
                    updateProfile(gpa, hobbies, activities, careers);
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    private void uploadCoverImage(){
        try {
            progress.setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Uri uri = StaticClass.getImageUri(getActivity(), bitmap);
            File f = StaticClass.getFilePathForN(uri, getActivity());
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), f);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("cover", f.getName(), requestBody);
            MultipartBody.Part token = MultipartBody.Part.createFormData("token", currentUser.getToken());
            Call<ResponseUploadCover> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).uploadCover(fileToUpload, token);
            call.enqueue(new Callback<ResponseUploadCover>() {
                @Override
                public void onResponse(Call<ResponseUploadCover> call, Response<ResponseUploadCover> response) {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progress.setVisibility(View.GONE);
                    if (response.isSuccessful()){
                        if (response.body().getType().equals("success")){
                            String url = response.body().getFilename();
                            Glide.with(getActivity()).load(url).placeholder(R.drawable.sample2).into(coverPic);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Success");
                            builder.setMessage("Profile cover updated !");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                        }else{
                            Toast.makeText(getActivity(), ""+response.body().getType(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), ""+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseUploadCover> call, Throwable t) {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }catch (Exception e){
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progress.setVisibility(View.GONE);
            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProfile(String gpa, String hobbies, String activities, String careers){
        progress.setVisibility(View.VISIBLE);
        Call<ResponseUpdateProfile> call = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).updateProfile(currentUser.getToken(),
                gpa, hobbies, activities, careers);
        call.enqueue(new Callback<ResponseUpdateProfile>() {
            @Override
            public void onResponse(Call<ResponseUpdateProfile> call, Response<ResponseUpdateProfile> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Success");
                    builder.setMessage(response.body().getMessage());
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getProfileData();
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Failed");
                    builder.setMessage(response.body().getMessage());
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getProfileData();
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateProfile> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBioDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.about_me_dialog_layout, null);
        EditText etBio = view.findViewById(R.id.etBio);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        if (user!=null){
            if (user.getAbout_us()!=null){
                etBio.setText(user.getAbout_us());
            }
        }
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = etBio.getText().toString();
                if (TextUtils.isEmpty(text)){
                    etBio.setError("Text required");
                    return;
                }
                updateBio(text, alertDialog);
            }
        });
        alertDialog.show();
    }

    private void updateBio(String text, AlertDialog alert){
        progress.setVisibility(View.VISIBLE);
        alert.dismiss();
        Call<ResponseAboutMe> responseAboutCall = RetrofitApiClient.createRetrofitApi(StaticClass.BASE_URL).updateAboutMe(text, currentUser.getToken());
        responseAboutCall.enqueue(new Callback<ResponseAboutMe>() {
            @Override
            public void onResponse(Call<ResponseAboutMe> call, Response<ResponseAboutMe> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body().getType().equals("success")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Success");
                        builder.setMessage(response.body().getMessage());
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getProfileData();
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Failed");
                        builder.setMessage(response.body().getMessage());
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getProfileData();
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                    }
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Failed");
                    builder.setMessage(response.message());
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getProfileData();
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAboutMe> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.fahaddev.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_PHOTO);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturepath = cursor.getString(columnIndex);
                    cursor.close();
                    bitmap = BitmapFactory.decodeFile(picturepath);
                    coverPic.setImageBitmap(bitmap);
                    imageSelected=true;
                    uploadCoverImage();
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
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
        getActivity().sendBroadcast(mediaScanIntent);
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
        uploadCoverImage();
    }
}
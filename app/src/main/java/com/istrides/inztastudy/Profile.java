package com.istrides.inztastudy;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.istrides.inztastudy.connection.ApiConstant;
import com.istrides.inztastudy.connection.ApiGetPost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    TextView name, emailAddress, abtme, mobile, cancelBtn;
    EditText edtName, edtMail, edtPhone, edtAbtme;
    Button savebtn;
    FloatingActionButton fab;
    Boolean edit = true;
    ConstraintLayout screenBg;
    String deviceId;
    ImageView editImg, profileImg;
    ProgressDialog pDialog;
    Call<VideoListModel> list;
    Call<ProfileModel> retrievecall;
    Call<ProfileModel> uploadPic;
    Map<String, Object> profilebody = new HashMap<>();
     Dialog dialog ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_profile, container, false);



        dialog  = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_edit_profile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));







        name = v.findViewById(R.id.nametxt);
        emailAddress = v.findViewById(R.id.emailtxt);
        mobile = v.findViewById(R.id.mobiletxt);
        abtme = v.findViewById(R.id.abouttxt);
        fab = v.findViewById(R.id.fab);
        savebtn = dialog.findViewById(R.id.savbtn);
        cancelBtn = dialog.findViewById(R.id.cancelbtn);
        screenBg = v.findViewById(R.id.screenbg);
        editImg = v.findViewById(R.id.editimg);
        profileImg = v.findViewById(R.id.profilepic);
        edtName = dialog.findViewById(R.id.edtnametxt);
        edtMail = dialog.findViewById(R.id.edtemailtxt);
        edtPhone = dialog.findViewById(R.id.edtmobiletxt);
        edtAbtme = dialog.findViewById(R.id.edtabttxt);


        profilebody.put("api_method", "profile-retrieve");
        profilebody.put("device_id", deviceId);
        profilebody.put("apptype", "main");

        retrieveProfileDetails(profilebody);


        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                opencropimage();


            }
        });


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtName.getText().toString().length() == 0) {

                    Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();

                } else if (edtPhone.getText().toString().length() != 10) {

                    Toast.makeText(getActivity(), "Invalid Mobile Number", Toast.LENGTH_SHORT).show();


                } else if (!isValidEmail(edtMail.getText().toString())) {

                    Toast.makeText(getActivity(), "Invalid Mail id", Toast.LENGTH_SHORT).show();

                } else {

                    Map<String, Object> body = new HashMap<>();

                    body.put("first_name", edtName.getText().toString());
                    body.put("email_id", edtMail.getText().toString());
                    body.put("mobile_number", edtPhone.getText().toString());
                    body.put("about_me", edtAbtme.getText().toString());
                    body.put("device_id", deviceId);
                    body.put("apptype", "main");


                    updateProfile(body);

                }


            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dialog.cancel();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               dialog.show();


            }
        });




        return v;
    }

    private void retrieveProfileDetails(Map<String, Object> body) {

        pDialog = new ProgressDialog(getActivity());
        pDialog.show();
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ApiGetPost service = ApiConstant.geturl().create(ApiGetPost.class);
        retrievecall = service.ProfileRetrieve(body);

        retrievecall.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                pDialog.dismiss();


                ProfileModel listResponse = response.body();

                if (listResponse.getOutput().get(0).getStatus().equalsIgnoreCase("success")) {

                    name.setText(listResponse.getOutput().get(0).getName());
                    mobile.setText(listResponse.getOutput().get(0).getMobileNumber());
                    emailAddress.setText(listResponse.getOutput().get(0).getEmailId());
                    abtme.setText(listResponse.getOutput().get(0).getAboutMe());

                    Glide.with(getActivity()).load(listResponse.getOutput().get(0).getProfilePic()).override(300, 300).apply(RequestOptions.circleCropTransform().placeholder(R.drawable.profile_avatar)).into(profileImg);

                    edtName.setText(listResponse.getOutput().get(0).getName());
                    edtPhone.setText(listResponse.getOutput().get(0).getMobileNumber());
                    edtMail.setText(listResponse.getOutput().get(0).getEmailId());
                    edtAbtme.setText(listResponse.getOutput().get(0).getAboutMe());


                } else {

                   // Toast.makeText(getActivity(), listResponse.getOutput().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

                pDialog.dismiss();
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    private void updateProfile(Map<String, Object> body) {

        pDialog = new ProgressDialog(getActivity());
        pDialog.show();
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ApiGetPost service = ApiConstant.geturl().create(ApiGetPost.class);
        list = service.UpdateProfile(body);

        list.enqueue(new Callback<VideoListModel>() {
            @Override
            public void onResponse(Call<VideoListModel> call, Response<VideoListModel> response) {
                pDialog.dismiss();


                VideoListModel listResponse = response.body();

                if (listResponse.getOutput().get(0).getStatus().equalsIgnoreCase("success")) {

                    retrieveProfileDetails(profilebody);
                  dialog.cancel();

                } else {

                    Toast.makeText(getActivity(), listResponse.getOutput().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<VideoListModel> call, Throwable t) {

                pDialog.dismiss();
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    Glide.with(getActivity()).load(bitmap).override(300, 300).apply(RequestOptions.circleCropTransform()).into(profileImg);
                    File f = new File(getActivity().getCacheDir(), "profileimg");
                    f.createNewFile();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                    RequestBody pic = RequestBody.create(MediaType.parse("image/*"),
                            f);


                    RequestBody deviceid = RequestBody.create(MediaType.parse("text/plain"),
                            deviceId);

                    RequestBody appType = RequestBody.create(MediaType.parse("text/plain"),
                            "main");

                    uploadpic(pic, deviceid,appType);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void uploadpic(RequestBody pic, RequestBody deviceid,RequestBody appType) {

        pDialog = new ProgressDialog(getActivity());
        pDialog.show();
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        ApiGetPost service = ApiConstant.geturl().create(ApiGetPost.class);
        uploadPic = service.PicUpload(pic,deviceid,appType);

        uploadPic.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                pDialog.dismiss();


                ProfileModel listResponse = response.body();

                if (listResponse.getOutput().get(0).getStatus().equalsIgnoreCase("success")) {

                    Toast.makeText(getActivity(), "Profile Pic Uploaded", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getActivity(), listResponse.getOutput().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

                pDialog.dismiss();
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public void opencropimage() {

        CropImage.activity()
                .start(getContext(), this);

    }
}

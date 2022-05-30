package com.bawp.customcard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import static com.bawp.customcard.Constants.KEY_IMAGE_URI;


public class SelectImageActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 100 ;
    private Button selectImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        selectImageButton = findViewById(R.id.select_image_button);

        selectImageButton.setOnClickListener(v -> {
            if (isPermissionGranted()){
                Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(chooseIntent,REQUEST_CODE_IMAGE);
            }else{
                takePermission();
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==Activity.RESULT_OK){
            if (requestCode == REQUEST_CODE_IMAGE) {
                handleImageRequestResult(data);
            }
        }else {
            Toast.makeText(this, "Can't get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleImageRequestResult(@org.jetbrains.annotations.Nullable Intent data) {

        Uri imageUri = null;
        if (data.getClipData() !=null){
            imageUri = data.getClipData().getItemAt(0).getUri();

        }else if (data.getData()!=null){
            imageUri = data.getData();
        }else{
            Toast.makeText(this, "Invalid image", Toast.LENGTH_SHORT).show();
        }

        Intent filter = new Intent(this,CreateCardActivity.class);

        if (imageUri != null) {
            filter.putExtra(KEY_IMAGE_URI,imageUri.toString());
        }

        startActivity(filter);
    }

    private void takePermission() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){

            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivity(intent);
            }catch (Exception e){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
        }
    }

    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int readExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            return readExternalStoragePermission == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0){
            if (requestCode==101){
                Boolean readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (readExternalStorage){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }else{
                    takePermission();
                }
            }
        }
    }
}
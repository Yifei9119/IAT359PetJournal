package com.example.iat359_petjournal;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

public class AddPhoto extends AppCompatActivity implements View.OnClickListener {

//    declare variables
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    private CameraProvider cameraProvider;

    private Button buttonCaptureSave, buttonCaptureShow;
    private ImageCapture imageCapture;
    private ImageView imageViewCaptured;

    private ImageButton back;
    private String mImageFileLocation = "";
    private static final int img_id = 1;

    private MyDatabase db;

    private MyHelper helper;

    int photoNum = 1;

    byte[] bmap = null;
    // new
    private Executor executor = Executors.newSingleThreadExecutor();
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photo);

//initialize variables
        buttonCaptureSave = findViewById(R.id.buttonCaptureSave);
        buttonCaptureShow = findViewById(R.id.buttonCaptureShow);
        imageViewCaptured = findViewById(R.id.imageViewCapturedImg);

        buttonCaptureSave.setOnClickListener(this);
        buttonCaptureShow.setOnClickListener(this);
        db = new MyDatabase(this);
        helper = new MyHelper(this);
        back = findViewById(R.id.backButton);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonCaptureShow) {
            if ((ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{
                            Manifest.permission.CAMERA,
                    }, 123);
                }
            } else {
//                start camera
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, img_id);
            }


        } else if (view.getId() == R.id.buttonCaptureSave) {
            photoNum++;

//            inserting photo to db
            db.insertPhotoData(photoNum, bmap, ""+bmap.length);
            Toast t2 = Toast.makeText(this, "Photo Inserted",Toast.LENGTH_LONG);
            t2.show();

            Intent i = new Intent(view.getContext(), Journal.class);
            startActivity(i);

        }
    }


//go back to last activity
    public void goBack(View view){
        Intent i = new Intent(view.getContext(), Journal.class);
        startActivity(i);
    }

//    based on the result of take a photo button for camera
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        imageViewCaptured.setImageBitmap(photo);
        imageViewCaptured.setRotation(90);
        imageViewCaptured.setScaleType(ImageView.ScaleType.FIT_XY);

        long timeStamp = System.currentTimeMillis();
        OutputStream fos;
        try {
//            saving photo to media storage
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timeStamp);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

            Uri imageURI = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(Objects.requireNonNull(imageURI));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Objects.requireNonNull(fos);

            photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            bmap = outputStream.toByteArray();
            Toast.makeText(AddPhoto.this, "Saving...", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}


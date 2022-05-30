package com.example.smiledetector;


import android.content.ContentValues;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.smiledetector.adapter.FaceDetectionAdapter;
import com.example.smiledetector.model.FaceDetectionModel;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import com.google.mlkit.vision.common.InputImage;

import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;


import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements FrameProcessor {

    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private Uri imageUri;

    private Facing cameraFacing = Facing.FRONT;
    private ImageView imageView;
    private ImageView imageViewCropped;
    private CameraView cameraView;
    private RecyclerView bottomSheetRecyclerView;
    private BottomSheetBehavior bottomSheetBehavior;
    private ArrayList<FaceDetectionModel> faceDetectionModels;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        faceDetectionModels = new ArrayList<>();
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        imageView = findViewById(R.id.faceDetection_camera_imageView);
        imageViewCropped = findViewById(R.id.faceDetection_imageView);
        cameraView = findViewById(R.id.faceDetection_camera_view);
        ImageButton toggleButton = findViewById(R.id.faceDetection_camera_switchButton);
        ImageButton bottomSheetButton = findViewById(R.id.bottom_sheet_imageButton);
        bottomSheetRecyclerView = findViewById(R.id.bottom_sheet_recyclerView);
        ImageButton cameraButton = findViewById(R.id.faceDetection_cameraButton);
        linearLayout=findViewById(R.id.facedetected_LinearLayout);
        linearLayout.setVisibility(View.GONE);

        cameraButton.setOnClickListener(view -> {
            cameraView.open();
            linearLayout.setVisibility(View.GONE);
        });

        toggleButton.setOnClickListener(v -> {
            cameraFacing = (cameraFacing == Facing.FRONT) ? Facing.BACK : Facing.FRONT;
            // cameraFacing = (cameraFacing == Facing.FRONT) ? Facing.BACK : Facing.FRONT;
            cameraView.setFacing(cameraFacing);

        });

        cameraView.setFacing(cameraFacing);
        cameraView.setLifecycleOwner(MainActivity.this);
        cameraView.addFrameProcessor(MainActivity.this);


        bottomSheetButton.setOnClickListener(view -> {
            Log.i("Crop Image","Done");


            showImagePicDialog();


        });
        bottomSheetRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        FaceDetectionAdapter faceDetectionAdapter = new FaceDetectionAdapter(faceDetectionModels, this);
        bottomSheetRecyclerView.setAdapter(faceDetectionAdapter);


    }


    private void showImagePicDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image From");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                pickFromCamera();
            } else if (which == 1) {
                pickFromGallery();
            }
        });
        builder.create().show();
    }



    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    private void pickFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , STORAGE_REQUEST);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if ( requestCode == STORAGE_REQUEST){

            Uri selectedImage = data != null ? data.getData() : null;
            RequestOptions myOptions = new RequestOptions()// or centerCrop
                    .override(480, 360);

            Glide.with(this).asBitmap().apply(myOptions).load(selectedImage).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    if (resultCode==RESULT_OK && selectedImage!=null){
                        Log.i("Chobi", "Ache");
                        //MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage)
                        analyzeImage(resource);
                    }
                }
            });

        }if (requestCode == CAMERA_REQUEST){
            if (resultCode==RESULT_OK) {
                try {

                    RequestOptions myOptions = new RequestOptions()// or centerCrop
                            .override(480, 360);

                    Glide.with(this).asBitmap().apply(myOptions).load(imageUri).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Log.i("Chobi", "Ache");
                            //MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage)
                            analyzeImage(resource);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private void analyzeImage(Bitmap bitmap) {
        if (bitmap==null){
            Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show();
        }else {
            imageViewCropped.setImageBitmap(null);
            faceDetectionModels.clear();
            Objects.requireNonNull(bottomSheetRecyclerView.getAdapter()).notifyDataSetChanged();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            showProgress();
            InputImage image = InputImage.fromBitmap(bitmap,0);
            FaceDetectorOptions options =
                    new FaceDetectorOptions.Builder()
                            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                            .enableTracking()
                            .build();

            FaceDetector detector = FaceDetection.getClient(options);
            detector.process(image).addOnSuccessListener(faces -> {
                Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
                linearLayout.setVisibility(View.VISIBLE);
                detectFaces(faces,mutableBitmap);
                imageViewCropped.setImageBitmap(mutableBitmap);
                cameraView.close();

                hideProgress();
                Objects.requireNonNull(bottomSheetRecyclerView.getAdapter()).notifyDataSetChanged();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }).addOnFailureListener(e -> {
                Toast.makeText(MainActivity.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                hideProgress();
            });

        }
    }

    private void detectFaces(List<Face> faces, Bitmap mutableBitmap) {

        if (faces==null && mutableBitmap==null) {

            Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show();
        }
            Canvas canvas = new Canvas(mutableBitmap);

            Paint facePaint = new Paint();
            facePaint.setColor(Color.GREEN);
            facePaint.setStyle(Paint.Style.STROKE);
            facePaint.setStrokeWidth(5f);

            Paint faceTextPaint = new Paint();
            faceTextPaint.setColor(Color.BLUE);
            faceTextPaint.setTextSize(50f);
            faceTextPaint.setTypeface(Typeface.SANS_SERIF);

            Paint landMarkPaint = new Paint();
            landMarkPaint.setColor(Color.RED);
            landMarkPaint.setStyle(Paint.Style.FILL);
            landMarkPaint.setStrokeWidth(5f);

        if (faces != null) {
            for (int i=0;i<faces.size();i++){
                canvas.drawRect(faces.get(i).getBoundingBox(),facePaint);
                canvas.drawText("Face "+ (i+1) ,
                        (faces.get(i).getBoundingBox().centerX()-(faces.get(i).getBoundingBox().width() >> 1)+8f),
                        (faces.get(i).getBoundingBox().centerY()-(faces.get(i).getBoundingBox().width() >> 1)-8f),
                        faceTextPaint);
                Face face = faces.get(i);
                if (face.getLandmark(FaceLandmark.LEFT_EYE)!=null){
                    FaceLandmark leftEye = face.getLandmark(FaceLandmark.LEFT_EYE);
                    canvas.drawCircle(
                            Objects.requireNonNull(leftEye).getPosition().x,
                            leftEye.getPosition().y,
                            8f,
                            landMarkPaint);
                }

                if (face.getLandmark(FaceLandmark.RIGHT_EYE)!=null){
                    FaceLandmark rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE);
                    canvas.drawCircle(
                            Objects.requireNonNull(rightEye).getPosition().x,
                            rightEye.getPosition().y,
                            8f,
                            landMarkPaint);
                }

                if (face.getLandmark(FaceLandmark.NOSE_BASE)!=null){
                    FaceLandmark noseBase = face.getLandmark(FaceLandmark.NOSE_BASE);
                    canvas.drawCircle(
                            Objects.requireNonNull(noseBase).getPosition().x,
                            noseBase.getPosition().y,
                            8f,
                            landMarkPaint);
                }

                if (face.getLandmark(FaceLandmark.LEFT_EAR)!=null){
                    FaceLandmark leftEar = face.getLandmark(FaceLandmark.LEFT_EAR);
                    canvas.drawCircle(
                            Objects.requireNonNull(leftEar).getPosition().x,
                            leftEar.getPosition().y,
                            8f,
                            landMarkPaint);
                }

                if (face.getLandmark(FaceLandmark.RIGHT_EAR)!=null){
                    FaceLandmark rightEar = face.getLandmark(FaceLandmark.RIGHT_EAR);
                    canvas.drawCircle(
                            Objects.requireNonNull(rightEar).getPosition().x,
                            rightEar.getPosition().y,
                            8f,
                            landMarkPaint);
                }

                if (face.getLandmark(FaceLandmark.RIGHT_EAR)!=null){
                    FaceLandmark rightEar = face.getLandmark(FaceLandmark.RIGHT_EAR);
                    canvas.drawCircle(
                            Objects.requireNonNull(rightEar).getPosition().x,
                            rightEar.getPosition().y,
                            8f,
                            landMarkPaint);
                }

                if (face.getLandmark(FaceLandmark.MOUTH_LEFT)!=null &&
                        face.getLandmark(FaceLandmark.MOUTH_RIGHT)!=null &&
                        face.getLandmark(FaceLandmark.MOUTH_BOTTOM)!=null){
                    FaceLandmark mouthLeft = face.getLandmark(FaceLandmark.MOUTH_LEFT);
                    FaceLandmark mouthRight = face.getLandmark(FaceLandmark.MOUTH_RIGHT);
                    FaceLandmark mouthBottom = face.getLandmark(FaceLandmark.MOUTH_BOTTOM);
                    canvas.drawLine(
                            Objects.requireNonNull(mouthLeft).getPosition().x,
                            mouthLeft.getPosition().y,
                            Objects.requireNonNull(mouthBottom).getPosition().x,
                            mouthBottom.getPosition().y,
                            landMarkPaint);
                    canvas.drawLine(
                            mouthBottom.getPosition().x,
                            mouthBottom.getPosition().y,
                            Objects.requireNonNull(mouthRight).getPosition().x,
                            mouthRight.getPosition().y,
                            landMarkPaint);

                }

                Float smiling=face.getSmilingProbability();
                Float lefteye=face.getLeftEyeOpenProbability();
                Float righteye=face.getRightEyeOpenProbability();

                if (smiling!=null && lefteye!=null && righteye !=null){
                    faceDetectionModels.add(new FaceDetectionModel(i+1,"Smiling  " +smiling *100+"%"));
                    faceDetectionModels.add(new FaceDetectionModel(i+1,"Left Eye Open  " + lefteye*100+"%"));
                    faceDetectionModels.add(new FaceDetectionModel(i+1,"Right Eye Open  " + righteye*100+"%"));
                }



            }
        }

    }


    private void showProgress() {
        findViewById(R.id.bottom_sheet_imageButton).setVisibility(View.GONE);
        findViewById(R.id.bottom_sheet_progressBar).setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        findViewById(R.id.bottom_sheet_imageButton).setVisibility(View.VISIBLE);
        findViewById(R.id.bottom_sheet_progressBar).setVisibility(View.GONE);
    }


    @Override
    public void process(@NonNull Frame frame) {
        int width = frame.getSize().getWidth();
        int height = frame.getSize().getHeight();
        int rotation =frame.getRotationToUser();

        InputImage inputImage = InputImage.fromByteArray(frame.getData(),
                width,
                height,
                rotation ,
                InputImage.IMAGE_FORMAT_NV21);

        FaceDetectorOptions detectorOptions = new FaceDetectorOptions.Builder().setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL).build();

        FaceDetector faceDetector = FaceDetection.getClient(detectorOptions);
        faceDetector.process(inputImage).addOnSuccessListener(faces -> {

            Bitmap bitmap = Bitmap.createBitmap(height,width, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);

            Paint dotPaint = new Paint();
            dotPaint.setColor(Color.RED);
            dotPaint.setStyle(Paint.Style.FILL);
            dotPaint.setStrokeWidth(3f);

            Paint linePaint = new Paint();
            linePaint.setColor(Color.GREEN);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(2f);

            for (Face face:faces){
                List<PointF> faceContours = Objects.requireNonNull(face.getContour(FaceContour.FACE)).getPoints();

                for (int i=0;i<faceContours.size();i++){
                    PointF faceContour;
                    faceContour = faceContours.get(i);
                    if (i!=faceContours.size()-1){
                        canvas.drawLine(faceContour.x,
                                faceContour.y,
                                faceContours.get(i+1).x,
                                faceContours.get(i+1).y,
                                linePaint);
                    }else {
                        canvas.drawLine(faceContour.x,
                                faceContour.y,
                                faceContours.get(0).x,
                                faceContours.get(0).y,
                                linePaint);
                    }
                    canvas.drawCircle(faceContour.x,
                            faceContour.y,
                           4f,
                            dotPaint);
                }

                List<PointF> leftEyebrowTopCountours = Objects.requireNonNull(face.getContour(
                        FaceContour.LEFT_EYEBROW_TOP)).getPoints();
                for (int i = 0; i < leftEyebrowTopCountours.size(); i++) {
                    PointF contour = leftEyebrowTopCountours.get(i);
                    if (i != (leftEyebrowTopCountours.size() - 1))
                        canvas.drawLine(contour.x, contour.y, leftEyebrowTopCountours.get(i + 1).x,leftEyebrowTopCountours.get(i + 1).y, linePaint);
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);

                }

                List<PointF> rightEyebrowTopCountours = Objects.requireNonNull(face.getContour(
                        FaceContour.RIGHT_EYEBROW_TOP)).getPoints();
                for (int i = 0; i < rightEyebrowTopCountours.size(); i++) {
                    PointF contour = rightEyebrowTopCountours.get(i);
                    if (i != (rightEyebrowTopCountours.size() - 1))
                        canvas.drawLine(contour.x, contour.y, rightEyebrowTopCountours.get(i + 1).x,rightEyebrowTopCountours.get(i + 1).y, linePaint);
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);

                }

                List<PointF> rightEyebrowBottomCountours = Objects.requireNonNull(face.getContour(
                        FaceContour.RIGHT_EYEBROW_BOTTOM)).getPoints();
                for (int i = 0; i < rightEyebrowBottomCountours.size(); i++) {
                    PointF contour = rightEyebrowBottomCountours.get(i);
                    if (i != (rightEyebrowBottomCountours.size() - 1))
                        canvas.drawLine(contour.x, contour.y, rightEyebrowTopCountours.get(i + 1).x,rightEyebrowTopCountours.get(i + 1).y, linePaint);
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);

                }

                List<PointF> leftEyeContours = Objects.requireNonNull(face.getContour(
                        FaceContour.LEFT_EYE)).getPoints();
                for (int i = 0; i < leftEyeContours.size(); i++) {
                    PointF contour = leftEyeContours.get(i);
                    if (i != (leftEyeContours.size() - 1)){
                        canvas.drawLine(contour.x, contour.y, leftEyeContours.get(i + 1).x,leftEyeContours.get(i + 1).y, linePaint);

                    }else {
                        canvas.drawLine(contour.x, contour.y, leftEyeContours.get(0).x,
                                leftEyeContours.get(0).y, linePaint);
                    }
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);


                }

                List<PointF> rightEyeContours = Objects.requireNonNull(face.getContour(
                        FaceContour.RIGHT_EYE)).getPoints();
                for (int i = 0; i < rightEyeContours.size(); i++) {
                    PointF contour = rightEyeContours.get(i);
                    if (i != (rightEyeContours.size() - 1)){
                        canvas.drawLine(contour.x, contour.y, rightEyeContours.get(i + 1).x,rightEyeContours.get(i + 1).y, linePaint);

                    }else {
                        canvas.drawLine(contour.x, contour.y, rightEyeContours.get(0).x,
                                rightEyeContours.get(0).y, linePaint);
                    }
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);


                }

                List<PointF> upperLipTopContour = Objects.requireNonNull(face.getContour(
                        FaceContour.UPPER_LIP_TOP)).getPoints();
                for (int i = 0; i < upperLipTopContour.size(); i++) {
                    PointF contour = upperLipTopContour.get(i);
                    if (i != (upperLipTopContour.size() - 1)){
                        canvas.drawLine(contour.x, contour.y,
                                upperLipTopContour.get(i + 1).x,
                                upperLipTopContour.get(i + 1).y, linePaint);
                    }
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);

                }

                List<PointF> upperLipBottomContour = Objects.requireNonNull(face.getContour(
                        FaceContour.UPPER_LIP_BOTTOM)).getPoints();
                for (int i = 0; i < upperLipBottomContour.size(); i++) {
                    PointF contour = upperLipBottomContour.get(i);
                    if (i != (upperLipBottomContour.size() - 1)){
                        canvas.drawLine(contour.x, contour.y, upperLipBottomContour.get(i + 1).x,upperLipBottomContour.get(i + 1).y, linePaint);
                    }
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);

                }
                List<PointF> lowerLipTopContour = Objects.requireNonNull(face.getContour(
                        FaceContour.LOWER_LIP_TOP)).getPoints();
                for (int i = 0; i < lowerLipTopContour.size(); i++) {
                    PointF contour = lowerLipTopContour.get(i);
                    if (i != (lowerLipTopContour.size() - 1)){
                        canvas.drawLine(contour.x, contour.y, lowerLipTopContour.get(i + 1).x,lowerLipTopContour.get(i + 1).y, linePaint);
                    }
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);

                }
                List<PointF> lowerLipBottomContour = Objects.requireNonNull(face.getContour(
                        FaceContour.LOWER_LIP_BOTTOM)).getPoints();
                for (int i = 0; i < lowerLipBottomContour.size(); i++) {
                    PointF contour = lowerLipBottomContour.get(i);
                    if (i != (lowerLipBottomContour.size() - 1)){
                        canvas.drawLine(contour.x, contour.y, lowerLipBottomContour.get(i + 1).x,lowerLipBottomContour.get(i + 1).y, linePaint);
                    }
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);

                }

                List<PointF> noseBridgeContours = Objects.requireNonNull(face.getContour(
                        FaceContour.NOSE_BRIDGE)).getPoints();
                for (int i = 0; i < noseBridgeContours.size(); i++) {
                    PointF contour = noseBridgeContours.get(i);
                    if (i != (noseBridgeContours.size() - 1)) {
                        canvas.drawLine(contour.x, contour.y, noseBridgeContours.get(i + 1).x,noseBridgeContours.get(i + 1).y, linePaint);
                    }
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);

                }

                List<PointF> noseBottomContours = Objects.requireNonNull(face.getContour(
                        FaceContour.NOSE_BOTTOM)).getPoints();
                for (int i = 0; i < noseBottomContours.size(); i++) {
                    PointF contour = noseBottomContours.get(i);
                    if (i != (noseBottomContours.size() - 1)) {
                        canvas.drawLine(contour.x, contour.y, noseBottomContours.get(i + 1).x,noseBottomContours.get(i + 1).y, linePaint);
                    }
                    canvas.drawCircle(contour.x, contour.y, 4f, dotPaint);

                }
                if (cameraFacing == Facing.FRONT) {
                    //Flip image!
                    Matrix matrix = new Matrix();
                    matrix.preScale(-1f, 1f);
                    Bitmap flippedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            bitmap.getWidth(), bitmap.getHeight(),
                            matrix, true);
                    imageView.setImageBitmap(flippedBitmap);
                }else
                    imageView.setImageBitmap(bitmap);
            }



        }).addOnFailureListener(e -> imageView.setImageBitmap(null));


    }

}
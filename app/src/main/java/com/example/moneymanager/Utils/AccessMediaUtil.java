package com.example.moneymanager.Utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.theartofdev.edmodo.cropper.CropImage;

//import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import static android.os.Environment.getExternalStoragePublicDirectory;


public class AccessMediaUtil extends AppCompatActivity {

    String currentPhotoPath;
    Uri photoURI,videoURI;
    List<String> mPaths;
    String selectedVideoPath;
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int REQUEST_FROM_GALLERY = 2;
    public static final int ACCESS_CAMERA_GALLERY = 3;
    public static final int REQUEST_TAKE_GALLERY_VIDEO=4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        selectImage();
        dispatchPickFromGalleryIntent();
    }

    public void selectImage() {
        final CharSequence[] options = {"Choose from Gallery", "Choose from Video", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo !!");
        builder.setCancelable(false);
        builder.setItems(options, (dialog, item) -> {
           if (options[item].equals("Choose from Gallery")) {
                dispatchPickFromGalleryIntent();
            } else if (options[item].equals("Choose from Video")) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, REQUEST_TAKE_GALLERY_VIDEO);
                finish();
            } else {
                dialog.dismiss();
                onBackPressed();
            }
        });
        builder.show();
    }



    private void dispatchPickFromGalleryIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_FROM_GALLERY);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);

        Intent returnFromGalleryIntent = new Intent();
        returnFromGalleryIntent.putExtra("picturePath", currentPhotoPath);
        returnFromGalleryIntent.putExtra("name", f.getName());
        setResult(RESULT_OK, returnFromGalleryIntent);
        finish();
    }


    private void galleryAddvideo() {
        Intent returnFromGalleryIntent = new Intent();
        returnFromGalleryIntent.putExtra("videoPath", String.valueOf(mPaths));
        Log.d("videoPath",""+mPaths);
        setResult(RESULT_OK, returnFromGalleryIntent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            Log.d("videoPath",""+data.getData());
            if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
                photoURI=data.getData();
                CropImage.activity(photoURI).start(this);
                Log.d("takephoto",""+photoURI);
            }
            else if (requestCode == REQUEST_FROM_GALLERY && resultCode == RESULT_OK) {
                photoURI = data.getData();

                if(getIntent().getStringExtra("type")==null){
                    if(getIntent().getBooleanExtra("type",false)){
                        CropImage.activity(photoURI).setAspectRatio(780,340).start(this);
                    }else{
                        CropImage.activity(photoURI).setAspectRatio(170,170).start(this);
                    }
                }else{
                    if(getIntent().getStringExtra("type").equalsIgnoreCase("POST")){
                        CropImage.activity(photoURI).start(this);
                    }
                    else if(getIntent().getBooleanExtra("type",false)){
                        CropImage.activity(photoURI).setAspectRatio(780,340).start(this);
                    }else{
                        CropImage.activity(photoURI).setAspectRatio(170,170).start(this);
                    }
                }

                Log.d("selectedVideoPath",""+selectedVideoPath);
            }
            else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO && resultCode == RESULT_OK) {
                Uri contentURI = data.getData();
                selectedVideoPath = getPath(contentURI);
                Log.d("selectedVideoPath",""+selectedVideoPath);
                Intent returnFromGalleryIntent = new Intent();
                returnFromGalleryIntent.putExtra("VideoePath", selectedVideoPath);
                Log.d("VideoePath",""+videoURI);
                setResult(RESULT_OK, returnFromGalleryIntent);
                finish();

//                mPaths =  data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
//                Intent returnFromGalleryIntent = new Intent();
//                returnFromGalleryIntent.putExtra("videoPath", String.valueOf(mPaths));
//                Log.d("videoPath",""+mPaths);
//                setResult(RESULT_OK, returnFromGalleryIntent);
//                finish();
//                Log.d("videoPath",""+mPaths);
//                MKPlayerActivity.configPlayer(this).play(mPaths.get(0));

            } else {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                photoURI = result.getUri();
                Log.d("selectedVideoPath",""+selectedVideoPath);

                if (resultCode == RESULT_OK) {
                    Log.d("selectedVideoPath",""+selectedVideoPath);
                    currentPhotoPath = photoURI.getPath();
                    galleryAddPic();
                    galleryAddvideo();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

}

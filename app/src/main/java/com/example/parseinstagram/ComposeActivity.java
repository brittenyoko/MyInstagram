//package com.example.parseinstagram;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.media.ExifInterface;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.FileProvider;
//
//import com.example.parseinstagram.Models.Post;
//import com.parse.ParseException;
//import com.parse.ParseFile;
//import com.parse.ParseUser;
//import com.parse.SaveCallback;
//
//import java.io.File;
//import java.io.IOException;
//
//public class ComposeFragment extends AppCompatActivity {
//    private Button capturebtn;
//    private EditText etDescription;
//    private final String TAG = "LoginActivity";
//    private Button submitbtn;
//    private ImageView ivPost;
//    public final String APP_TAG = "MyCustomApp";
//    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
//    public String photoFileName = "photo.jpg";
//    private File photoFile;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_compose);
//        etDescription = findViewById(R.id.describe_et);
//        capturebtn = findViewById(R.id.capture_btn);
//        submitbtn = findViewById(R.id.submit_btn);
//        ivPost = findViewById(R.id.ivPost);
//
//        submitbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String description = etDescription.getText().toString();
//                ParseUser user = ParseUser.getCurrentUser();
//                if (photoFile == null || ivPost.getDrawable() == null) {
//                    Log.e(TAG, "No Photo Submitted");
//                    Toast.makeText(ComposeFragment.this,"No Image Selected", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                savePost(description,user, photoFile);
//            }
//        });
//        capturebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onLaunchCamera(view);
//            }
//        });
//    }
//
//    private void savePost(String description, ParseUser parseUser,File photoFile) {
//
//        Post post= new Post();
//        post.setDescription(description);
//        post.setUser(parseUser);
//        post.setImage( new ParseFile(photoFile));
//        post.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e != null) {
//                    Log.e(TAG, "Error while saving");
//                    e.printStackTrace();
//                    return;
//                }
//                Log.d(TAG,"Success");
//                etDescription.setText("");
//            }
//        });
//    }
//    public void onLaunchCamera(View view) {
//        // create Intent to take a picture and return control to the calling application
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Create a File reference to access to future access
//        photoFile = getPhotoFileUri(photoFileName);
//        // wrap File object into a content provider
//        // required for API >= 24
//        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
//        Uri fileProvider = FileProvider.getUriForFile(ComposeFragment.this, "com.codepath.fileprovider", photoFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//
//        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
//        // So as long as the result is not null, it's safe to use the intent.
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // Start the image capture intent to take photo
//            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//        }
//    }
//    // Returns the File for a photo stored on disk given the fileName
//    public File getPhotoFileUri(String fileName) {
//        // Get safe storage directory for photos
//        // Use `getExternalFilesDir` on Context to access package-specific directories.
//        // This way, we don't need to request external read/write runtime permissions.
//        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
//            Log.d(TAG, "failed to create directory");
//        }
//
//        // Return the file target for the photo based on filename
//        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
//
//        return file;
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                // by this point we have the camera photo on disk
//                //Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                Bitmap takenImage = rotateBitmapOrientation(photoFile.getAbsolutePath());
//                // RESIZE BITMAP, see section below
//                // Load the taken image into a preview
//                ivPost.setImageBitmap(takenImage);
//            } else { // Result was a failure
//                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//    public Bitmap rotateBitmapOrientation(String photoFilePath) {
//        // Create and configure BitmapFactory
//        BitmapFactory.Options bounds = new BitmapFactory.Options();
//        bounds.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(photoFilePath, bounds);
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
//        // Read EXIF Data
//        ExifInterface exif = null;
//        try {
//            exif = new ExifInterface(photoFilePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
//        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
//        int rotationAngle = 0;
//        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
//        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
//        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
//        // Rotate Bitmap
//        Matrix matrix = new Matrix();
//        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
//        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
//        // Return result
//        return rotatedBitmap;
//    }
//}

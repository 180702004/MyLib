package com.example.mylib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

// Emre 180702004

public class DownloadImageTask extends AsyncTask<Object, Void, Bitmap> {
    ImageView imageView;
    String imagePath;
    Bitmap mBytes;

    public DownloadImageTask(ImageView imageView, String imagePath) {
        this.imageView = imageView;
        this.imagePath = imagePath;
    }

    protected Bitmap doInBackground(Object... params) {
        try {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference imageRef = storage.getReference().child(imagePath);
            imageRef.getBytes(8*1024*1024).
                    addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            mBytes = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            imageView.setImageBitmap(mBytes);
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(imageView.getContext(), "Error in separate thread", Toast.LENGTH_SHORT).show();

            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mBytes;
    }
}
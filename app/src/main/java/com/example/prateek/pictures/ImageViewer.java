package com.example.prateek.pictures;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ImageViewer extends AppCompatActivity {
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        String s = getIntent().getStringExtra("imagePath");
        Bitmap btm = BitmapFactory.decodeFile(s);
        try {
            ExifInterface exif = new ExifInterface(s);
            int orientation  = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();
            if(orientation ==6){
                matrix.postRotate(90);
            }
            else if(orientation == 3){
                matrix.postRotate(180);
            }
            else if(orientation == 8){
                matrix.postRotate(270);
            }
            btm  = Bitmap.createBitmap(btm, 0 ,0, btm.getWidth(), btm.getHeight(), matrix, true);
        }
        catch (Exception e) {

        }
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView2.setImageBitmap(btm);
    }
}

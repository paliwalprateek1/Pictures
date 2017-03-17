package com.example.prateek.pictures;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Gallery extends AppCompatActivity {
    private ArrayList<String> images;
    String str[] = new String[100000];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            GridView gallery = (GridView) findViewById(R.id.galleryGridView);

            gallery.setAdapter(new ImageAdapter(this));

        final ImageView imageView = (ImageView)findViewById(R.id.imageView);


        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    if (null != images && !images.isEmpty()) {
                        int a = position;
                        Intent intent = new Intent();
                        intent.setClass(Gallery.this, ImageViewer.class);
                        intent.putExtra("imagePath", str[a]);
                        startActivity(intent);
                    }

                }
            });

        }

        /**
         * The Class ImageAdapter.
         */
        private class ImageAdapter extends BaseAdapter {

            /** The context. */
            private Activity context;

            /**
             * Instantiates a new image adapter.
             *
             * @param localContext
             *            the local context
             */
            public ImageAdapter(Activity localContext) {
                context = localContext;
                images = getAllShownImagesPath(context);
            }

            public int getCount() {
                return images.size();
            }

            public Object getItem(int position) {
                return position;
            }

            public long getItemId(int position) {
                return position;
            }

            public View getView(final int position, View convertView,
                                ViewGroup parent) {
                ImageView picturesView;
                if (convertView == null) {
                    picturesView = new ImageView(context);
                    picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    picturesView
                            .setLayoutParams(new GridView.LayoutParams(100, 100));

                } else {
                    picturesView = (ImageView) convertView;
                }

                Glide.with(context).load(images.get(position))
                        .centerCrop()
                        .into(picturesView);

                return picturesView;
            }

            /**
             * Getting All Images Path.
             *
             * @param activity
             *            the activity
             * @return ArrayList with images Path
             */
            private ArrayList<String> getAllShownImagesPath(Activity activity) {
                Uri uri;
                Cursor cursor;
                int column_index_data, column_index_folder_name;
                ArrayList<String> listOfAllImages = new ArrayList<String>();
                String absolutePathOfImage = null;
                uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                String[] projection = { MediaStore.MediaColumns.DATA,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

                cursor = activity.getContentResolver().query(uri, projection, null,
                        null, null);
                String s= uri.toString();
                Log.d("hello"+s, "aloha");

                column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                column_index_folder_name = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                int i=0;
                while (cursor.moveToNext()) {
                    absolutePathOfImage = cursor.getString(column_index_data);
                    str[i]=absolutePathOfImage.toString();
                    i++;

                    listOfAllImages.add(absolutePathOfImage);
                }
               // Log.d("her"+str[3], "ooooooooooooo");
                return listOfAllImages;
            }
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


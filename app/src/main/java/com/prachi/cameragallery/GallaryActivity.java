package com.prachi.cameragallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class GallaryActivity extends AppCompatActivity {

    private ImageView image;
    private Button btnSaveImage;
    String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);

        image=findViewById(R.id.Image);
        btnSaveImage=findViewById(R.id.btnSaveImage);

        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });


    }

    private void BrowseImage() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_OK){
            if (data==null)
            {
                Toast.makeText(this, "Please enter an image", Toast.LENGTH_SHORT).show();
            }
        }

        Uri uri=data.getData();
        imagePath=getRealPathFromUri(uri);
        image.setImageURI(uri);

    }

    private String getRealPathFromUri(Uri uri){
        String[] projection={MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor =loader.loadInBackground();
        int colIndex=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result=cursor.getString(colIndex);
        cursor.close();
        return result;
    }
}

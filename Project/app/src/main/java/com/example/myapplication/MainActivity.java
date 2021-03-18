package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.ClipboardManager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private Button button;
    private ClipboardManager clipboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Permissions
        SetPermission();
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        Toast toast = Toast.makeText(this, "Hello Android!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0,160);
        toast.show();
        button.setOnClickListener(v -> testLastImage());
    }

    @SuppressLint("WrongConstant")
    void SetPermission(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    this.finish();
                })
                .start();
    }
    private void testLastImage(){
        Bitmap bitmap = GetLastImage();
        if(bitmap == null)
            Toast.makeText(this,"Галерея пуста",Toast.LENGTH_LONG).show();
        else
            imageView.setImageBitmap(bitmap);
    }

    private Bitmap GetLastImage(){
        Cursor cursor;
        String[] projection = new String[] {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATE_ADDED
        };

        String selection = MediaStore.Images.Media.DATE_ADDED+" >= ?";
        String[] selectionArgs = new String[]{String.valueOf(System.currentTimeMillis()/1000-86400)};

        String sortOrder = MediaStore.Images.Media.DATE_ADDED;
        cursor = getApplicationContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        );
        if(cursor == null || !cursor.moveToLast())
            return null;
        long id = cursor.getLong(0);//id колонки id всегда 0
        cursor.close();
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

        ContentResolver resolver = getApplicationContext().getContentResolver();

        Bitmap bitmap = Bitmap.createBitmap(100, 100,
                Bitmap.Config.ARGB_8888);
        try {
            InputStream stream = resolver.openInputStream(contentUri);
            bitmap = BitmapFactory.decodeStream(stream);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return bitmap;
        }
    }
}
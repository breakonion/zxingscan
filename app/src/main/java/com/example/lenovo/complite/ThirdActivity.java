package com.example.lenovo.complite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xys.libzxing.zxing.encoding.EncodingUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


public class ThirdActivity extends Activity {
    private EditText mInput;
    private ImageView mImg;
    private CheckBox isLogo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mInput= (EditText) findViewById(R.id.editText);
        mImg= (ImageView) findViewById(R.id.imageView);
        isLogo= (CheckBox) findViewById(R.id.checkBox);
        mImg.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
                builder.setItems(new String[]{getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        mImg.setDrawingCacheEnabled(true);
                        Bitmap imageBitmap = mImg.getDrawingCache();
                        if (imageBitmap != null) {
                            new SaveImageTask().execute(imageBitmap);
                        }
                    }
                });
                builder.show();

                return true;
            }
        });

    }

    private class SaveImageTask extends AsyncTask<Bitmap, Void, String> {

        protected String doInBackground(Bitmap... params) {
            String result = getResources().getString(R.string.save_picture_failed);
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();

                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }

                File imageFile = new File(file.getAbsolutePath(),new Date().getTime()+".jpg");
                FileOutputStream outStream = null;
                outStream = new FileOutputStream(imageFile);
                Bitmap image = params[0];
                image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                result = getResources().getString(R.string.save_picture_success,  file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

            mImg.setDrawingCacheEnabled(false);
        }
    }
    public void make(View view){
        String input=mInput.getText().toString().trim();
        //生成二维码，然后为二维码增加logo
        Bitmap bitmap= EncodingUtils.createQRCode(input,500,500,
                isLogo.isChecked()? BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher):null
        );
        mImg.setImageBitmap(bitmap);
        }


}

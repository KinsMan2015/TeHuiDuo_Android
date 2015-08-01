package com.jeegoo.o2o.tehuiduo.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by xintong on 2015/7/16.
 */
public class IntentUtils {
    public static void updateImage(Context context,String file){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.parse(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
    public static Intent cropImageUriIntent(Uri imageUri, int outputX, int outputY) {

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(imageUri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 2);

        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", outputX);

        intent.putExtra("outputY", outputY);

        intent.putExtra("scale", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection

       return intent;

    }
    public static Intent getImageUriFromPhoto(Uri imageUri){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);

        intent.setType("image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 2);

        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 600);

        intent.putExtra("outputY", 300);

        intent.putExtra("scale", true);

        intent.putExtra("return-data", false);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }
}

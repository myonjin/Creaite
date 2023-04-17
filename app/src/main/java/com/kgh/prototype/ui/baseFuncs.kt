package com.kgh.prototype.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Throws(IOException::class)
fun createImageFile(
//    viewModel: MediaViewModel
): File {
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_$timeStamp.jpg"

    // Get the public Pictures directory
    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val appDir = File(storageDir, "SignEz")

    // Create the app directory if it doesn't exist
    if (!appDir.exists()) {
        appDir.mkdirs()
    }

    // Create the image file in the app directory
    val imageFile = File(appDir, imageFileName)
//    viewModel.mCurrentPhotoPath.value = imageFile.absolutePath
    return imageFile
}

fun galleryAddPic(
    context: Context,
//    viewModel: MediaViewModel
) {
    // Get the absolute path of the image file
//    val imagePath = viewModel.mCurrentPhotoPath.value ?: return
    // Insert the image into the MediaStore
//    val values = ContentValues().apply {
//        put(MediaStore.Images.Media.DISPLAY_NAME, "My Image")
//        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//        put(MediaStore.Images.Media.DATA, imagePath)
//    }
//    viewModel.imageUri.value = Uri.parse(imagePath)
//    Toast.makeText(context, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
}

//fun openTutorialActivity(context: Context) {
//    val REQUEST_CODE_TUTORIAL_ACTIVITY = 310
//    val intent = Intent(context, TutorialActivity::class.java)
//    (context as Activity).startActivityForResult(intent, REQUEST_CODE_TUTORIAL_ACTIVITY)
//}

fun openSettingIntent(context: Context){
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:" + "com. signez.signageproblemshooting")
    ContextCompat.startActivity(context, intent, null)
}
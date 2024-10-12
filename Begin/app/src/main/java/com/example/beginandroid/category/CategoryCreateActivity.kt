package com.example.beginandroid.category

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.beginandroid.MainActivity
import com.example.beginandroid.BaseActivity
import com.example.beginandroid.R // Імпорт правильного R
import com.example.beginandroid.services.ApplicationNetwork
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class CategoryCreateActivity : BaseActivity() {
    private var ivSelectImage: ImageView? = null
    private var filePath: String? = null
    private var tlCategoryName: TextInputLayout? = null
    private val TAG = "CategoryCreateActivity"

    private val isStoragePermissionGranted: Boolean
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.v(TAG, "Permission is granted")
                    return true
                } else {
                    Log.v(TAG, "Permission is revoked")
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1
                    )
                    return false
                }
            } else {
                Log.v(TAG, "Permission is granted")
                return true
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_category_create) // Переконайся, що XML існує

        ivSelectImage = findViewById(R.id.ivSelectImage)
        tlCategoryName = findViewById(R.id.tlCategoryName)

        val url = "http://www.websimbadb.somee.com/images/noimage.jpg"

        // Перевірка на null перед використанням ivSelectImage
        ivSelectImage?.let { imageView ->
            Glide
                .with(this)
                .load(url)
                .apply(RequestOptions().override(300))
                .into(imageView)
        }

        isStoragePermissionGranted
    }


    fun openGallery(view: View?) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data

            ivSelectImage?.let { imageView ->
                Glide.with(this)
                    .load(uri)
                    .apply(RequestOptions().override(300))
                    .into(imageView)
            }

            // Якщо хочеш отримати шлях до файлу, викликай цей метод
            filePath = getPathFromURI(uri)
        }
    }


    private fun getPathFromURI(contentUri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri!!, projection, null, null, null)
        cursor?.let {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            return filePath
        }
        return null
    }

    fun onCreateCategory(view: View?) {
        val name = tlCategoryName?.editText?.text.toString().trim()
        val params: MutableMap<String, RequestBody> = HashMap()
        params["name"] = RequestBody.create("text/plain".toMediaTypeOrNull(), name)

        var imagePart: MultipartBody.Part? = null
        if (filePath != null) {
            val imageFile = File(filePath)
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
            imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
        }

        ApplicationNetwork.getInstance()
            .getCategoriesApi()
            .create(params, imagePart)
            .enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.isSuccessful) {
                        val intent = Intent(this@CategoryCreateActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<Void?>, throwable: Throwable) {
                    Log.e(TAG, "Error creating category", throwable)
                }
            })
    }

    companion object {
        private const val PICK_IMAGE = 1
    }
}

package com.m1zyuk1.ixia

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.m1zyuk1.ixia.databinding.ActivityMainBinding
import android.support.v4.content.FileProvider
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.m1zyuk1.ixia.model.Post
import com.m1zyuk1.ixia.model.SerializeUtil
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val RESULT_CAMERA = 1001
    private val REQUEST_PERMISSION = 1002
    private val RESULT_POST = 1003

    private val POST_PREF = "post_pref"
    private val POST_STRING = "post_pref"

    private var cameraUri: Uri? = null
    private var filePath: String? = null
    private lateinit var postList: MutableList<Post>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()
        initializePostdata()
        setupRecyclerView()
    }

    private fun initializePostdata() {
        // load data or create new one
        val data = getSharedPreferences(POST_PREF, Context.MODE_PRIVATE)
        val postsRaw = data.getString(POST_STRING, "")

        postList = if (postsRaw.isEmpty()) {
            // createDummyData()
            mutableListOf()
        } else {
            SerializeUtil.toSchedules(postsRaw)
        }
    }

    private fun setupRecyclerView(){
        val adapter = GridRecycleViewAdapter(postList)
        val gridManager = GridLayoutManager(this,3)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = gridManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(GridDecoration(20))
        binding.recyclerView.clipToPadding = true
    }

    private fun createDummyData(): MutableList<Post> {
        var list: MutableList<Post> = mutableListOf()

        var post = Post("Title", "url", Date(), "Comment")

        list.add(post)
        list.add(post)
        list.add(post)
        list.add(post)
        return list
    }

    private fun setupUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.cameraButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 23) {
                checkPermission()
            } else {
                cameraIntent()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RESULT_CAMERA -> {
                if (cameraUri != null) {
                    if (filePath != null) {
                        registerDatabase(filePath!!)
                    }
                    startActivityForResult(CreatePostActivity.makeIntent(this, cameraUri.toString()), RESULT_POST)
                } else {
                    Log.d("debug", "cameraUri == null")
                }
            }

            RESULT_POST -> {
                if (resultCode == RESULT_OK){
                    var post = data?.getSerializableExtra(CreatePostActivity.RESPONSE_POST) as Post
                    postList.add(0,post)
                    savePosts()
                }
            }
        }
    }

    private fun cameraIntent() {
        // 保存先のフォルダーを作成
        val cameraFolder = File(
                getExternalStoragePublicDirectory(
                        DIRECTORY_PICTURES), "IMG")
        cameraFolder.mkdirs()

        // 保存ファイル名
        val fileName = SimpleDateFormat(
                "ddHHmmss", Locale.US).format(Date())
        filePath = String.format("%s/%s.jpg", cameraFolder.getPath(), fileName)
        Log.d("debug", "filePath:" + filePath)

        // capture画像のファイルパス
        val cameraFile = File(filePath)
        cameraUri = FileProvider.getUriForFile(
                this@MainActivity,
                "com.m1zyuk1.ixia.fileprovider",
                cameraFile)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri)
        startActivityForResult(intent, RESULT_CAMERA)
    }

    private fun registerDatabase(file: String) {
        val contentValues = ContentValues()
        val contentResolver = this.contentResolver
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        contentValues.put("_data", file)
        contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            cameraIntent()
        } else {
            requestPermission()
        }
    }

    // 許可を求める
    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION)

        } else {
            Toast.makeText(this, "Can't work if doesn't permit it", Toast.LENGTH_SHORT).show()

            ActivityCompat.requestPermissions(this,
                    arrayOf(WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION)

        }
    }

    // 結果の受け取り
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {

        Log.d("debug", "onRequestPermissionsResult()")

        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraIntent()

            } else {
                // それでも拒否された時の対応
                Toast.makeText(this, "Doesn't work", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun savePosts() {
        val data = getSharedPreferences(POST_PREF, Context.MODE_PRIVATE)
        val convertedPosts = SerializeUtil.toBase64(postList)
        val editor = data.edit()
        editor.putString(POST_STRING, convertedPosts)
        editor.apply()
    }
}

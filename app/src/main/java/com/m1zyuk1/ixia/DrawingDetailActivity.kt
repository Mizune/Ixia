package com.m1zyuk1.ixia

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.m1zyuk1.ixia.databinding.ActivityCreatePostBinding
import com.m1zyuk1.ixia.databinding.ActivityDrawingDetailBinding
import com.m1zyuk1.ixia.model.Post

class DrawingDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrawingDetailBinding

    companion object MakeIntent {
        val POST_DATA = "post_data"
        fun makeIntent(context: Context, post: Post): Intent {
            var intent = Intent(context, DrawingDetailActivity::class.java)
            intent.putExtra(POST_DATA, post)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawing_detail)
        Toast.makeText(this,(intent.getSerializableExtra(POST_DATA) as Post).title, Toast.LENGTH_SHORT).show()
    }
}

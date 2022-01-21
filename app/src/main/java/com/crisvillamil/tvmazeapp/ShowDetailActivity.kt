package com.crisvillamil.tvmazeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crisvillamil.tvmazeapp.databinding.ActivityMainBinding
import com.crisvillamil.tvmazeapp.databinding.ActivityShowDetailBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class ShowDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)
        val showDetailBinding = ActivityShowDetailBinding.inflate(layoutInflater)
        setContentView(showDetailBinding.root)
        supportPostponeEnterTransition()
        val imageURL = intent.getStringExtra("imageURL")
        Picasso
            .get()
            .load(imageURL)
            .into(showDetailBinding.imageShow, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }

            })
    }

    override fun onDestroy() {
        supportFinishAfterTransition()
        super.onDestroy()
    }
}
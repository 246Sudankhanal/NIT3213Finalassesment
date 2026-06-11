package com.example.nit3213.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nit3213.data.model.Entity
import com.example.nit3213.databinding.ActivityDetailsBinding
import com.google.gson.Gson

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val entityJson = intent.getStringExtra("entity_json")
        val entity = Gson().fromJson(entityJson, Entity::class.java)

        entity?.let {
            binding.tvDeviceName.text = it.deviceName
            binding.tvManufacturer.text = it.manufacturer
            binding.tvOS.text = it.operatingSystem
            binding.tvReleaseYear.text = it.releaseYear.toString()
            binding.tvDescription.text = it.description
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}

package com.example.nit3213.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nit3213.databinding.ActivityDashboardBinding
import com.example.nit3213.ui.details.DetailsActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: EntityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val keypass = intent.getStringExtra("keypass") ?: ""

        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.dashboardState.collect { state ->
                binding.progressBar.isVisible = state is DashboardState.Loading
                
                when (state) {
                    is DashboardState.Success -> {
                        adapter.submitList(state.entities)
                    }
                    is DashboardState.Error -> {
                        Toast.makeText(this@DashboardActivity, state.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }

        viewModel.fetchDashboard(keypass)
    }

    private fun setupRecyclerView() {
        adapter = EntityAdapter { entity ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("entity_json", Gson().toJson(entity))
            }
            startActivity(intent)
        }
        binding.rvEntities.layoutManager = LinearLayoutManager(this)
        binding.rvEntities.adapter = adapter
    }
}

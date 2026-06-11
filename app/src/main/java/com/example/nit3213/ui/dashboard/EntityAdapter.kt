package com.example.nit3213.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213.data.model.Entity
import com.example.nit3213.databinding.ItemEntityBinding

class EntityAdapter(
    private val onEntityClick: (Entity) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    private var entities: List<Entity> = emptyList()

    fun submitList(list: List<Entity>) {
        entities = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemEntityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(entities[position])
    }

    override fun getItemCount(): Int = entities.size

    inner class EntityViewHolder(private val binding: ItemEntityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entity: Entity) {
            binding.tvDeviceName.text = entity.deviceName
            binding.tvManufacturer.text = "Manufacturer: ${entity.manufacturer}"
            binding.tvOS.text = "OS: ${entity.operatingSystem}"
            binding.root.setOnClickListener { onEntityClick(entity) }
        }
    }
}

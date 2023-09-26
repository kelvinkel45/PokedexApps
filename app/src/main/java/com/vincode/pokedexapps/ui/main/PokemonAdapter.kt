package com.vincode.pokedexapps.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vincode.pokedexapps.data.local.entity.PokemonEntity
import com.vincode.pokedexapps.databinding.ItemDataBinding

class PokemonAdapter(private val onClick: (PokemonEntity, ItemDataBinding) -> Unit) :
    ListAdapter<PokemonEntity, PokemonAdapter.PokemonViewHolder>(DIFF_CALLBACK), Filterable {

    private var listData = ArrayList<PokemonEntity>()
    fun setList(list: List<PokemonEntity>) {
        this.listData.clear()
        this.listData.addAll(list)
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PokemonViewHolder(
        private val binding: ItemDataBinding,
        private val onClick : (PokemonEntity, ItemDataBinding) -> Unit): RecyclerView.ViewHolder(binding.root){

        private var currentItem : PokemonEntity? = null

        init {
            binding.root.setOnClickListener {
                currentItem?.let { data -> onClick(data, binding) }
            }
        }

        fun bind(pokemonEntity: PokemonEntity){
            currentItem = pokemonEntity
            binding.tvName.text = pokemonEntity.name
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PokemonEntity> =
            object : DiffUtil.ItemCallback<PokemonEntity>() {
                override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
                    return oldItem.name == newItem.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<PokemonEntity>()
                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(listData)
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()
                    listData.forEach { data ->
                        if (data.name.lowercase().contains(filterPattern)) {
                            filteredList.add(data)
                        }
                    }
                }
                val result = FilterResults()
                result.values = filteredList
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filteredList = results?.values as? List<PokemonEntity> ?: return
                submitList(filteredList)
            }

        }
    }

}
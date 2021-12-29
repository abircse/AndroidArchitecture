package com.coxtunes.androidarchitecturekotlin2021.ui.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coxtunes.androidarchitecturekotlin2021.domain.viewobjects.UsersViewItems
import com.coxtunes.androidarchitecturekotlin2021.databinding.ItemUserBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    fun submitCatFactList(facts: List<UsersViewItems>) = differ.submitList(facts)


    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(users: UsersViewItems) {
            binding.user = users
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<UsersViewItems>() {
        override fun areItemsTheSame(
            oldItem: UsersViewItems,
            newItem: UsersViewItems
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: UsersViewItems,
            newItem: UsersViewItems
        ): Boolean {
            return oldItem.phone == newItem.phone && oldItem.website == newItem.website
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItemCatBinding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return UserViewHolder(listItemCatBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = differ.currentList.size
}
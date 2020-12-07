package ru.subbotind.android.academy.myfirstapp.ui.movielist.adapter

import androidx.recyclerview.widget.DiffUtil

class MovieDiffUtilCallback : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
        oldItem == newItem
}
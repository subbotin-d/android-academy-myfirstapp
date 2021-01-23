package ru.subbotind.android.academy.myfirstapp.ui.moviedetails.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.subbotind.android.academy.myfirstapp.domain.entity.Actor

class ActorDiffUtilCallback : DiffUtil.ItemCallback<Actor>() {

    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean =
        oldItem == newItem
}
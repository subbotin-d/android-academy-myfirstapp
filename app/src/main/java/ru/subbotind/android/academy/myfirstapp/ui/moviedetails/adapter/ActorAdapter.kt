package ru.subbotind.android.academy.myfirstapp.ui.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.data.Actor
import ru.subbotind.android.academy.myfirstapp.databinding.ActorItemBinding

class ActorAdapter : ListAdapter<Actor, ActorViewHolder>(ActorDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder =
        ActorViewHolder.from(parent)

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ActorViewHolder private constructor(private val binding: ActorItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): ActorViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ActorItemBinding.inflate(inflater, parent, false)
            return ActorViewHolder(binding)
        }
    }

    fun bind(actor: Actor) {
        binding.apply {
            actor.avatar?.let { avatarImage ->
                actorImage.setImageResource(avatarImage)
            } ?: actorImage.setImageResource(R.drawable.ic_unknown_actor_avatar)

            actorText.text = actor.fullName
        }
    }
}
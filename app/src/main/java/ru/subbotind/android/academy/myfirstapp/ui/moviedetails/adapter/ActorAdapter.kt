package ru.subbotind.android.academy.myfirstapp.ui.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.databinding.ActorItemBinding
import ru.subbotind.android.academy.myfirstapp.domain.entity.Actor

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
            Glide.with(itemView.context)
                .load(actor.picture)
                .placeholder(R.drawable.ic_image_download_placeholder)
                .error(R.drawable.ic_unknown_actor_avatar)
                .into(actorImage)

            actorText.text = actor.name
        }
    }
}
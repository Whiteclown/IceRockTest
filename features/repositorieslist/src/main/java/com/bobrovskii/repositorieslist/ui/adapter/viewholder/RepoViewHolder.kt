package com.bobrovskii.repositorieslist.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bobrovskii.repositorieslist.databinding.ItemRepoBinding
import com.bobrovskii.session.domain.entity.Repo

class RepoViewHolder(
	private val binding: ItemRepoBinding,
) : RecyclerView.ViewHolder(binding.root) {

	fun bind(
		item: Repo,
		onItemClicked: (Repo) -> Unit
	) {
		with(binding) {
			//Set data and listeners
			repoTitle.text = item.name
			language.text = item.language
			description.text = item.description

			layout.setOnClickListener { onItemClicked(item) }
		}
	}

	companion object {

		fun from(parent: ViewGroup): RepoViewHolder {
			val layoutInflater = LayoutInflater.from(parent.context)
			val binding = ItemRepoBinding.inflate(layoutInflater, parent, false)
			return RepoViewHolder(binding)
		}
	}
}
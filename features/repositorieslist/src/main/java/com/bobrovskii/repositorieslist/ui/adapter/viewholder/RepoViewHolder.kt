package com.bobrovskii.repositorieslist.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
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
			repoTitle.text = item.name
			language.text = item.language
			if (item.description.isNullOrBlank()) {
				description.visibility = View.GONE
			} else {
				description.text = item.description
			}

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
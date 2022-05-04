package com.bobrovskii.repositorieslist.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bobrovskii.repositorieslist.databinding.ItemRepoBinding
import com.bobrovskii.session.domain.entity.Repo

object Languages {

	const val KOTLIN = "Kotlin"
	const val JAVA = "Java"
	const val JAVASCRIPT = "JavaScript"
}

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
			when (item.language) {
				Languages.KOTLIN     -> language.setTextColor(itemView.context.resources.getColor(com.bobrovskii.core.R.color.purple))

				Languages.JAVA       -> language.setTextColor(itemView.context.resources.getColor(com.bobrovskii.core.R.color.orange))

				Languages.JAVASCRIPT -> language.setTextColor(itemView.context.resources.getColor(com.bobrovskii.core.R.color.yellow))

				else                 -> {}
			}

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
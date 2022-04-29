package com.bobrovskii.repositorieslist.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bobrovskii.repositorieslist.ui.adapter.viewholder.RepoViewHolder
import com.bobrovskii.session.domain.entity.Repo

class RepoAdapter(
	private val repos: List<Repo>,
	private val onItemClick: (Repo) -> Unit,
) : ListAdapter<Repo, RepoViewHolder>(RepoDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
		RepoViewHolder.from(parent)

	override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
		holder.bind(repos[position], onItemClick)
	}

	override fun getItemCount(): Int = repos.size
}

private class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {

	override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.name == newItem.name

	override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
}
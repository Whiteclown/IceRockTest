package com.bobrovskii.repositorieslist.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bobrovskii.repositorieslist.ui.adapter.viewholder.RepoViewHolder
import com.bobrovskii.session.domain.entity.Repo

class RepoAdapter(
	private val onItemClick: (Repo) -> Unit,
) : RecyclerView.Adapter<RepoViewHolder>() {

	var repos: List<Repo>? = null
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
		RepoViewHolder.from(parent)

	override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
		repos?.let {
			holder.bind(it[position], onItemClick)
		}
	}

	override fun getItemCount(): Int = repos?.size ?: 0
}
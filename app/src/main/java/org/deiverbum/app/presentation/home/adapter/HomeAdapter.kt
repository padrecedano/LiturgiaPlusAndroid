package org.deiverbum.app.presentation.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.deiverbum.app.domain.model.Bible
import org.deiverbum.app.databinding.LayoutPrayBinding


class HomeAdapter : RecyclerView.Adapter<HomeAdapter.PrayViewHolder>() {

    private val bibles: ArrayList<Bible> = ArrayList()
    private lateinit var context: Context

    class PrayViewHolder(binding: LayoutPrayBinding) : RecyclerView.ViewHolder(binding.root) {

        val name = binding.prayNameTv
        val time = binding.prayTimeTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayViewHolder {
        context = parent.context
        val viewBinding = LayoutPrayBinding.inflate(LayoutInflater.from(parent.context))
        return PrayViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: PrayViewHolder, position: Int) {
        bibles[position].also {
            holder.name.text = it.name
            holder.time.text = it.liturgyID.toString()//.getTimeFormated()
        }
    }

    override fun getItemCount(): Int = bibles.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(newBibles: List<Bible>) {
        bibles.run {
            clear()
            addAll(newBibles)
            notifyDataSetChanged()
        }
    }
}
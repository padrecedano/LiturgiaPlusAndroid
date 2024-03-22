package org.deiverbum.app.core.presentation.sacramentos.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import org.deiverbum.app.R
import org.deiverbum.app.databinding.ItemRowChildBinding


class SacramentosChildAdapter(private val itemsList: List<SacramentoItem>) :
    RecyclerView.Adapter<SacramentosChildAdapter.SacramentosChildHolder>() {
    private var membersList: List<SacramentoItem> = ArrayList()

    init {
        this.membersList = itemsList

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SacramentosChildHolder {
        val itemBinding =
            ItemRowChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SacramentosChildHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SacramentosChildHolder, position: Int) {
        val sacramentoItem: SacramentoItem = itemsList[position]
        holder.bind(sacramentoItem)
    }

    override fun getItemCount(): Int = itemsList.size

    inner class SacramentosChildHolder(private val itemBinding: ItemRowChildBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.root.setOnClickListener {
                val item = membersList[bindingAdapterPosition]
                val bundle = Bundle()
                bundle.putString("title", item.title)
                bundle.putString("subTitle", item.subTitle)
                bundle.putString("rawPath", item.rawPath)
                Navigation.findNavController(it!!).navigate(R.id.nav_sacramentum, bundle)
            }
        }


        fun bind(paymentBean: SacramentoItem) {
            itemBinding.name.text = paymentBean.subTitle
        }
    }
}

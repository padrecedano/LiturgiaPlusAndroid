package org.deiverbum.app.core.presentation.sacramentos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.deiverbum.app.databinding.ItemRowParentBinding

/**
 *
 *
 * Esta clase maneja el adaptador de la pantalla `Sacramentos`,
 * presentada desde `SacramentosFragment`.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
class SacramentosParentAdapter(private val parentList: List<SacramentosParent>) :
    RecyclerView.Adapter<SacramentosParentAdapter.SacramentosParentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SacramentosParentHolder {
        val itemBinding =
            ItemRowParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SacramentosParentHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SacramentosParentHolder, position: Int) {
        val sacramentosParent: SacramentosParent = parentList[position]
        holder.bind(sacramentosParent)
    }

    override fun getItemCount(): Int = parentList.size

    class SacramentosParentHolder(private val itemBinding: ItemRowParentBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @Composable
        fun ListItem(data: SacramentosParent, modifier: Modifier = Modifier) {
            Row(modifier.fillMaxWidth()) {
                Text(text = data.name)
                // … other composables required for displaying `data`
            }
        }

        fun bind(sacramentosParent: SacramentosParent) {
            itemBinding.contentTitle.text = sacramentosParent.name
            val childAdapter = SacramentosChildAdapter(sacramentosParent.members)
            itemBinding.childRecyclerView.layoutManager = GridLayoutManager(itemView.context, 1)
            itemBinding.childRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    itemView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            itemBinding.childRecyclerView.adapter = childAdapter
        }
    }
}

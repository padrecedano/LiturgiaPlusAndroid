package org.deiverbum.app.core.presentation.sacramentos.adapter

import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView
import com.google.accompanist.themeadapter.material.MdcTheme

class MyComposeAdapter : RecyclerView.Adapter<MyComposeViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyComposeViewHolder {
        return MyComposeViewHolder(ComposeView(parent.context))
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyComposeViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onViewRecycled(holder: MyComposeViewHolder) {
        // This is from the previous guidance
        // NOTE: You **do not** want to do this with Compose 1.2.0-beta02+
        // and RecyclerView 1.3.0-alpha02+
        holder.composeView.disposeComposition()
    }

     fun onBindViewHolderr(holder: RecyclerView.ViewHolder, position: Int) {
        /*val state = rowStates.getOrPut(position) { LazyListState() }
        holder.itemRow.index = position
        holder.itemRow.rowState = state*/
    }

    /* Other methods */
}

class MyComposeViewHolder(
    val composeView: ComposeView
) : RecyclerView.ViewHolder(composeView) {

    init {
        // This is from the previous guidance
        // NOTE: **Do not** do this with Compose 1.2.0-beta02+
        // and RecyclerView 1.3.0-alpha02+
        composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
    }

    fun bind(input: String) {
        composeView.setContent {
            MdcTheme {
                Text(input)
            }
        }
    }
}
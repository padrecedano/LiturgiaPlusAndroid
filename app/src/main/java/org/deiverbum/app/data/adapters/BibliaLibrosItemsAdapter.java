package org.deiverbum.app.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;
import org.deiverbum.app.model.BibliaLibros;

import java.util.List;


public class BibliaLibrosItemsAdapter extends RecyclerView.Adapter<BibliaLibrosItemsAdapter.MyViewHolder> {

    private List<BibliaLibros> mainList;
    private BibliaLibrosItemsAdapter.ItemListener mListener;

    public BibliaLibrosItemsAdapter(List<BibliaLibros> mainList, BibliaLibrosItemsAdapter.ItemListener itemListener) {
        this.mainList = mainList;
        mListener = itemListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_rosario, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        BibliaLibros album = mainList.get(position);
        holder.title.setText(album.getName());
        holder.description.setText(album.getDescription());

        //holder.thumbnail.setImageResource(album.getThumbnail());
        holder.setData(mainList.get(position));
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public interface ItemListener {
        void onItemClick(BibliaLibros item);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, description;
        BibliaLibros item;
        RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = view.findViewById(R.id.tv_title);
            description = view.findViewById(R.id.tv_description);

            relativeLayout = view.findViewById(R.id.mainCardRelativeLayout);

        }

        public void setData(BibliaLibros item) {
            this.item = item;
            //relativeLayout.setBackgroundColor(item.getColor());
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
}
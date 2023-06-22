package com.example.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterLista extends RecyclerView.Adapter<AdapterLista.RecyclerTesteViewHolder> {

    public static ClickRecyclerView_Interface clickRecyclerViewInterface;
    Context mctx;
    private List<Pessoa> mList;

    public AdapterLista(Context applicationContext, List<Pessoa> pessoasListas) {
        this.mctx = applicationContext;
        this.mList = pessoasListas;
    }

    @NonNull
    @Override
    public RecyclerTesteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlist_itemdalista, viewGroup, false);
        return new RecyclerTesteViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerTesteViewHolder holder, int position) {
        Pessoa pessoa = mList.get(position);

        holder.viewNome.setText(pessoa.getName().toString());
        holder.image.setImageBitmap(pessoa.getImagem());

    }

    @Override
    public int getItemCount() {

        return mList.size();
    }


    protected class RecyclerTesteViewHolder extends RecyclerView.ViewHolder {

        protected TextView viewNome;
        protected ImageView image;

        public RecyclerTesteViewHolder(final View itemView) {
            super(itemView);

            viewNome = (TextView) itemView.findViewById(R.id.text);
            image = (ImageView) itemView.findViewById(R.id.image);

        }
    }
}
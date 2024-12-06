package com.example.gremioapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private List<EventoClass> eventoList;

    public EventoAdapter(List<EventoClass> eventoList) {
        this.eventoList = eventoList;
    }

    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventoViewHolder holder, int position) {
        EventoClass eventoClass = eventoList.get(position);

        holder.txtUser.setText(eventoClass.getUser());
        holder.txtTitulo.setText(eventoClass.getTitulo());
        holder.txtDescricao.setText(eventoClass.getDescricao());
        holder.txtLocal.setText(eventoClass.getLocal());
        holder.txtLocalDateTime.setText(eventoClass.getLocalDateTime());

        if (eventoClass.getImagem() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(eventoClass.getImagem(), 0, eventoClass.getImagem().length);
            holder.imgEvento.setImageBitmap(bitmap);
        } else {
            holder.imgEvento.setImageResource(R.drawable.baseline_home_24);
        }
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo, txtDescricao, txtLocal, txtUser, txtLocalDateTime;
        ImageView imgEvento;

        public EventoViewHolder(View itemView) {
            super(itemView);

            txtUser = itemView.findViewById(R.id.usuario);
            txtLocal = itemView.findViewById(R.id.local);
            imgEvento = itemView.findViewById(R.id.imagem);
            txtTitulo = itemView.findViewById(R.id.titulo);
            txtDescricao = itemView.findViewById(R.id.descricao);
            txtLocalDateTime = itemView.findViewById(R.id.localDateTime);
        }
    }

}

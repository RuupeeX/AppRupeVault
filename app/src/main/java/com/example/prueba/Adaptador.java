package com.example.prueba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public abstract class Adaptador<T> extends BaseAdapter {
    private ArrayList<T> entradas;
    private int R_layout_IdView;
    private Context contexto;

    public Adaptador(Context contexto, int R_layout_IdView, ArrayList<T> entradas) {
        super();
        this.contexto = contexto;
        this.entradas = entradas;
        this.R_layout_IdView = R_layout_IdView;
    }

    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public T getItem(int position) {
        return entradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }

        onEntrada(getItem(position), view);
        return view;
    }

    public abstract void onEntrada(T entrada, View view);
}
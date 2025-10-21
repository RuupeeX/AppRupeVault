package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridCategorias = findViewById(R.id.gridCategorias);

        // Datos para las categorías con descripciones
        ArrayList<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria("SNEAKERS", "Latest sneaker releases and collections", R.drawable.snknews));
        categorias.add(new Categoria("CLOTHES", "Streetwear and fashion apparel", R.drawable.clothes_category));
        categorias.add(new Categoria("ACCESSORIES", "Caps, hats and fashion accessories", R.drawable.accesories_category));
        categorias.add(new Categoria("COLLECTABLE", "Figures, replicas, and collectible items", R.drawable.collectables_category));

        // Adaptador para las categorías
        gridCategorias.setAdapter(new Adaptador<Categoria>(this, R.layout.item_categoria, categorias) {
            @Override
            public void onEntrada(Categoria entrada, View view) {
                if (entrada != null) {
                    TextView textoCategoria = view.findViewById(R.id.textoCategoria);
                    TextView descripcionCategoria = view.findViewById(R.id.descripcionCategoria);
                    ImageView iconoCategoria = view.findViewById(R.id.iconoCategoria);

                    textoCategoria.setText(entrada.getNombre());
                    descripcionCategoria.setText(entrada.getDescripcion());
                    iconoCategoria.setImageResource(entrada.getIcono());
                }
            }
        });

        // Click listener para las categorías
        gridCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoriaSeleccionada = categorias.get(position);

                Intent intent = new Intent(MainActivity.this, ProductosActivity.class);
                intent.putExtra("categoria", categoriaSeleccionada.getNombre());
                startActivity(intent);
            }
        });
    }
}
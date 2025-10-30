package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridCategorias;
    private LinearLayout homeButton, userButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar componentes
        gridCategorias = findViewById(R.id.gridCategorias);
        homeButton = findViewById(R.id.homeButton);
        userButton = findViewById(R.id.userButton);

        setupBottomNavigation();

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

    private void setupBottomNavigation() {
        // Home Button - Ya estamos en home, puede servir para recargar
        LinearLayout homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            refreshHome();
        });

        // Search Button (segundo botón)
        LinearLayout searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> {
            showSearchFunctionality();
        });

        // Like Button (tercer botón)
        LinearLayout likeButton = findViewById(R.id.likeButton);
        likeButton.setOnClickListener(v -> {
            showFavorites();
        });

        // Notifications Button (cuarto botón)
        LinearLayout notificationsButton = findViewById(R.id.notificationsButton);
        notificationsButton.setOnClickListener(v -> {
            showNotifications();
        });

        // User Button (quinto botón)
        LinearLayout userButton = findViewById(R.id.userButton);
        userButton.setOnClickListener(v -> {
            navigateToUserProfile();
        });

        // Resaltar el botón de home al iniciar
        highlightActiveButton(R.id.homeButton);
    }

    private void refreshHome() {
        // Recargar las categorías o datos principales
        Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show();

        // Si quieres recargar datos:
        // setupCategories();
    }

    private void showSearchFunctionality() {
        Toast.makeText(this, "Búsqueda", Toast.LENGTH_SHORT).show();

        // Puedes implementar:
        // - Un diálogo de búsqueda
        // - Navegar a una pantalla de búsqueda
        // - Expandir una barra de búsqueda en el header
    }

    private void showFavorites() {
        Toast.makeText(this, "Favoritos", Toast.LENGTH_SHORT).show();

        // Navegar a pantalla de favoritos o mostrar diálogo
        // Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
        // startActivity(intent);
    }

    private void showNotifications() {
        Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show();

        // Navegar a pantalla de notificaciones o mostrar diálogo
        // Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
        // startActivity(intent);
    }

    private void navigateToUserProfile() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    // Método para resaltar el botón activo
    private void highlightActiveButton(int activeButtonId) {
        int[] buttonIds = {
                R.id.homeButton,
                findViewById(R.id.searchButton) != null ? R.id.searchButton : -1,
                findViewById(R.id.likeButton) != null ? R.id.likeButton : -1,
                findViewById(R.id.notificationsButton) != null ? R.id.notificationsButton : -1,
                R.id.userButton
        };

        for (int id : buttonIds) {
            if (id == -1) continue;

            LinearLayout button = findViewById(id);
            if (button != null) {
                // Buscar el ImageView dentro del LinearLayout
                ImageView icon = null;
                for (int i = 0; i < button.getChildCount(); i++) {
                    View child = button.getChildAt(i);
                    if (child instanceof ImageView) {
                        icon = (ImageView) child;
                        break;
                    }
                }

                if (icon != null) {
                    if (id == activeButtonId) {
                        // Botón activo - color púrpura
                        icon.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_purple));
                    } else {
                        // Botones inactivos - color blanco
                        icon.setColorFilter(ContextCompat.getColor(this, android.R.color.white));
                    }
                }
            }
        }
    }
}
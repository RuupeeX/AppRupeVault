package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class ProductosActivity extends AppCompatActivity {

    private ListView lista;
    private GridView grid;
    private ImageView  iconoBuscar, iconoMenu, iconoVolver;
    private LinearLayout layoutBuscar;
    private EditText editTextBuscar;
    private Button btnCancelarBuscar;
    private ArrayList<items> datos;
    private ArrayList<items> datosOriginales;
    private boolean isGridView = false;
    private boolean isSearching = false;
    private String categoriaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        // Obtener la categoría seleccionada
        Intent intent = getIntent();
        categoriaActual = intent.getStringExtra("categoria");

        // Enlazar vistas
        lista = findViewById(R.id.listView);
        grid = findViewById(R.id.gridView);
        iconoBuscar = findViewById(R.id.iconoBuscar);
        iconoMenu = findViewById(R.id.iconoMenu);
        iconoVolver = findViewById(R.id.iconoVolver);
        layoutBuscar = findViewById(R.id.layoutBuscar);
        editTextBuscar = findViewById(R.id.editTextBuscar);

        // Configurar título según categoría
        TextView tituloCategoria = findViewById(R.id.tituloCategoria);
        tituloCategoria.setText(categoriaActual);

        // Configurar la barra de navegación inferior
        setupBottomNavigation();

        // Configurar listeners existentes
        iconoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleView();
            }
        });

        iconoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSearch();
            }
        });

        iconoVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Volver a la actividad principal
            }
        });

        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterItems(s.toString());
            }
        });

        // Inicializar datos según la categoría
        initializeData();

        // Configurar adaptadores
        if (isGridView) {
            setupGridView();
            lista.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
        } else {
            setupListView();
            grid.setVisibility(View.GONE);
            lista.setVisibility(View.VISIBLE);
        }
    }

    private void setupBottomNavigation() {
        // Home Button - Volver al MainActivity
        LinearLayout homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            navigateToHome();
        });

        // Search Button - Activar búsqueda en esta pantalla
        LinearLayout searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> {
            // Activar la funcionalidad de búsqueda existente
            if (!isSearching) {
                toggleSearch();
            }
        });

        // Like Button - Favoritos
        LinearLayout likeButton = findViewById(R.id.likeButton);
        likeButton.setOnClickListener(v -> {
            showFavorites();
        });

        // Notifications Button - Notificaciones
        LinearLayout notificationsButton = findViewById(R.id.notificationsButton);
        notificationsButton.setOnClickListener(v -> {
            showNotifications();
        });

        // User Button - Perfil de usuario
        LinearLayout userButton = findViewById(R.id.userButton);
        userButton.setOnClickListener(v -> {
            navigateToUserProfile();
        });

        // Resaltar el botón de búsqueda cuando estás en productos
        highlightActiveButton(R.id.searchButton);
    }

    private void navigateToHome() {
        Intent intent = new Intent(ProductosActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void showFavorites() {
        Toast.makeText(this, "Favoritos", Toast.LENGTH_SHORT).show();
        // Intent intent = new Intent(ProductosActivity.this, FavoritesActivity.class);
        // startActivity(intent);
    }

    private void showNotifications() {
        Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show();
        // Intent intent = new Intent(ProductosActivity.this, NotificationsActivity.class);
        // startActivity(intent);
    }

    private void navigateToUserProfile() {
        Intent intent = new Intent(ProductosActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    // Método para resaltar el botón activo
    private void highlightActiveButton(int activeButtonId) {
        int[] buttonIds = {
                R.id.homeButton,
                R.id.searchButton,
                R.id.likeButton,
                R.id.notificationsButton,
                R.id.userButton
        };

        for (int id : buttonIds) {
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
                        icon.setColorFilter(ContextCompat.getColor(this, R.color.purple));
                    } else {
                        // Botones inactivos - color blanco
                        icon.setColorFilter(ContextCompat.getColor(this, android.R.color.white));
                    }
                }
            }
        }
    }

    private void initializeData() {
        datos = new ArrayList<items>();

        if ("SNEAKERS".equals(categoriaActual)) {
            // Datos de sneakers - usar el constructor correcto
            datos.add(new items(R.drawable.j1, "Jordan 1 Retro High", "1000€", false, "SNEAKERS",
                    "The Jordan 1 Retro High is an icon of basketball and urban culture. It features a classic design with premium leather and Air-Sole cushioning."));
            datos.add(new items(R.drawable.airmax1patta, "Air Max 1 Patta 20th", "100€", false, "SNEAKERS",
                    "Special collaborative edition between Nike and Patta celebrating the 20th anniversary. Characterized by earthy tones, suede details, and reflective elements."));
            datos.add(new items(R.drawable.j4red, "Jordan 4 Red Cement", "125€", false, "SNEAKERS",
                    "The Air Jordan 4 ‘Red Cement’ showcases a bold fire-red color combined with the iconic cement print, featuring the classic mesh panels on the sides."));
            datos.add(new items(R.drawable.tnoff, "Air VaporMax Off-White", "480€", false, "SNEAKERS",
                    "Exclusive collaboration between Nike and Virgil Abloh’s Off-White. The VaporMax includes deconstructed details characteristic of the brand."));
            datos.add(new items(R.drawable.airforce1, "Air Force 1 Off-White ICA", "725€", false, "SNEAKERS",
                    "The Off-White Museum Edition deconstructs the classic Air Force 1, featuring exposed details, visible stitching, and multiple material layers."));
            datos.add(new items(R.drawable.yeezy350beluga, "Yeezy 350 V2 Beluga", "235€", false, "SNEAKERS",
                    "The original Beluga model that started the Yeezy revolution. Breathable Primeknit in dark grey tones with the iconic orange side stripe."));
            datos.add(new items(R.drawable.j11, "Air Jordan 11 Bred", "225€", false, "SNEAKERS",
                    "The Jordan 11 ‘Bred’ combines shiny black patent leather with red accents. Features a translucent rubber outsole and a carbon-fiber midsole plate."));
            datos.add(new items(R.drawable.j1travisolive, "Jordan 1 Low Travis Olive", "335€", false, "SNEAKERS",
                    "Collaboration between Travis Scott and Jordan Brand in olive and black tones. Known for its reversed Swoosh and premium suede details."));

        } else if ("CLOTHES".equals(categoriaActual)) {
            // Datos de ropa
            datos.add(new items(R.drawable.boxlogo1, "Hoodie Supreme BoxLogo", "355€", false, "CLOTHES",
                    "The iconic Supreme hoodie featuring the famous embroidered Box Logo on the chest. Made from high-quality French Terry cotton."));
            datos.add(new items(R.drawable.denimtearsgrey, "Hoodie Denim Tears", "365€", false, "CLOTHES",
                    "Conceptual design by Tremaine Emory exploring African American history and culture. Features the signature cotton wreath graphic on the front."));
            datos.add(new items(R.drawable.boxersupreme, "Boxer Supreme Hanes", "95€", false, "CLOTHES",
                    "Pack of 3 boxers in collaboration with Hanes, combining Hanes' classic comfort with Supreme branding."));
            datos.add(new items(R.drawable.feargodpant, "SweetPants Fear of God", "1000€", false, "CLOTHES",
                    "Luxury sweatpants from Fear of God’s Essentials collection. Loose fit with an elastic waistband and drawstring."));
            datos.add(new items(R.drawable.nupsetblack, "North Face 1996 Jacket", "275€", false, "CLOTHES",
                    "The iconic 1996 Nuptse jacket returns with its original design. Filled with 700-fill down for maximum warmth."));
            datos.add(new items(R.drawable.socceroffwhite, "Soccer Jersey OFF-WHITE", "260€", false, "CLOTHES",
                    "Off-White soccer jersey featuring signature diagonal patterns and conceptual design elements inspired by streetwear aesthetics."));
            datos.add(new items(R.drawable.yeezygap, "Yeezy Gap Hoodie", "410€", false, "CLOTHES",
                    "The Yeezy Gap hoodie features a minimalist oversized design, soft heavyweight cotton, and a clean silhouette characteristic of Kanye West’s aesthetic."));

        } else if ("ACCESSORIES".equals(categoriaActual)) {
            // Datos de accesorios
            datos.add(new items(R.drawable.rolexdaytona, "Rolex Daytona Two Tone", "14000€", false, "ACCESSORIES",
                    "Rolex Cosmograph Daytona chronograph in Oystersteel and 18k yellow gold. Black dial with contrasting counters."));
            datos.add(new items(R.drawable.bolsolv, "LV Speedy 30", "1650€", false, "ACCESSORIES",
                    "The classic Louis Vuitton Speedy 30 in Monogram canvas. Cylindrical design with leather handles, padlock closure, and key."));
            datos.add(new items(R.drawable.fiftynine, "New Era Cap MLB", "25€", false, "ACCESSORIES",
                    "New Era 59FIFTY MLB cap. Structured fit, stiff visor, and snap closure."));
            datos.add(new items(R.drawable.supremeband, "Supreme Band Bezel", "105€", false, "ACCESSORIES",
                    "Supreme silicone bracelet with the brand’s print across the entire piece. Comfortable and elastic fit."));
            datos.add(new items(R.drawable.carteraoffwhite, "Off-White Wallet", "280€", false, "ACCESSORIES",
                    "Off-White leather wallet featuring minimalist branding and a functional, compact design."));
        }

        // Guardar copia de los datos originales
        datosOriginales = new ArrayList<>(datos);
    }

    private void toggleSearch() {
        isSearching = !isSearching;
        if (isSearching) {
            layoutBuscar.setVisibility(View.VISIBLE);
            editTextBuscar.requestFocus();
            // Resaltar el botón de búsqueda cuando está activo
            highlightActiveButton(R.id.searchButton);
        } else {
            cancelSearch();
            // Quitar resaltado cuando se cancela la búsqueda
            highlightActiveButton(-1);
        }
    }

    private void cancelSearch() {
        layoutBuscar.setVisibility(View.GONE);
        editTextBuscar.setText("");
        isSearching = false;
        datos.clear();
        datos.addAll(datosOriginales);
        updateAdapters();
    }

    private void filterItems(String searchText) {
        datos.clear();
        if (searchText.isEmpty()) {
            datos.addAll(datosOriginales);
        } else {
            String lowerCaseQuery = searchText.toLowerCase();
            for (items item : datosOriginales) {
                if (item.get_textoTitulo().toLowerCase().contains(lowerCaseQuery)) {
                    datos.add(item);
                }
            }
        }
        updateAdapters();
    }

    private void updateAdapters() {
        if (lista.getAdapter() != null) {
            ((Adaptador) lista.getAdapter()).notifyDataSetChanged();
        }
        if (grid.getAdapter() != null) {
            ((Adaptador) grid.getAdapter()).notifyDataSetChanged();
        }
    }

    private void toggleView() {
        isGridView = !isGridView;
        if (isGridView) {
            lista.setVisibility(View.GONE);
            grid.setVisibility(View.VISIBLE);
            // Asegurar que el GridView tenga el adaptador
            if (grid.getAdapter() == null) {
                setupGridView();
            }
        } else {
            grid.setVisibility(View.GONE);
            lista.setVisibility(View.VISIBLE);
            // Asegurar que el ListView tenga el adaptador
            if (lista.getAdapter() == null) {
                setupListView();
            }
        }
    }

    private void abrirDetalleProducto(items producto) {
        Intent intent = new Intent(ProductosActivity.this, DetalleProductoActivity.class);
        intent.putExtra("titulo", producto.get_textoTitulo());
        intent.putExtra("precio", producto.get_textoContenido());
        intent.putExtra("imagen", producto.get_idImagen());
        intent.putExtra("descripcion", producto.getDescripcion());
        startActivity(intent);
    }

    private void setupListView() {
        lista.setAdapter(new Adaptador<items>(this, R.layout.entrada, datos) {
            @Override
            public void onEntrada(items entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = view.findViewById(R.id.texto_titulo);
                    TextView texto_inferior_entrada = view.findViewById(R.id.texto_datos);
                    ImageView imagen_entrada = view.findViewById(R.id.imagen);

                    if (texto_superior_entrada != null) {
                        texto_superior_entrada.setText(entrada.get_textoTitulo());
                    }
                    if (texto_inferior_entrada != null) {
                        texto_inferior_entrada.setText(entrada.get_textoContenido());
                    }
                    if (imagen_entrada != null) {
                        imagen_entrada.setImageResource(entrada.get_idImagen());
                    }
                }
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < datos.size()) {
                    items product = datos.get(position);
                    abrirDetalleProducto(product);
                }
            }
        });
    }

    private void setupGridView() {
        grid.setAdapter(new Adaptador<items>(this, R.layout.entrada_grid, datos) {
            @Override
            public void onEntrada(items entrada, View view) {
                if (entrada != null) {
                    TextView texto_titulo = view.findViewById(R.id.texto_titulo_grid);
                    TextView texto_datos = view.findViewById(R.id.texto_datos_grid);
                    ImageView imagen = view.findViewById(R.id.imagen_grid);

                    if (texto_titulo != null) {
                        texto_titulo.setText(entrada.get_textoTitulo());
                    }
                    if (texto_datos != null) {
                        texto_datos.setText(entrada.get_textoContenido());
                    }
                    if (imagen != null) {
                        imagen.setImageResource(entrada.get_idImagen());
                    }
                }
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < datos.size()) {
                    items product = datos.get(position);
                    abrirDetalleProducto(product);
                }
            }
        });
    }
}
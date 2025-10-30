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
        btnCancelarBuscar = findViewById(R.id.btnCancelarBuscar);

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

        btnCancelarBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSearch();
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

        // Resaltar el botón activo (ninguno en productos, o puedes resaltar search)
        highlightActiveButton(-1); // -1 para no resaltar ninguno específico
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
                        icon.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_purple));
                    } else {
                        // Botones inactivos - color blanco
                        icon.setColorFilter(ContextCompat.getColor(this, android.R.color.white));
                    }
                }
            }
        }
    }

    // Tus métodos existentes se mantienen igual...
    private void initializeData() {
        datos = new ArrayList<items>();

        if ("SNEAKERS".equals(categoriaActual)) {
            // Datos de sneakers - usar el constructor correcto
            datos.add(new items(R.drawable.j1, "Jordan 1 Retro High", "1000€", false, "SNEAKERS",
                    "Las Jordan 1 Retro High son un icono del baloncesto y la cultura urbana. Diseño clásico con cuero premium y amortiguación Air-Sole."));
            datos.add(new items(R.drawable.airmax1patta, "Air Max 1 Patta 20th", "100€", false, "SNEAKERS",
                    "Edición especial colaborativa entre Nike y Patta para celebrar el 20º aniversario. Caracterizada por su diseño en tonos tierra, detalles en gamuza y elementos reflectantes."));
            datos.add(new items(R.drawable.j4red, "Jordan 4 Red Cement", "125€", false, "SNEAKERS",
                    "Las Air Jordan 4 'Red Cement' presentan un audaz color rojo fuego combinado con el icónico estampado cement. Cuentan con la emblemática malla de malla en los laterales."));
            datos.add(new items(R.drawable.tnoff, "Air VaporMax Off-White", "480€", false, "SNEAKERS",
                    "Colaboración exclusiva entre Nike y Off-White de Virgil Abloh. La VaporMax presenta detalles deconstructivos característicos de la marca."));
            datos.add(new items(R.drawable.airforce1, "Air Force 1 Off-White ICA", "725€", false, "SNEAKERS",
                    "Edición Museum de Off-White que deconstruye el clásico Air Force 1. Incluye detalles expuestos, costuras visibles y múltiples capas de materiales."));
            datos.add(new items(R.drawable.yeezy350beluga, "Yeezy 350 V2 Beluga", "235€", false, "SNEAKERS",
                    "El modelo Beluga original que inició la revolución Yeezy. Primeknit transpirable en tonos gris oscuro con la icónica franja naranja."));
            datos.add(new items(R.drawable.j11, "Air Jordan 11 Bred", "225€", false, "SNEAKERS",
                    "Las Jordan 11 'Bred' combinan patent leather negra brillante con detalles en rojo. La suela de goma translúcida y la entresuela de fibra de carbono."));
            datos.add(new items(R.drawable.j1travisolive, "Jordan 1 Low Travis Olive", "335€", false, "SNEAKERS",
                    "Colaboración entre Travis Scott y Jordan Brand en tonos oliva y negro. Caracterizada por el reverso del Swoosh, detalles en gamuza premium."));

        } else if ("CLOTHES".equals(categoriaActual)) {
            // Datos de ropa
            datos.add(new items(R.drawable.boxlogo1, "Hoodie Supreme BoxLogo", "355€", false, "CLOTHES",
                    "El icónico hoodie de Supreme con el famoso Box Logo bordado en el pecho. Fabricado en algodón French Terry de alta calidad."));
            datos.add(new items(R.drawable.denimtearsgrey, "Hoodie Denim Tears", "365€", false, "CLOTHES",
                    "Diseño conceptual de Tremaine Emory que explora la historia y cultura afroamericana. Estampado algodonero en la parte delantera."));
            datos.add(new items(R.drawable.boxersupreme, "Boxer Supreme Hanes", "95€", false, "CLOTHES",
                    "Pack de 3 boxers en colaboración con Hanes. Combinan el comfort clásico de Hanes con el branding de Supreme."));
            datos.add(new items(R.drawable.feargodpant, "SweetPants Fear of God", "1000€", false, "CLOTHES",
                    "Pantalones de chándal de lujo de la colección Essentials de Fear of God. Corte holgado, cintura elástica con cordón."));
            datos.add(new items(R.drawable.nupsetblack, "North Face 1996 Jacket", "275€", false, "CLOTHES",
                    "La chaqueta Nuptse de 1996 regresa con su diseño original. Relleno de pluma 700-fill para máximo calor."));

        } else if ("ACCESSORIES".equals(categoriaActual)) {
            // Datos de accesorios
            datos.add(new items(R.drawable.rolexdaytona, "Rolex Daytona Two Tone", "14000€", false, "ACCESSORIES",
                    "Reloj cronógrafo Rolex Cosmograph Daytona en acero Oystersteel y oro amarillo de 18k. Esfera negra con contadores contrastados."));
            datos.add(new items(R.drawable.bolsolv, "LV Speedy 30", "1650€", false, "ACCESSORIES",
                    "El clásico Speedy 30 de Louis Vuitton en canvas Monogram. Diseño cilíndrico con asas de piel, cierre con candado y llave."));
            datos.add(new items(R.drawable.fiftynine, "New Era Cap MLB", "25€", false, "ACCESSORIES",
                    "Gorra 59FIFTY de New Era para MLB. Ajuste estructurado, visera rígida y cierre de broche."));
            datos.add(new items(R.drawable.supremeband, "Supreme Band Bezel", "105€", false, "ACCESSORIES",
                    "Brazalete de silicona de Supreme con estampado de la marca alrededor de toda la pieza. Ajuste cómodo y elástico."));
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
        intent.putExtra("categoria", producto.getCategoria()); // Añadir la categoría
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

                    texto_superior_entrada.setText(entrada.get_textoTitulo());
                    texto_inferior_entrada.setText(entrada.get_textoContenido());
                    imagen_entrada.setImageResource(entrada.get_idImagen());
                }
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items product = datos.get(position);
                abrirDetalleProducto(product);
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

                    texto_titulo.setText(entrada.get_textoTitulo());
                    texto_datos.setText(entrada.get_textoContenido());
                    imagen.setImageResource(entrada.get_idImagen());
                }
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items product = datos.get(position);
                abrirDetalleProducto(product);
            }
        });
    }
}
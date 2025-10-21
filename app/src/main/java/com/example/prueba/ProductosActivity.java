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
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ProductosActivity extends AppCompatActivity {

    private ListView lista;
    private GridView grid;
    private ImageView  iconoBuscar, iconoMenu, iconoVolver;
    private LinearLayout layoutBuscar;
    private EditText editTextBuscar;
    private Button btnCancelarBuscar;
    private RadioButton radioButton_pulsado;
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

        // Configurar listeners
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
        setupListView();
        setupGridView();
    }

    private void initializeData() {
        datos = new ArrayList<items>();

        if ("SNEAKERS".equals(categoriaActual)) {
            // Datos de sneakers
            datos.add(new items(R.drawable.j1, "Jordan 1 Retro High", "1000€", false));
            datos.add(new items(R.drawable.airmax1patta, "Air Max 1 Patta 20th", "100€", false));
            datos.add(new items(R.drawable.j4red, "Jordan 4 Red Cement", "125€", false));
            datos.add(new items(R.drawable.tnoff, "Air VaporMax Off-White", "480€", false));
            datos.add(new items(R.drawable.airforce1, "Air Force 1 Off-White ICA", "725€", false));
            datos.add(new items(R.drawable.yeezy350beluga, "Yeezy 350 V2 Beluga", "235€", false));
            datos.add(new items(R.drawable.j11, "Air Jordan 11 Bred", "225€", false));
            datos.add(new items(R.drawable.j1travisolive, "Jordan 1 Low Travis Olive", "335€", false));

        } else if ("CLOTHES".equals(categoriaActual)) {
            // Datos de ropa
            datos.add(new items(R.drawable.boxlogo1, "Hoodie Supreme BoxLogo", "355€", false));
            datos.add(new items(R.drawable.denimtearsgrey, "Hoodie Denim Tears", "365€", false));
            datos.add(new items(R.drawable.boxersupreme, "Boxer Supreme Hanes", "95€", false));
            datos.add(new items(R.drawable.feargodpant, "SweetPants Fear of God", "1000€", false));
            datos.add(new items(R.drawable.nupsetblack, "North Face 1996 Jacket", "275€", false));

        } else if ("ACCESSORIES".equals(categoriaActual)) {
            // Datos de accesorios
            datos.add(new items(R.drawable.rolexdaytona, "Rolex Daytona Two Tone", "14000€", false));
            datos.add(new items(R.drawable.bolsolv, "LV Speedy 30", "1650€", false));
            datos.add(new items(R.drawable.fiftynine, "New Era Cap MLB", "25€", false));
            datos.add(new items(R.drawable.supremeband, "Supreme Band Bezel", "105€", false));
        }

        // Guardar copia de los datos originales
        datosOriginales = new ArrayList<>(datos);
    }
    private void toggleSearch() {
        isSearching = !isSearching;
        if (isSearching) {
            layoutBuscar.setVisibility(View.VISIBLE);
            editTextBuscar.requestFocus();
        } else {
            cancelSearch();
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
        } else {
            grid.setVisibility(View.GONE);
            lista.setVisibility(View.VISIBLE);
        }
    }

    private void setupListView() {
        lista.setAdapter(new Adaptador<items>(this, R.layout.entrada, datos) {
            @Override
            public void onEntrada(items entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = view.findViewById(R.id.texto_titulo);
                    TextView texto_inferior_entrada = view.findViewById(R.id.texto_datos);
                    ImageView imagen_entrada = view.findViewById(R.id.imagen);
                    RadioButton miRadio = view.findViewById(R.id.boton);

                    texto_superior_entrada.setText(entrada.get_textoTitulo());
                    texto_inferior_entrada.setText(entrada.get_textoContenido());
                    imagen_entrada.setImageResource(entrada.get_idImagen());
                    miRadio.setChecked(entrada.get_seleccion());

                    miRadio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateSelection(entrada, miRadio);
                        }
                    });

                    miRadio.setTag(entrada);
                }
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RadioButton radio = view.findViewById(R.id.boton);
                if (radio != null) {
                    radio.performClick();
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
                    RadioButton miRadio = view.findViewById(R.id.boton_grid);

                    texto_titulo.setText(entrada.get_textoTitulo());
                    texto_datos.setText(entrada.get_textoContenido());
                    imagen.setImageResource(entrada.get_idImagen());
                    miRadio.setChecked(entrada.get_seleccion());

                    miRadio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateSelection(entrada, miRadio);
                        }
                    });

                    miRadio.setTag(entrada);
                }
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items elemento = datos.get(position);
                RadioButton radio = view.findViewById(R.id.boton_grid);
                if (radio != null) {
                    radio.performClick();
                }
            }
        });
    }

    private void updateSelection(items entrada, RadioButton miRadio) {
        // Desmarcar radio button anterior y actualizar estados
        if (radioButton_pulsado != null && radioButton_pulsado != miRadio) {
            radioButton_pulsado.setChecked(false);
            for (items item : datos) {
                item.set_seleccion(false);
            }
        }

        // Marcar nuevo radio button
        radioButton_pulsado = miRadio;
        entrada.set_seleccion(true);

        // Forzar actualización de ambas vistas
        updateAdapters();
    }
}
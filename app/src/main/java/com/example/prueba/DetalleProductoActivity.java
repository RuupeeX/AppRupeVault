package com.example.prueba;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleProductoActivity extends AppCompatActivity {

    private ImageView imagenDetalle;
    private TextView tituloDetalle, precioDetalle, descripcionDetalle;
    private Spinner spinnerTallas;
    private String tallaSeleccionada;
    private String categoriaProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_producto);

        // Enlazar vistas
        imagenDetalle = findViewById(R.id.imagenDetalle);
        tituloDetalle = findViewById(R.id.tituloDetalle);
        precioDetalle = findViewById(R.id.precioDetalle);
        descripcionDetalle = findViewById(R.id.descripcionDetalle);
        spinnerTallas = findViewById(R.id.spinnerTallas);

        // Obtener datos del intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titulo = extras.getString("titulo");
            String precio = extras.getString("precio");
            int imagen = extras.getInt("imagen");
            String descripcion = extras.getString("descripcion");
            categoriaProducto = extras.getString("categoria"); // Obtener la categoría

            // Mostrar datos
            tituloDetalle.setText(titulo);
            precioDetalle.setText(precio);
            imagenDetalle.setImageResource(imagen);
            descripcionDetalle.setText(descripcion);
        }

        // Configurar el Spinner según la categoría
        setupSpinnerTallas();

        // Botón volver
        findViewById(R.id.iconoVolver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Botón añadir al carrito
        findViewById(R.id.btnComprar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tallaSeleccionada == null || tallaSeleccionada.equals("Seleccionar talla")) {
                    Toast.makeText(DetalleProductoActivity.this, "Por favor, selecciona una talla", Toast.LENGTH_SHORT).show();
                } else {
                    // Añadir al carrito
                    agregarAlCarrito();
                }
            }
        });
    }

    private void setupSpinnerTallas() {
        String[] tallas;
        String hintText;

        if ("SNEAKERS".equals(categoriaProducto)) {
            // Tallas de zapatillas (numéricas)
            tallas = new String[]{"Seleccionar talla", "36", "36.5", "37", "37.5", "38", "38.5",
                    "39", "40", "40.5", "41", "42", "42.5", "43", "44", "45"};
            hintText = "Seleccionar talla (EU)";
        } else if ("CLOTHES".equals(categoriaProducto)) {
            // Tallas de ropa
            tallas = new String[]{"Seleccionar talla", "XS", "S", "M", "L", "XL", "XXL", "XXXL"};
            hintText = "Seleccionar talla";
        } else {
            // Para accesorios u otros productos
            tallas = new String[]{"Seleccionar talla", "Única"};
            hintText = "Seleccionar opción";
        }

        // Crear adaptador personalizado
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.spinner_item,
                tallas
        ) {
            @Override
            public boolean isEnabled(int position) {
                // El primer item (hint) no es seleccionable
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;

                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                    textView.setBackgroundColor(Color.parseColor("#F5F5F5"));
                } else {
                    textView.setTextColor(Color.parseColor("#0C3F66"));
                    textView.setBackgroundColor(Color.WHITE);
                }

                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;

                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.parseColor("#0C3F66"));
                }

                return view;
            }
        };

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerTallas.setAdapter(adapter);

        // Listener para cuando se selecciona una talla
        spinnerTallas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    tallaSeleccionada = tallas[position];
                    if (view != null) {
                        ((TextView) view).setTextColor(Color.parseColor("#0C3F66"));
                    }

                    // Mostrar talla seleccionada (opcional)
                    Toast.makeText(DetalleProductoActivity.this,
                            "Talla seleccionada: " + tallaSeleccionada,
                            Toast.LENGTH_SHORT).show();
                } else {
                    tallaSeleccionada = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tallaSeleccionada = null;
            }
        });
    }

    private void agregarAlCarrito() {
        if (tallaSeleccionada == null || tallaSeleccionada.equals("Seleccionar talla")) {
            Toast.makeText(DetalleProductoActivity.this, "Por favor, selecciona una talla", Toast.LENGTH_SHORT).show();
        } else {
            String nombreProducto = tituloDetalle.getText().toString();
            String precio = precioDetalle.getText().toString();

            String mensaje = nombreProducto + " - Talla: " + tallaSeleccionada + " añadido al carrito";
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        }
    }
}
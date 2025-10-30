package com.example.prueba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvNombreCompleto, tvEmail, tvAvatarIniciales, tvTipoUsuario;
    private MaterialButton btnLogout, btnLoginAsUser;
    private ImageView iconoVolver;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_IS_GUEST = "isGuest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Enlazar vistas
        tvNombreCompleto = findViewById(R.id.tvNombreCompleto);
        tvEmail = findViewById(R.id.tvEmail);
        tvAvatarIniciales = findViewById(R.id.tvAvatarIniciales);
        tvTipoUsuario = findViewById(R.id.tvTipoUsuario);
        btnLogout = findViewById(R.id.btnLogout);
        btnLoginAsUser = findViewById(R.id.btnLoginAsUser);
        iconoVolver = findViewById(R.id.iconoVolver);

        // Configurar datos del usuario
        setupUserData();

        // Configurar listeners
        setupListeners();
    }

    private void setupUserData() {
        boolean isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
        boolean isGuest = sharedPreferences.getBoolean(KEY_IS_GUEST, false);

        if (isLoggedIn) {
            // Usuario registrado
            String email = sharedPreferences.getString(KEY_EMAIL, "usuario@ejemplo.com");
            String name = sharedPreferences.getString(KEY_NAME, "Usuario");

            tvEmail.setText(email);
            tvNombreCompleto.setText(name);

            // Generar iniciales del nombre
            String iniciales = generateInitials(name);
            tvAvatarIniciales.setText(iniciales);

            if (isGuest) {
                tvTipoUsuario.setText("Usuario Invitado");
                btnLogout.setVisibility(View.GONE);
                btnLoginAsUser.setVisibility(View.VISIBLE);
            } else {
                tvTipoUsuario.setText("Usuario Registrado");
                btnLogout.setVisibility(View.VISIBLE);
                btnLoginAsUser.setVisibility(View.GONE);
            }

        } else {
            // No hay usuario logueado (caso inesperado)
            tvEmail.setText("No logueado");
            tvNombreCompleto.setText("Invitado");
            tvAvatarIniciales.setText("IN");
            tvTipoUsuario.setText("No logueado");
            btnLogout.setVisibility(View.VISIBLE);
            btnLoginAsUser.setVisibility(View.GONE);
        }
    }

    private String generateInitials(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "US";
        }

        String[] names = fullName.trim().split("\\s+");
        if (names.length == 1) {
            return names[0].substring(0, Math.min(2, names[0].length())).toUpperCase();
        } else {
            return (names[0].charAt(0) + "" + names[names.length - 1].charAt(0)).toUpperCase();
        }
    }

    private void setupListeners() {
        // Botón volver
        iconoVolver.setOnClickListener(v -> {
            finish();
        });

        // Botón logout
        btnLogout.setOnClickListener(v -> {
            logoutUser();
        });

        // Botón login (para usuarios guest)
        btnLoginAsUser.setOnClickListener(v -> {
            navigateToLogin();
        });
    }

    private void logoutUser() {
        // Mostrar diálogo de confirmación
        new android.app.AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    performLogout();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void performLogout() {
        // Limpiar SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Navegar al LoginActivity
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar datos por si cambió algo
        setupUserData();
    }
}
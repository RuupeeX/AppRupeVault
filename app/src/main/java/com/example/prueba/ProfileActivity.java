package com.example.prueba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

        // Configurar la barra de navegación inferior
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        // Home Button - Volver al MainActivity
        LinearLayout homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            navigateToHome();
        });

        // Search Button
        LinearLayout searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> {
            showSearchFunctionality();
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

        // User Button - Ya estamos en perfil, puede servir para recargar
        LinearLayout userButton = findViewById(R.id.userButton);
        userButton.setOnClickListener(v -> {
            refreshProfile();
        });

        // Resaltar el botón de usuario (activo)
        highlightActiveButton(R.id.userButton);
    }

    private void navigateToHome() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void showSearchFunctionality() {
        Toast.makeText(this, "Búsqueda", Toast.LENGTH_SHORT).show();
        // Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
        // startActivity(intent);
    }

    private void showFavorites() {
        Toast.makeText(this, "Favoritos", Toast.LENGTH_SHORT).show();
        // Intent intent = new Intent(ProfileActivity.this, FavoritesActivity.class);
        // startActivity(intent);
    }

    private void showNotifications() {
        Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show();
        // Intent intent = new Intent(ProfileActivity.this, NotificationsActivity.class);
        // startActivity(intent);
    }

    private void refreshProfile() {
        // Recargar datos del perfil
        setupUserData();
        Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
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
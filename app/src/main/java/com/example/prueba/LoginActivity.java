package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmailLogin, etPasswordLogin;
    private MaterialButton btnLogin;
    private TextView tvRegisterLink;

    // Lista para almacenar usuarios
    public static ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);

        // Agregar algunos usuarios de prueba (solo si está vacía)
        if (listaUsuarios.isEmpty()) {
            listaUsuarios.add(new Usuario("Usuario Demo", "demo@rupevault.com", "123456"));
            listaUsuarios.add(new Usuario("Test User", "test@rupevault.com", "123456"));
        }

        // Click en botón Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsuario();
            }
        });

        // Click en enlace de registro
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irARegister();
            }
        });

        // Opcional: Rellenar campos automáticamente para testing
        etEmailLogin.setText("demo@rupevault.com");
        etPasswordLogin.setText("123456");
    }

    private void loginUsuario() {
        String email = etEmailLogin.getText().toString().trim();
        String password = etPasswordLogin.getText().toString().trim();

        // Validaciones
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buscar usuario en la lista
        Usuario usuarioEncontrado = null;
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                usuarioEncontrado = usuario;
                break;
            }
        }

        if (usuarioEncontrado != null) {
            // Login exitoso
            Toast.makeText(this, "Welcome " + usuarioEncontrado.getNombre() + "!", Toast.LENGTH_SHORT).show();

            // Ir a MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("usuario", usuarioEncontrado.getNombre());
            startActivity(intent);
            finish(); // Cerrar LoginActivity para que no pueda volver atrás
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void irARegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        // No usar finish() para que pueda volver al login
    }
}
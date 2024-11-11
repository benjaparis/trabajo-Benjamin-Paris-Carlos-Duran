package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private EditText usuarioEditText;
    private EditText contrasenaEditText;
    private EditText correoEditText;
    private EditText edadEditText;
    private Spinner spinnerRoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configuración del WebView
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //URL del video
        String videoUrl = "https://www.youtube.com/watch?v=5mGuCdlCcNM&t=24834s";
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(videoUrl);

        // Referencia de los EditTexts y Spinner
        usuarioEditText = findViewById(R.id.usuario);
        contrasenaEditText = findViewById(R.id.contrasena);
        correoEditText = findViewById(R.id.correo);
        edadEditText = findViewById(R.id.edad);
        spinnerRoles = findViewById(R.id.spinnerRoles);


        String[] roles = {"Usuario", "Administrador"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoles.setAdapter(adapter);
    }

    public void onClickAcceder(View view) {
        //Obtener los datos ingresados por el usuario
        String usuario = usuarioEditText.getText().toString().trim();
        String contrasena = contrasenaEditText.getText().toString().trim();
        String correo = correoEditText.getText().toString().trim();
        String edad = edadEditText.getText().toString().trim();
        String rol = spinnerRoles.getSelectedItem().toString();

        //Validar que los campos no estén vacíos
        if (usuario.isEmpty()) {
            Toast.makeText(this, "El campo de usuario está vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        if (contrasena.isEmpty()) {
            Toast.makeText(this, "El campo de contraseña está vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        if (correo.isEmpty()) {
            Toast.makeText(this, "El campo de correo está vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edad.isEmpty()) {
            Toast.makeText(this, "El campo de edad está vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        //Insertar el usuario en la base de datos
        DataHelper dh = new DataHelper(this, "usuarios.db", null, 1);
        SQLiteDatabase db = dh.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("usuario", usuario);
        contentValues.put("contrasena", contrasena);
        contentValues.put("correo", correo);
        contentValues.put("edad", edad);
        contentValues.put("rol", rol);

        //Insertar los datos en la tabla
        long id = db.insert("usuarios", null, contentValues);

        if (id == -1) {
            Toast.makeText(this, "Error al guardar el usuario", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();

            // Intent para ir a otra actividad
            Intent intent = new Intent(this, AccesoActivity.class);
            startActivity(intent);
        }

        db.close();
    }

    public void onClickSalir(View view) {
        // Método para salir de la aplicación
        finish();
    }

    // Método para limpiar los campos de texto
    public void onClickLimpiar(View view) {
        usuarioEditText.setText("");
        contrasenaEditText.setText("");
        correoEditText.setText("");
        edadEditText.setText("");
        spinnerRoles.setSelection(0); // Restablecer el Spinner al primer valor
    }
}

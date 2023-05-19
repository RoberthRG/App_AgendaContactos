package app_agendacontactos.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AddActivity extends AppCompatActivity {
    Contactos contactos;
    Integer id = 0;

    EditText txtNombre, txtTelefono, txtDireccion, txtEmail, txtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        contactos = new Contactos(this, "app_agendacontactos.db", 1);
        txtId = findViewById(R.id.txtId);
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtEmail = findViewById(R.id.txtEmail);
    }

    public void cmdCreate_onClick(View v) {
        Contacto c = contactos.Create(
                txtId.getText().toString(),
                txtNombre.getText().toString(),
                txtTelefono.getText().toString(),
                txtDireccion.getText().toString(),
                txtEmail.getText().toString()
        );
        if (c != null)
            Toast.makeText(this, "REGISTRO INSERTADO OK", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "ERROR !! REGISTRO NO INSERTADO", Toast.LENGTH_SHORT).show();
    }

    public void btnCancel(View view) {
        Intent btnCancelar = new Intent(this, MainActivity.class);
        startActivity(btnCancelar);
    }


}

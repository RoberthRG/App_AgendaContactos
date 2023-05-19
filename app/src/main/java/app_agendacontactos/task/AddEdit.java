package app_agendacontactos.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public class AddEdit extends AppCompatActivity {

    Contactos contactos;
    EditText txtId, txtNombre, txtTelefono, txtDireccion, txtMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        contactos = new Contactos(this, "app_agendacontactos.db", 1);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        String nombre = extras.getString("nombre");
        String telefono = extras.getString("telefono");
        String direccion = extras.getString("direccion");
        String mail = extras.getString("mail");

        txtId = findViewById(R.id.txtIdEdit);
        txtNombre = findViewById(R.id.txtNombreEdit);
        txtTelefono = findViewById(R.id.txtTelefonoEdit);
        txtDireccion = findViewById(R.id.txtDireccionEdit);
        txtMail = findViewById(R.id.txtEmailEdit);

        txtId.setText(id);
        txtNombre.setText(nombre);
        txtTelefono.setText(telefono);
        txtDireccion.setText(direccion);
        txtMail.setText(mail);

    }

    public void btnCancel(View view) {
        Intent btnCancelar = new Intent(this, MainActivity.class);
        startActivity(btnCancelar);
    }

    public void cmdUpdate_onClick(View v) {
        boolean resultado = contactos.Update(
                txtId.getText().toString(),
                txtNombre.getText().toString(),
                txtTelefono.getText().toString(),
                txtDireccion.getText().toString(),
                txtMail.getText().toString()
        );
        if (resultado == true)
            Toast.makeText(this, "RESGISTRO ACTUALZIADO OK", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "ERROR!! REGISTRO NO ACTUALIZADO", Toast.LENGTH_SHORT).show();
    }

    public void cmdDelete_onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro de que desea eliminar este registro?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean resultado = contactos.Delete(txtId.getText().toString());
                        if (resultado == true) {
                            Toast.makeText(getApplicationContext(), "REGISTRO BORRADO OK", Toast.LENGTH_SHORT).show();
                            txtId.setText("");
                            txtNombre.setText("");
                            txtTelefono.setText("");
                            txtDireccion.setText("");
                            txtMail.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "ERROR: REGISTRO NO ENCONTRADO !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // No hacer nada si se selecciona "No"
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}

package app_agendacontactos.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Contactos contactos;
    private TableRow touchedRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTableLayout(newText);
                return true;
            }
        });

        contactos = new Contactos(this, "app_agendacontactos.db", 1);
        Contacto[] c = contactos.Read_All();
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        final GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //View view = getCurrentFocus();
                if (touchedRow != null) {
                    String id = ((TextView) touchedRow.getChildAt(0)).getText().toString();
                    String nombre = ((TextView) touchedRow.getChildAt(1)).getText().toString();
                    String telefono = ((TextView) touchedRow.getChildAt(2)).getText().toString();
                    String direccion = ((TextView) touchedRow.getChildAt(3)).getText().toString();
                    String mail = ((TextView) touchedRow.getChildAt(4)).getText().toString();

                    Intent intent = new Intent(MainActivity.this, AddEdit.class);
                    intent.putExtra("id", id);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("telefono", telefono);
                    intent.putExtra("direccion", direccion);
                    intent.putExtra("mail", mail);
                    MainActivity.this.startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        if (c != null) {
            for (Contacto conta : c) {
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                TextView txtId = new TextView(this);
                txtId.setText(conta.Id);
                row.addView(txtId);


                TextView txtNombre = new TextView(this);
                txtNombre.setText(conta.Nombre);
                row.addView(txtNombre);


                TextView txtTelefono = new TextView(this);
                txtTelefono.setText(conta.Telefono);
                row.addView(txtTelefono);


                TextView txtDireccion = new TextView(this);
                txtDireccion.setText(conta.Direccion);
                row.addView(txtDireccion);


                TextView txtEmail = new TextView(this);
                txtEmail.setText(conta.Mail);
                row.addView(txtEmail);

                row.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            touchedRow = (TableRow) v;
                        }
                        // Pasar el evento al detector de gestos compatibles
                        gestureDetectorCompat.onTouchEvent(event);
                        return true;
                    }
                });

                tableLayout.addView(row);
            }
        }
    }

    public void addView(View view) {
        Intent btnAdd = new Intent(this, AddActivity.class);
        startActivity(btnAdd);
    }

    private void filterTableLayout(String query) {
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            boolean shouldShow = false;

            for (int j = 0; j < row.getChildCount(); j++) {
                View view = row.getChildAt(j);
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    String text = textView.getText().toString().toLowerCase();
                    if (text.contains(query.toLowerCase())) {
                        shouldShow = true;
                        break;
                    }
                }
            }

            if (shouldShow) {
                row.setVisibility(View.VISIBLE);
            } else {
                row.setVisibility(View.GONE);
            }
        }
    }
}
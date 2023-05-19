package app_agendacontactos.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class Contactos {
    private DBHellper dbHelper;
    private SQLiteDatabase db;

    public Contactos(Context contexto, String dbName, int version) {
        dbHelper = new DBHellper(contexto, dbName, null, version);
    }

    public Contacto Create(String id, String nombre, String telefono, String direccion, String mail) {
        db = dbHelper.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put("id", id);
        row.put("nombre", nombre);
        row.put("telefono", telefono);
        row.put("direccion", direccion);
        row.put("mail", mail);

        long qty = db.insert("contactos", null, row);
        if (qty > 0) {
            Contacto data = new Contacto();
            data.Id = id;
            data.Nombre = nombre;
            data.Telefono = telefono;
            data.Direccion = direccion;
            data.Mail = mail;
            return data;
        } else {
            return null;
        }
    }

    public Contacto Read_One(String id) {
        db = dbHelper.getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT id, nombre, telefono, direccion, mail FROM contactos WHERE id = '" + id + "'", null);
        if (cr.getCount() > 0) {
            Contacto conta = new Contacto();
            cr.moveToNext();
            conta.Id = cr.getString(0);
            conta.Nombre = cr.getString(1);
            conta.Telefono = cr.getString(2);
            conta.Direccion = cr.getString(3);
            conta.Mail = cr.getString(4);

            return conta;
        } else {
            return null;
        }
    }

    public Contacto[] Read_All() {
        db = dbHelper.getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT id, nombre, telefono, direccion, mail FROM contactos ORDER BY id", null);
        if (cr.getCount() > 0) {
            Contacto[] datos = new Contacto[cr.getCount()];
            Contacto conta;
            int i = 0;

            while (cr.moveToNext()) {
                conta = new Contacto();
                conta.Id = cr.getString(0);
                conta.Nombre = cr.getString(1);
                conta.Telefono = cr.getString(2);
                conta.Direccion = cr.getString(3);
                conta.Mail = cr.getString(4);

                datos[i++] = conta;
            }

            return datos;
        } else {
            return null;
        }
    }

    public Contacto[] Read_ByNombre(String find) {
        db = dbHelper.getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT id, nombre, telefono, direccion, mail FROM contactos WHERE nombre LIKE '%" + find + "%' ORDER BY nombre", null);
        if (cr.getCount() > 0) {
            Contacto[] datos = new Contacto[cr.getCount()];
            Contacto conta;

            int i = 0;

            while (cr.moveToNext()) {
                conta = new Contacto();
                conta.Id = cr.getString(0);
                conta.Nombre = cr.getString(1);
                conta.Telefono = cr.getString(2);
                conta.Direccion = cr.getString(3);
                conta.Mail = cr.getString(4);
            }
            return datos;
        } else {
            return null;
        }
    }

    public boolean Update(String id, String nombre, String telefono, String direccion, String mail) {
        db = dbHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("id", id);
        row.put("nombre", nombre);
        row.put("telefono", telefono);
        row.put("direccion", direccion);
        row.put("mail", mail);

        int qty = db.update("contactos", row, "id='" + id + "'", null);
        return qty > 0;
    }

    public boolean Delete(String id) {
        db = dbHelper.getWritableDatabase();
        int qty = db.delete("contactos", "id='" + id + "'", null);
        return qty > 0;
    }

    public List<Contacto> obtenerDatos() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nombre, telefono, direccion, mail FROM contactos ORDER BY nombre", null);
        List<Contacto> datos = new ArrayList<>();
        Contacto conta;
        if (cursor.moveToFirst()) {
            do {
                conta = new Contacto();
                conta.Id = cursor.getString(0);
                conta.Nombre = cursor.getString(1);
                conta.Telefono = cursor.getString(2);
                conta.Direccion = cursor.getString(3);
                conta.Mail = cursor.getString(4);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return datos;

    }
}

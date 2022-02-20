package com.example.pme14820;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import Configuracion.bd;
import Configuracion.conexion;

public class ActivityEditar extends AppCompatActivity {

    Configuracion.conexion conexion;
    EditText id;
    EditText TextNombre;
    EditText TextTelefono;
    EditText nota, TextPais;
    Button btnactualizar2,btneliminar2,btnBuscar,btnregresar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        conexion=new conexion(this, bd.NameDataBase, null, 1);
        TextNombre = (EditText) findViewById(R.id.TextNombre);
        TextTelefono = (EditText) findViewById(R.id.TextTelefono);
        TextPais = (EditText) findViewById(R.id.TextPais);
        nota = (EditText) findViewById(R.id.TextNota);
        btnBuscar= (Button) findViewById(R.id.btnBuscar);
       btnactualizar2= (Button) findViewById(R.id.btnactualizar2);
        btneliminar2 = (Button) findViewById(R.id.btneliminar2);
        btnregresar2 = (Button) findViewById(R.id.btnregresar2);



        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Buscar();
            }
        });

       btnactualizar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextNombre.getText().toString().isEmpty() && !TextTelefono.getText().toString().isEmpty() && !nota.getText().toString().isEmpty() ){
                    Actualizar();
                }
                else{
                    Toast.makeText(getApplicationContext()," Porfavor Llenar los campos vacios" ,Toast.LENGTH_LONG).show();

                }
            }
        });

        btneliminar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar();
            }
        });
    }
    private void Eliminar() {
        SQLiteDatabase db = conexion.getWritableDatabase();

        String[] params = {id.getText().toString()};
        String wherecond = bd.id + "=?";
        db.delete(bd.tablaPersonas, wherecond, params);
        Toast.makeText(getApplicationContext(), "Dato eliminado", Toast.LENGTH_LONG).show();
        limpiar();


    }

    private void Buscar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        //Parametros de configuracion de la sentencia SELECT
        String[] params = {id.getText().toString()}; //parametro de la busqueda
        String[] fields = {bd.pais,
                bd.Nombre,
                bd.Telefono,
                bd.Nota};
        String wherecond = bd.id + "=?";

        try {
            Cursor cdata = db.query(bd.tablaPersonas, fields, wherecond, params, null, null, null);
            cdata.moveToFirst();
            TextPais.setText(cdata.getString(0));
           TextNombre.setText(cdata.getString(1));
            nota.setText(cdata.getString(2));
           TextTelefono.setText(cdata.getString(3));


            Toast.makeText(getApplicationContext(), "Excelente", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            limpiar();
            Toast.makeText(getApplicationContext(), "Informacion no encontrado", Toast.LENGTH_LONG).show();
        }

    }
    private void Actualizar() {

        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] params = {id.getText().toString()};
        ContentValues valores = new ContentValues();
        valores.put(bd.pais,TextPais.getText().toString());
        valores.put(bd.Nombre,TextNombre.getText().toString());
        valores.put(bd.Telefono, TextTelefono.getText().toString());
        valores.put(bd.Nota, nota.getText().toString());
        db.update(bd.tablaPersonas, valores, bd.id + "=?", params);
        Toast.makeText(getApplicationContext(), "Dato actualizado", Toast.LENGTH_LONG).show();
        limpiar();
    }
    void limpiar(){
        id.setText("");
       TextPais.setText("");
      TextNombre.setText("");
       TextTelefono.setText("");
        nota.setText("");


    }

}
package com.example.pme14820;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import java.util.ArrayList;
import Configuracion.bd;
import Configuracion.conexion;
import Tablas.Personas;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Notation;

public class ListView1 extends AppCompatActivity {


   conexion conexion;
    ListView listV;
    Button btnregresar,btneliminar, btnactualizar ;
    ArrayList<Personas> lista;
    ArrayList<String> Arreglopersonas;

    EditText TextNombre, TextTelefono,TextNota,id;
    Spinner CmbPais;

    private void setbtneliminar()  {
        SQLiteDatabase db = conexion.getWritableDatabase();

        String[] params = {id.getText().toString()};
        String wherecond = bd.id + "=?";
        db.delete(bd.tablaPersonas, wherecond, params);
        Toast.makeText(getApplicationContext(), "Dato eliminado", Toast.LENGTH_LONG).show();
        limpiar();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        btnactualizar = (Button) findViewById(R.id.btnactualizar) ;
        btnregresar = (Button) findViewById(R.id.btnregresar) ;
        btneliminar = (Button) findViewById(R.id.btneliminar);
        btnregresar.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                Buscar();
            }
        });

       btnactualizar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ListView1.this,ActivityEditar.class);
            startActivity(intent);
        }
    });

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListView1.this,ActivityEditar.class);
                startActivity(intent);
            }
        });


        conexion = new conexion(this, bd.NameDataBase, null,1);
        listV = (ListView) findViewById(R.id.listV);
        ObtenerListaPersonas();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,Arreglopersonas);
        listV.setAdapter(adp);


    }

    private void ObtenerListaPersonas() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas lista_personas = null;
        lista = new ArrayList<Personas>();

        Cursor cursor = db.rawQuery( "select * from "+ bd.tablaPersonas,null);

        while(cursor.moveToNext()){
            lista_personas = new Personas();

            lista_personas.setPais(cursor.getString(1));
            lista_personas.setNombres(cursor.getString(2));
            lista_personas.setTelefono(cursor.getInt(3));
            lista_personas.setNota(cursor.getString(4));
            lista.add(lista_personas);
        }
        cursor.close();
        filllist();
    }

    private void filllist() {
        Arreglopersonas = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++) {
            Arreglopersonas.add(
                    lista.get(i).getPais() + " | " +
                            lista.get(i).getNombres() + " | " +
                            lista.get(i).getTelefono() + " | " +
                             lista.get(i).getNota());
        }

    }
    private void Buscar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        //Parametros de configuracion de la sentencia SELECT
        String[] params = {id.getText().toString()}; //parametro de la busqueda
        String[] fields = {bd.Nombre,
               bd.Telefono,
              bd.Nota,};
        String wherecond = bd.id + "=?";

        try {
            Cursor cdata = db.query(bd.tablaPersonas, fields, wherecond, params, null, null, null);
            cdata.moveToFirst();
           TextNombre.setText(cdata.getString(0));
            TextTelefono.setText(cdata.getString(1));
            TextNota.setText(cdata.getString(2));


            Toast.makeText(getApplicationContext(), "Exito", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            limpiar();
            Toast.makeText(getApplicationContext(), " no se encontrado", Toast.LENGTH_LONG).show();
        }
    }



        void limpiar(){
            id.setText("");
            TextNombre.setText("");
            TextTelefono.setText("");
          TextNota.setText("");
        }
        }




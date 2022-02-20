package com.example.pme14820;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Configuracion.bd;
import Configuracion.conexion;

public class MainActivity extends AppCompatActivity {
    EditText TextNombre;
    EditText TextTelefono;
    EditText nota;
    Button ButtonSalvar,ButtonSalvados;
    Spinner CmbPais;
    ImageView ImageViewfoto;
Button btnfoto;
    String ps;
    static final int PETICION_ACCESO_CAM = 100;
    static final int TAKE_PIC_REQUEST = 101;

    ImageView img_agg;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_SELECT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      TextNombre = (EditText) findViewById(R.id.TextNombre);
        TextTelefono = (EditText) findViewById(R.id.TextTelefono);
        nota = (EditText) findViewById(R.id.TextNota);
      ImageViewfoto = (ImageView) findViewById(R.id.ImageViewfoto);
      btnfoto = (Button) findViewById(R.id.btnfoto);
        ButtonSalvar = (Button) findViewById(R.id.buttonSalvar);
        ButtonSalvados = (Button) findViewById(R.id.buttonSalvados);
        CmbPais = (Spinner) findViewById(R.id.CmbPais);





                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.CmbPais, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CmbPais.setAdapter((adapter));

        CmbPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                ps = CmbPais.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       ButtonSalvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListView1.class);
                startActivity(intent);
            }
        });

       ButtonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()){
                    AgregarMas();
                }

            }
        });
        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });
    }


    public boolean validar(){
        Boolean retorno=true;
        String  validar1=TextNombre.getText().toString();
        String  validar2=TextTelefono.getText().toString();
        String  validar3=nota.getText().toString();

        if(validar1.isEmpty()){
            TextNombre.setError("Escribir un nombre");
            retorno=false;
        }
        if(validar2.isEmpty()){
           TextTelefono.setError("Escribir un telefono");
            retorno=false;
        }
        if(validar3.isEmpty()){
            nota.setError("Escribir una nota");
            retorno=false;
        }

        return true;
    }


    private void AgregarMas() {

      conexion conexion = new conexion(this, bd.NameDataBase, null,1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(bd.pais, String.valueOf(ps));
        valores.put(bd.Nombre, TextNombre.getText().toString());
        valores.put(bd.Telefono, TextTelefono.getText().toString());
        valores.put(bd.Nota, nota.getText().toString());

        Long resultado =db.insert(bd.tablaPersonas,bd.id,valores);
        Toast.makeText(getApplicationContext(),"Registro Ingresado:" + resultado.toString(),Toast.LENGTH_LONG).show();

        db.close();

        LimpiarPantalla();
    }
        private void LimpiarPantalla() {
            TextNombre.setText("");
            TextTelefono.setText("");
            nota.setText("");
        }

    //Permisos
    private void permisos()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PETICION_ACCESO_CAM);
        }
        else
        {
            tomarfoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PETICION_ACCESO_CAM)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                tomarfoto();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Se necesitan permisos de acceso a la camara", Toast.LENGTH_LONG).show();
        }


 btnfoto.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            permisos();
        }
    });


    }
    private void tomarfoto()
    {
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takepic.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takepic, TAKE_PIC_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Byte[] arreglo;

        if(requestCode == TAKE_PIC_REQUEST && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imagen = (Bitmap) extras.get("data");
           ImageViewfoto.setImageBitmap(imagen);
        }


    }



    }






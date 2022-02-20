package Configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


    public class conexion extends SQLiteOpenHelper
    {
        public conexion(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version)
        {
            super (context,dbname,factory,version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(bd.CreateTablePersonas);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(bd.DROPTablePersonas);

            onCreate(db);
}}

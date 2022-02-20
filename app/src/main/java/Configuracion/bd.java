package Configuracion;

public class bd {

    public static final String NameDataBase ="PME14820";


    public static final String tablaPersonas = "personas";
    public static final String id="id";
    public static final String pais="Pais";
    public static final String Nombre="Nombre";
    public static final String Telefono="telefono";
    public static final String Nota="Nota";

    public static final String CreateTablePersonas = "CREATE TABLE personas(id INTEGER PRYMARY KEY AUTROINCREMENT,"+" Pais TEXT,Nombre TEXT,Telefono INTEGER,Nota TEXT)";
    public static final String DROPTablePersonas ="DROP TABLE IF EXISTS personas";


}

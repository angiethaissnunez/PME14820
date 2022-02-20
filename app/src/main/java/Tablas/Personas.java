package Tablas;

public class Personas {
    private  Integer id;
    private String pais;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Personas(){}

    private String nombres;
    private Integer telefono;
    private String nota;

    public Personas(Integer id, String pais, String nombres, Integer telefono, String Nota) {
        this.id = id;
        this.pais = pais;
        this.nombres = nombres;
        this.telefono = telefono;
        this.nota = nota;
    }
}


package co.edu.unilibre.dominio;

public class Moto {
    private String placa;
    private String marca;
    private String idDueno;

    public Moto(String placa, String marca, String idDueno) {
        this.placa = placa;
        this.marca = marca;
        this.idDueno = idDueno;
    }

    public String obtenerPlaca() { return placa; }
    public String obtenerMarca() { return marca; }
    public String obtenerIdDueno() { return idDueno; }
}
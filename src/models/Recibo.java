package models;

public class Recibo {
    private String id;
    private TipoRecibo tipo;
    private String desricaoAdicional;
    private double valor;

    public Recibo(TipoRecibo tipo, double valor){
        this.tipo= tipo;
        this.valor= valor;
        this.desricaoAdicional= "nenhuma";
    }

    public Recibo(TipoRecibo tipo, double valor, String descricao){
        this.tipo= tipo;
        this.valor= valor;
        this.desricaoAdicional= descricao;
    }

    // Getters e Setters
    public TipoRecibo getTipo(){
        return tipo;
    }

    public void setTipo(TipoRecibo tipo) {
        this.tipo = tipo;
    }

    public String getDesricaoAdicional() {
        return desricaoAdicional;
    }

    public void setDesricaoAdicional(String desricaoAdicional) {
        this.desricaoAdicional = desricaoAdicional;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

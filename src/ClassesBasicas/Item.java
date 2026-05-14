package ClassesBasicas;

import Dados.InterfaceIdentificavel;
import java.time.LocalDate;

public class Item implements InterfaceIdentificavel {

    protected String id;
    protected String nome;
    protected LocalDate validade;
    protected double valor;

    public Item(String id, String nome, LocalDate validade, double valor) {
        this.id = id;
        this.nome = nome;
        this.validade = validade;
        this.valor = valor;
    }

    // getters e setters
    public String getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public LocalDate getValidade() {
        return validade;
    }
    public double getValor() {
        return valor;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getChave() {
        return this.id;
    }
}

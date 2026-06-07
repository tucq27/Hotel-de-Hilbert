package com.smarthotel.models;

public class Funcionario extends Pessoa {
    private String cargo;
    private boolean ocupado = false; // por padrão, funcionario está disponivel

    // Construtor
    public Funcionario(Pessoa p, String cargo) {
        super( p.getNome(), p.getCpf(), p.getDataNascimento(), p.getTelefone(), p.getEmail());
        this.cargo = cargo;
    }

    // Getters e Setters
    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public boolean isOcupado() {
        return ocupado;
    }
    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
}

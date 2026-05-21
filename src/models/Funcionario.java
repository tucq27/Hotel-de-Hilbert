package models;

import java.time.LocalDate; 

public class Funcionario extends Pessoa {
    private String cargo;
    private boolean ocupado = false; // por padrão, funcionario está disponivel

    // Construtor
    public Funcionario(String nome, String cpf, LocalDate dataNascimento,
                       String telefone, String email, String cargo) {
        super(nome, cpf, dataNascimento, telefone, email);
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

package com.smarthotel.models;
import com.smarthotel.dados.IIdentificavel;

public class Servico implements IIdentificavel {
    private String id;
    private String descricao;
    private Funcionario funcionario;

    // Construtor, considerando um unico funcionario
    public Servico(String descricao, Funcionario funcionario) {
        this.descricao = descricao;
        this.funcionario = funcionario;
    }

    // Serviços prestados
    public void lavarRoupa(Quarto quarto) {
        
    }
    public void levarComida(Quarto quarto) {
        
    }
    public void limparQuarto(Quarto quarto) {
        
    }

    // Getters
    public String getDescricao() {
        return descricao;
    }
    public Funcionario getFuncionario() {
        return funcionario;
    }
    public String getId() {
        return id;
    }

    // Setters
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getChave() {
        return id;
    }
}
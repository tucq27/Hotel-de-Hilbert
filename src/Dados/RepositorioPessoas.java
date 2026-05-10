package Dados;
import ClassesBasicas.Pessoa;

import java.time.LocalDate;

public class RepositorioPessoas extends RepositorioPadrao<Pessoa> {

    // construtor
    public RepositorioPessoas() {
        super();
    }

    // metodos de atualizar
    public void atualizarNome(String id, String novoNome) {
        Pessoa pessoa = buscar(id);
        if (pessoa != null) {
            pessoa.setNome(novoNome);
        }
    }
    public void atualizarCpf(String id, String novoCpf) {
        Pessoa pessoa = buscar(id);
        if (pessoa != null) {
            pessoa.setCpf(novoCpf);
        }
    }
    public void atualizarDataNascimento(String id, LocalDate novaData) {
        Pessoa pessoa = buscar(id);
        if (pessoa != null) {
            pessoa.setDataNascimento(novaData);
        }
    }
    public void atualizarTelefone(String id, String novoTelefone) {
        Pessoa pessoa = buscar(id);
        if (pessoa != null) {
            pessoa.setTelefone(novoTelefone);
        }
    }
    public void atualizarEmail(String id, String novoEmail) {
        Pessoa pessoa = buscar(id);
        if (pessoa != null) {
            pessoa.setEmail(novoEmail);
        }
    }
    
}

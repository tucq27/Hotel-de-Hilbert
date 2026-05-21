package models;

// Classe Hospede herda atributos e métodos da classe Pessoa
public class Hospede extends Pessoa {

    // Preferências do hóspede
    // Exemplo: quarto com varanda, cama de casal etc.
    private String preferencias;

    // Histórico do hóspede no hotel
    // Pode guardar informações sobre hospedagens anteriores
    private String historico;

    // Construtor da classe Hospede
    public Hospede(String nome, String cpf, java.time.LocalDate dataNascimento,
                   String telefone, String email,
                   String preferencias, String historico) {

        // Chama o construtor da classe Pessoa
        // para inicializar os dados básicos
        super(nome, cpf, dataNascimento, telefone, email);

        // Inicializa os atributos específicos de Hospede
        this.preferencias = preferencias;
        this.historico = historico;
    }

    // Retorna as preferências do hóspede
    public String getPreferencias() {
        return preferencias;
    }

    // Altera as preferências do hóspede
    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    // Retorna o histórico do hóspede
    public String getHistorico() {
        return historico;
    }

    // Altera o histórico do hóspede
    public void setHistorico(String historico) {
        this.historico = historico;
    }

}

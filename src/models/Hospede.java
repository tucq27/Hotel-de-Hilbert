package models;

// Classe Hospede herda atributos e métodos da classe Pessoa
public class Hospede extends Pessoa {

    private String preferencias; // Preferências do hóspede ( Ex: quarto com varanda, cama de casal, etc)
    private String historico; // Pode guardar informações sobre hospedagens anteriores

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

    public String getPreferencias() {
        return preferencias;
    }
    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public String getHistorico() {
        return historico;
    }
    public void setHistorico(String historico) {
        this.historico = historico;
    }

}

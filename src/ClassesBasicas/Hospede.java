package ClassesBasicas;

public class Hospede extends Pessoa {
    private String preferencias;
    private String historico;

    public Hospede(String nome, String cpf, java.time.LocalDate dataNascimento,
                   String telefone, String email,
                   String preferencias, String historico) {
        super(nome, cpf, dataNascimento, telefone, email);
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

    public void exibirDados() {
    }
}

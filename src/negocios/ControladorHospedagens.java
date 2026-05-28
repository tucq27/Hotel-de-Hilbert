package negocios;
import dados.IRepositorio;
import dados.RepoHospedagens;
import models.Hospedagem;
import models.Quarto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;


public class ControladorHospedagens {

    static private IRepositorio<Hospedagem> repositorioHospedagens;
    private ControladorQuartos controladorQuartos;
    private ControladorPessoas controladorPessoas;
    static private float taxaTemporada = 1.0;

    public ControladorHospedagens(ControladorQuartos controladorQuartos) {

        if (repositorio == null) {
            repositorio = new RepoHospedagens();
        }
    }


    public criarHospedagem(Hospedagem hospedagem) {
        repositorioHospedagens.adicionar(hospedagem);
    }

    public checkIn(Hospedagem hospedagem) {
        hospedagem.checkIn();
    }

    public void cancelarHospedagem(Hospedagem hospedagem) {
        LocalDateTime agora = LocalDateTime.now();
        long periodo = Duration.between(agora, hospedagem.horarioEntrada)getHours;
        // ^Eu não faço ideia se essa linha funciona
        if (periodo > 24.0) {
            repositorioHospedagens.remover(Hospedagem);
            //Cancelar a estadia de graça; Implementação na fatura
        }
        else {
            repositorioHospedagens.remover(Hospedagem);
            //Sinalizar para cobrar a hospedagem; Implementação na fatura
        }
    }


    private setTaxaTemporada() {
        Month mesAtual = LocalDate.now().getMonth();
        if (mesAtual == Month.JANUARY ||
            mesAtual == Month.DECEMBER ||
            mesAtual == Month.JULY ||
            mesAtual == Month.JUNE) {
                ControladorHospedagens.taxaTemporada = 2.0;
                //Ainda temos que implementar realmente a taxa no pagamento
            }
    }

    public atualizar(Hospedagem hospedagem) {

        if (hospedagem.)
    }
}

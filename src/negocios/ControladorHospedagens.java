package negocios;
import dados.IRepositorio;
import dados.RepoHospedagens;
import models.Hospedagem;
import models.Quarto;
import java.time.LocalDate;

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


    private setTaxaTemporada() {
        Month mesAtual = LocalDate.now().getMonth();
        if (mesAtual == Month.JANUARY ||
            mesAtual == Month.DECEMBER ||
            mesAtual == Month.JULY ||
            mesAtual == Month.JUNE) {

                ControladorHospedagens.taxaTempoarada = 2.0;
            }
    }

    public atualizar(Hospedagem hospedagem) {

        if (hospedagem.)
    }
}

package negocios;
import dados.IRepositorio;
import dados.Repositorio;
import models.Hospedagem;
import models.Quarto;

public class ControladorHospedagens {

    static private IRepositorio<Hospedagem> repositorioHospedagens;
    private ControladorQuartos controladorQuartos;
    private ControladorPessoas controladorPessoas;
    static private taxaTemporada;

    public ControladorHospedagens(ControladorQuartos controladorQuartos) {

        if (repositorio == null) {
            repositorio = new RepoPadrao<Hospedagem>();
        }
    }


    public criarHospedagem(Hospedagem hospedagem) {

        repositorioHospedagens.adicionar(hospedagem);

    }

    public checkIn(Hospedagem hospedagem) {

    }


    private setTaxaTemporada() {


    }
    public atualizar(Hospedagem hospedagem) {

        if (hospedagem.)
    }
}

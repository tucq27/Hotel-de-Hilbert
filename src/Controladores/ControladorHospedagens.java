package Controladores;
import Dados.InterfaceRepositorio;
import Dados.RepositorioPadrao;
import ClassesBasicas.hospedagem;
import ClassesBasicas.quarto;

public class ControladorHospedagens {

    static private InterfaceRepositorio<Hospedagem> repositorioHospedagens;
    private ControladorQuartos controladorQuartos;
    private ControladorPessoas controladorPessoas;
    static private taxaTemporada;

    public ControladorHospedagens(ControladorQuartos controladorQuartos) {

        if (repositorio == null) {
            repositorio = new RepositorioPadrao<Hospedagem>();
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

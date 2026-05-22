package dados;
import models.Reserva;

// embora a resrva herde de hospedagem, é interessante ter um repositorio especifico
// pois pode facilitar a busca por reservas ativas, por exemplo
public class RepoReservas extends Repositorio<Reserva> {

    // construtor
    public RepoReservas() {
        super();
    }

}
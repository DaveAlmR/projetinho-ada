package org.atividade.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.atividade.model.Pessoa;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaRepository implements PanacheRepository<Pessoa> {


    public Pessoa findByUUID(String uuid) {
        Pessoa pessoa = find("SELECT p FROM Pessoa p WHERE p.uuid = ?1", uuid).singleResult();
        return pessoa;
    }

    // public List<Pessoa> findPessoaWithPlaylist(String uuid) {
    //     List<Pessoa> lista= find("Select p FROM Pessoa p JOIN FETCH p.playlists AS play WHERE play.uuid = ?1",uuid).list();
    //     return lista;
    // }

}

package org.atividade.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.atividade.model.Musica;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class MusicaRepository implements PanacheRepository<Musica> {

    public Musica findByUUID(String uuid) {
//        PanacheQuery<Musica> query = find("SELECT m FROM Musica m WHERE m.uuid = ?1", uuid,nati);
        TypedQuery<Musica> query = getEntityManager().createQuery("SELECT m FROM Musica m Where m.uuid = :uuid", Musica.class);
        query.setParameter("uuid",uuid);
        Musica musica = query.getSingleResult();

//        Musica musica = query.singleResult();

        return musica;
    }
}

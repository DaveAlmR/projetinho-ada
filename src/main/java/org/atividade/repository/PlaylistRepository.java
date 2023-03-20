package org.atividade.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.atividade.model.Playlist;

import javax.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class PlaylistRepository implements PanacheRepository<Playlist> {
    public Playlist findByUid(String uid){

        return this.find("uid",uid).firstResult();

    }
}

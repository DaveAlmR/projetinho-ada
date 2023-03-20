package org.atividade.service;

import org.atividade.dto.playlist.PlaylistDto;
import org.atividade.dto.playlist.PlaylistPostDto;
import org.atividade.model.Musica;
import org.atividade.model.Pessoa;
import org.atividade.model.Playlist;
import org.atividade.repository.MusicaRepository;
import org.atividade.repository.PlaylistRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ApplicationScoped
public class PlaylistService {
    @Inject
    PlaylistRepository playlistRepository;
    @Inject
    MusicaRepository musicaRepository;

    @Transactional
    public Playlist adicionarMusica(Musica musica, String uuid){
        Playlist playlist = this.getByUUID(uuid);
        playlist.getMusicas().add(musica);
        playlistRepository.persist(playlist);
//        Playlist playlist=playlistRepository.findByUid(base.getPlaylistUid());
//        Musica musica=musicaRepository.find("uid",base.getMusicaUID()).firstResult();
//        playlist.getMusicas().add(musica);
//        playlistRepository.persistAndFlush(playlist);
        return playlist;
    }

    @Transactional
    public Playlist removerMusica(Musica musica, String uuid) {
        Playlist playlist = this.getByUUID(uuid);
        playlist.getMusicas().removeIf(m->m.getId().equals(musica.getId()));
        playlistRepository.persist(playlist);
        return playlist;
    }
    public List<Playlist> findAll(){
        return playlistRepository.findAll().stream().filter(p->p.isPublica()).collect(Collectors.toList());
    }
    @Transactional
    public Playlist novaPlaylist(PlaylistPostDto playlistPostDto, Pessoa pessoa){
        Playlist playlist=new Playlist();
        playlist.setPublica(playlistPostDto.getIsPublic());
        playlist.setNome(playlistPostDto.getNome());
        playlist.setDono(pessoa);
        playlistRepository.persistAndFlush(playlist);
        return playlist;
    }

    @Transactional
    public Playlist update(PlaylistDto playlistDto,String uid){
        Playlist playlist = this.getByUUID(uid);
        if(playlist==null){
            throw new NoSuchElementException("Essa playlist não existe.");
        }
        if(playlistDto.getNome()!= null){
            playlist.setNome(playlistDto.getNome());
        }
        playlist.setPublica(playlistDto.isPublica());
        playlistRepository.persistAndFlush(playlist);
        return playlist;
    }
    @Transactional
    public void delete(String uid){
        Playlist playlist = this.getByUUID(uid);
        if(playlist==null){
            throw new NoSuchElementException("Essa playlist não existe.");
        }
        playlistRepository.delete(playlist);
    }

    public Playlist getByUUID( String uuid ) {
        Playlist playlist;

        try {
            playlist = playlistRepository.findByUid(uuid);
        } catch (NoResultException e) {
            throw new NoSuchElementException("UUID não encontrado");
        }

        return playlist;
    }
}

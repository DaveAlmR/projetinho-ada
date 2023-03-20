package org.atividade.service;

import org.atividade.dto.musica.MusicaGetDto;
import org.atividade.dto.playlist.PlaylistDto;
import org.atividade.dto.playlist.PlaylistPostDto;
import org.atividade.factory.DtoFactory;
import org.atividade.model.Musica;
import org.atividade.model.Pessoa;
import org.atividade.model.Playlist;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class OperationService {

    @Inject
    MusicaService musicaService;

    @Inject
    PlaylistService playlistService;

    @Inject
    PessoaService pessoaService;

    public MusicaGetDto curteMusica(String pessoaUUID, String musicaUUID) {
        Pessoa pessoa = pessoaService.findByUuid(pessoaUUID);
        Musica musica = musicaService.read(musicaUUID);


        musicaService.ganhaCurtida(musicaUUID, pessoa);
        pessoaService.curtirMusica(pessoaUUID, musica);
        return DtoFactory.modelToDto(musica);
    }

    public MusicaGetDto descurteMusica(String musicaUid, String pessoaUid) {
        Musica musica = musicaService.read(musicaUid);
        Pessoa pessoa = pessoaService.findByUuid(pessoaUid);

        musicaService.perdeCurtida(musicaUid, pessoa);
        pessoaService.descurtirMusica(pessoaUid, musica);
        return DtoFactory.modelToDto(musica);
    }

    public PlaylistDto criaPlaylist(PlaylistPostDto playlistPostDto) {
        Pessoa pessoa = pessoaService.findByUuid(playlistPostDto.getDonoUid());
        Playlist playlist = playlistService.novaPlaylist(playlistPostDto, pessoa);

        pessoaService.adicionarPlaylist(playlistPostDto.getDonoUid(), playlist);
        return DtoFactory.modelToDto(playlist);
    }

    public void deletarPlaylist(String playlistUid) {
        playlistService.delete(playlistUid);
    }

    public void adicionaMusica(String musicaUid, String playlistUid) {
        Musica musica = musicaService.read(musicaUid);
        playlistService.adicionarMusica(musica, playlistUid);
    }

    public void removeMusica(String musicaUid, String playlistUid) {
        Musica musica = musicaService.read(musicaUid);
        playlistService.removerMusica(musica, playlistUid);
    }


}

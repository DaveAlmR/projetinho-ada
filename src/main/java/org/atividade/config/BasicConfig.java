package org.atividade.config;

import io.quarkus.runtime.StartupEvent;
import org.atividade.model.Genero;
import org.atividade.model.Musica;
import org.atividade.model.Pessoa;
import org.atividade.model.Playlist;
import org.atividade.repository.GeneroRepository;
import org.atividade.repository.MusicaRepository;
import org.atividade.repository.PessoaRepository;
import org.atividade.repository.PlaylistRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class BasicConfig {
    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    PlaylistRepository playlistRepository;

    @Inject
    MusicaRepository musicaRepository;

    @Inject
    GeneroRepository generoRepository;

    @Transactional
    public void iniciaDB(@Observes StartupEvent ev) {
        Pessoa bobo = new Pessoa();
        bobo.setUuid("pessoa");
        bobo.setNome("bobo");
        pessoaRepository.persist(bobo);

        Playlist playlist = new Playlist();
        playlist.setUid(UUID.randomUUID().toString());
        playlist.setDono(bobo);
        playlist.setPublica(true);
        playlist.setNome("playlist do bobo");
        playlistRepository.persist(playlist);

        Genero genero = new Genero();
        genero.setNome("New Metal");
        generoRepository.persist(genero);


        Musica musica = new Musica();
        musica.setUuid("musica");
        musica.setNome("In the end");
        musica.setGenero(genero);
        musicaRepository.persist(musica);
//        PessoaPostDto pessoa = new PessoaPostDto();
//        pessoa.nome = "Bobo1";
//        PessoaGetDto pessoaGet3 = pessoaService.PostPessoa(pessoa);
//        pessoa.nome = "bobo2";
//        PessoaGetDto pessoaGet2 = pessoaService.PostPessoa(pessoa);
//        pessoa.nome = "bobo3";
//        PessoaGetDto pessoaGet1 = pessoaService.PostPessoa(pessoa);
//
//        PlaylistPostDto playlist = new PlaylistPostDto();
//        playlist.isPublic = true;
//        playlist.nome = "nome";
//        playlist.uuidPessoa = pessoaGet1.uuid;
//        operationService.criaPlaylist(playlist, pessoaGet1.uuid);
//        playlist.uuidPessoa = pessoaGet2.uuid;
//        operationService.criaPlaylist(playlist, pessoaGet2.uuid);
//        playlist.uuidPessoa = pessoaGet3.uuid;
//        operationService.criaPlaylist(playlist, pessoaGet3.uuid);

    }

}

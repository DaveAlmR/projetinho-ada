package org.atividade.service;

import org.atividade.dto.pessoa.PessoaGetDto;
import org.atividade.dto.pessoa.PessoaPostDto;
import org.atividade.factory.DtoFactory;
import org.atividade.model.Musica;
import org.atividade.model.Pessoa;
import org.atividade.model.Playlist;
import org.atividade.repository.PessoaRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class PessoaService {

    @Inject
    PessoaRepository repository;

    @Transactional
    public PessoaGetDto criarPessoa(PessoaPostDto dto) {
        Pessoa model = new Pessoa();
        model.setUuid(UUID.randomUUID().toString());
        model.setNome(dto.nome);
        repository.persist(model);
        return DtoFactory.modelToDto(model);
    }

    @Transactional
    public PessoaGetDto editarPessoa(String uuid, PessoaPostDto dto) {
        Pessoa model;
        try {
            model = repository.findByUUID(uuid);
        } catch (NoResultException e) {
            throw new NoSuchElementException("UUID não encontrado");
        }
        model.setNome(dto.nome);
        model.getMusicasCurtidas();
        model.getPlaylists();
        repository.persist(model);
        return DtoFactory.modelToDto(model);
    }

    public PessoaGetDto obterPessoaUuid(String uuid) {
        Pessoa model;
        try {
            model = repository.findByUUID(uuid);
        } catch (NoResultException e) {
            throw new NoSuchElementException("UUID não encontrado");
        }
        return DtoFactory.modelToDto(model);
    }

    public List<PessoaGetDto> obterTodos() {
        List<Pessoa> pessoas = repository.findAll().list();
        return pessoas.stream().map(DtoFactory::modelToDto).collect(Collectors.toList());
    }

    @Transactional
    public void deletar(String uuid) {
        Pessoa model;
        try {
            model = repository.findByUUID(uuid);
        } catch (NoResultException e) {
            throw new NoSuchElementException("UUID não encontrado");
        }

        repository.delete(model);
    }

    public Pessoa findByUuid(String uuid) {
        Pessoa model;
        try {
            model = repository.findByUUID(uuid);
        } catch (NoResultException e) {
            throw new NoSuchElementException("UUID não encontrado");
        }
        return model;
    }

    @Transactional
    public void curtirMusica(String uuid, Musica model) {
        Pessoa pessoa = repository.findByUUID(uuid);
        pessoa.getMusicasCurtidas().add(model);
        repository.persist(pessoa);
    }

    @Transactional
    public void descurtirMusica(String uuid, Musica model) {
        Pessoa pessoa = repository.findByUUID(uuid);
        pessoa.getMusicasCurtidas().remove(model);
        repository.persist(pessoa);
    }

    @Transactional
    public void adicionarPlaylist(String uuid, Playlist playlist) {
        Pessoa pessoa = repository.findByUUID(uuid);
        pessoa.getPlaylists().add(playlist);
        repository.persist(pessoa);
    }

    @Transactional
    public void removerPlaylist(Playlist playlist) {
        Pessoa pessoa = playlist.getDono();
        pessoa.getPlaylists().remove(playlist);
        repository.persist(pessoa);
    }

    // public void deletarPlaylistEmPessoas(Playlist model) {
    //     String uuidPlaylist = model.getUid();
    //     List<Pessoa> listaPessoas = repository.findPessoaWithPlaylist(uuidPlaylist);
    //     listaPessoas.stream().forEach(pessoa -> {
    //         pessoa.getPlaylists().remove(model);
    //         repository.persist(pessoa);
    //     });

    // }

}
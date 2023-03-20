package org.atividade.service;

import org.atividade.dto.musica.MusicaPostDto;
import org.atividade.model.Musica;
import org.atividade.model.Pessoa;
import org.atividade.repository.MusicaRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class MusicaService {
    @Inject
    MusicaRepository musicaRepository;

    @Inject
    GeneroService generoService;

    @Transactional
    public Musica create(MusicaPostDto musicaDto) throws NoResultException {
        Musica musica = new Musica();
        musica.setUuid(UUID.randomUUID().toString());
        musica.setNome(musicaDto.getNome());
        musica.setGenero(generoService.getByUid(musicaDto.getGeneroUid()));
        musicaRepository.persistAndFlush(musica);
        return musica;
    }

    public List<Musica> readAll() {
        return musicaRepository.findAll().list();
    }

    public Musica read(String uuid) throws NoResultException {
        Musica musica;
        try {
            musica = musicaRepository.findByUUID(uuid);
        } catch (NoResultException e) {
            throw new NoSuchElementException("UUID não encontrado");
        }
        return musica;
    }

    @Transactional
    public void delete(String uid) {
        musicaRepository.delete(this.read(uid));
    }

    @Transactional
    public Musica update(MusicaPostDto musicaDto, String uid) throws NoResultException {
        Musica musica = this.read(uid);
        musica.setNome(musicaDto.getNome());
        return musica;
    }

    @Transactional
    public void ganhaCurtida(String uuid, Pessoa pessoa) {
        Musica musica = read(uuid);
        musica.getPessoas().add(pessoa);
        musicaRepository.persistAndFlush(musica);
    }

    @Transactional
    public void perdeCurtida(String uuid, Pessoa pessoa) {
        Musica musica = read(uuid);
        musica.getPessoas().remove(pessoa);
        musicaRepository.persistAndFlush(musica);
    }


}

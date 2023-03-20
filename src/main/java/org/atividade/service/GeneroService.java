package org.atividade.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.atividade.dto.GeneroDto;
import org.atividade.model.Genero;
import org.atividade.repository.GeneroRepository;

import static org.atividade.util.CustomExceptions.getInternError;

@ApplicationScoped
public class GeneroService {
    
    @Inject
    GeneroRepository generoRepository;

    @Transactional(rollbackOn = Exception.class)
    public Genero create(GeneroDto generoDto) {
        try {
            var genero = Genero
                    .builder()
                    .nome(generoDto.getNome())
                    .build();

            generoRepository.persistAndFlush(genero);

            return genero;
        } catch (Exception e) {
            e.printStackTrace();
            throw getInternError(e);
        }
    }

    public List<Genero> getAll() {
        try {
            return generoRepository
                    .findAll()
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            throw getInternError(e);
        }
    }

    public Genero getByUid(UUID uid) throws NoResultException {
        try {
            Genero genero = generoRepository.find("uid", uid).firstResult();

            if(genero == null) {
                throw new NoResultException();
            } else {
                return genero;
            }
        } catch (NoResultException e) {
            throw new NoResultException("Gênero não encontrado!");
        } catch (Exception e) {
            throw getInternError(e);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(UUID uid) {
        try {
            generoRepository.delete(this.getByUid(uid));
        } catch (Exception e) {
            throw getInternError(e);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public Genero update(GeneroDto generoDto, UUID uid) throws NoResultException{
            try {
                Genero genero = this.getByUid(uid);
                genero.setNome(generoDto.getNome());
                return genero;
            } catch (NoResultException e) {
                throw e;
            } catch (Exception e) {
                throw getInternError(e);
            }
    }

}

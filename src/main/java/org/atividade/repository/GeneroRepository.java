package org.atividade.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.atividade.model.Genero;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GeneroRepository implements PanacheRepository<Genero>{
    
}

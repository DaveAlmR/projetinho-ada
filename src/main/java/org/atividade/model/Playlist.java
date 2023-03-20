package org.atividade.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uid=UUID.randomUUID().toString();

    private String nome;

    @OneToMany( fetch = FetchType.EAGER)
    private List<Musica> musicas = new ArrayList();

    private boolean publica=true;

    @ManyToOne
    private Pessoa dono;

}

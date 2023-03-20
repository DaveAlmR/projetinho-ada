package org.atividade.model;

import java.util.UUID;

import javax.persistence.*;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genero")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 16, columnDefinition="uuid")
    public UUID uid;
    
    public String nome;
}

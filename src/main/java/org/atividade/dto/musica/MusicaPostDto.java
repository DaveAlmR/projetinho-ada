package org.atividade.dto.musica;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MusicaPostDto {
    
    public String nome;

    public UUID generoUid;

}

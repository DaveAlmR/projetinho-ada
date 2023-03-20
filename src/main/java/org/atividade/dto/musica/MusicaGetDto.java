package org.atividade.dto.musica;

import lombok.Getter;
import lombok.Setter;
import org.atividade.dto.GeneroDto;

@Getter
@Setter
public class MusicaGetDto {

    public String uid;
    
    public String nome;

    public GeneroDto generoDto;

    public long curtidas;

}

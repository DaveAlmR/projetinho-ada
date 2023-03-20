package org.atividade.dto.playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistPostDto {
    
    private String nome;

    private String donoUid;

    private Boolean isPublic;

}

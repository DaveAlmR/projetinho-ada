package org.atividade.dto.playlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.atividade.dto.musica.MusicaGetDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistDto {
    private String uid;
    private String nome;
    private List<MusicaGetDto> musicas =new ArrayList<>();
    private boolean publica=true;
    private String dono;


}

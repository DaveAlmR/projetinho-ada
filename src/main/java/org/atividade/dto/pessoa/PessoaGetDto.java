package org.atividade.dto.pessoa;

import org.atividade.dto.musica.MusicaGetDto;
import org.atividade.dto.playlist.PlaylistDto;

import java.util.List;

public class PessoaGetDto {

    public String uuid;

    public String nome;

    public List<MusicaGetDto> musicasCurtidas;

    public List<PlaylistDto> playlists;
}

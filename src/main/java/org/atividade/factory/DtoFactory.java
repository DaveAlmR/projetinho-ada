package org.atividade.factory;

import org.atividade.dto.GeneroDto;
import org.atividade.dto.musica.MusicaGetDto;
import org.atividade.dto.pessoa.PessoaGetDto;
import org.atividade.dto.playlist.PlaylistDto;
import org.atividade.model.Genero;
import org.atividade.model.Musica;
import org.atividade.model.Pessoa;
import org.atividade.model.Playlist;

import java.util.stream.Collectors;

public class DtoFactory {

    public static PessoaGetDto modelToDto(Pessoa model) {
        PessoaGetDto dto = new PessoaGetDto();
        dto.uuid = model.getUuid();
        dto.nome = model.getNome();
        dto.musicasCurtidas = model.getMusicasCurtidas().stream().map(DtoFactory::modelToDto).collect(Collectors.toList());
        dto.playlists = model.getPlaylists().stream().map(DtoFactory::modelToDto).collect(Collectors.toList());
        return dto;
    }

    public static GeneroDto modelToDto(Genero genero) {
        GeneroDto generoDto = new GeneroDto();
        generoDto.setNome(genero.getNome());
        return generoDto;
    }

    public static MusicaGetDto modelToDto(Musica musica) {
        MusicaGetDto musicaDto = new MusicaGetDto();
        musicaDto.setUid(musica.getUuid());
        musicaDto.setNome(musica.getNome());
        musicaDto.setGeneroDto(DtoFactory.modelToDto(musica.getGenero()));
        musicaDto.curtidas = musica.getPessoas().stream().count();
        return musicaDto;
    }

    public static PlaylistDto modelToDto(Playlist playlist){
        return PlaylistDto.builder()
                .uid(playlist.getUid())
                .publica(playlist.isPublica())
                .musicas(playlist.getMusicas().stream().map(DtoFactory::modelToDto).collect(Collectors.toList()))
                .nome(playlist.getNome())
                .dono(playlist.getDono().getNome())
                .build();
    }
}

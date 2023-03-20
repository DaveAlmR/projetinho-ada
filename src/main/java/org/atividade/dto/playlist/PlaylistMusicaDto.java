package org.atividade.dto.playlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistMusicaDto {
    @NotBlank
    private String playlistUid;
    @NotBlank
    private String musicaUID;
}

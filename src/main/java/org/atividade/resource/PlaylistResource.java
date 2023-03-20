package org.atividade.resource;


import org.atividade.dto.playlist.PlaylistDto;
import org.atividade.dto.playlist.PlaylistPostDto;
import org.atividade.factory.DtoFactory;
import org.atividade.model.Playlist;
import org.atividade.service.OperationService;
import org.atividade.service.PlaylistService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/playlist")

@ApplicationScoped
public class PlaylistResource {
    @Inject
    PlaylistService playlistService;

    @Inject
    OperationService operationService;


    @GET
    @Path("/list")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso")},
                            schema = @Schema(implementation = PlaylistDto.class))
            }
    )
    @Operation(summary = "Listar playlists publicas.", description = "Lista todas as playlists publicas.")
    public Response getAll() {
        return Response.status(Response.Status.OK).entity(
                        playlistService.findAll().stream()
                                .map(DtoFactory::modelToDto)
                                .collect(Collectors.toList())
                ).build();
    }



    @POST
    @APIResponses( value={
            @APIResponse(responseCode = "200", description = "Sucesso", content =
                    {
                            @Content(examples = {
                                    @ExampleObject(name = "200", description = "Sucesso")},
                                    schema = @Schema(implementation = PlaylistDto.class))
                    }
            ),
            @APIResponse(responseCode = "204", description = "Não encontrado", content =
                    {
                            @Content(examples = {
                                    @ExampleObject(name = "204", description = "Não encontrado")},
                                    schema = @Schema(implementation = String.class))
                    }
            )
    }
    )
    @Path("/adiconarMusica")
    @Operation(summary = "Adiciona uma musica de uma playlist.", description = "Adiciona uma musica de uma playlist.")
    public Response adicionarMusica(@Valid String playlistUid, String musicaUid) {
        try {
            operationService.adicionaMusica(musicaUid, playlistUid);
            return Response.status(Response.Status.OK).build();
        } catch (NoSuchElementException e) {
            return Response.status(204).entity(e.getMessage()).build();
        }

    }



    @POST
    @APIResponses( value={
            @APIResponse(responseCode = "200", description = "Sucesso", content =
                    {
                            @Content(examples = {
                                    @ExampleObject(name = "200", description = "Sucesso")},
                                    schema = @Schema(implementation = PlaylistDto.class))
                    }
            ),
            @APIResponse(responseCode = "204", description = "Não encontrado", content =
                    {
                            @Content(examples = {
                                    @ExampleObject(name = "204", description = "Não encontrado")},
                                    schema = @Schema(implementation = String.class))
                    }
            )
    }
    )
    @Operation(summary = "Remove uma musica de uma playlist.", description = "Remove uma musica de uma playlist.")
    @Path("/removerMusica")
    public Response removerMusica(@Valid String playlistUid, String musicaUid) {
            try {
                operationService.removeMusica(musicaUid, playlistUid);
                return Response.status(Response.Status.OK).build();
            } catch (NoSuchElementException e) {
                return Response.status(204).entity(e.getMessage()).build();
            }    
    }


    @POST
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso")},
                            schema = @Schema(implementation = Playlist.class))
            }
    )
    @Path("/novaPlaylist")
    public Response novaPlaylist(PlaylistPostDto playlistPostDto) {

        return Response.status(Response.Status.OK).entity(operationService.criaPlaylist(playlistPostDto))
                .build();
    }



    @PUT
    @APIResponses( value={
            @APIResponse(responseCode = "200", description = "Sucesso", content =
                    {
                            @Content(examples = {
                                    @ExampleObject(name = "200", description = "Sucesso")},
                                    schema = @Schema(implementation = PlaylistDto.class))
                    }
            ),
            @APIResponse(responseCode = "204", description = "Não encontrado", content =
                    {
                            @Content(examples = {
                                    @ExampleObject(name = "204", description = "Não encontrado")},
                                    schema = @Schema(implementation = String.class))
                    }
            )
    }
    )
    @Operation(summary = "Atualiza os dados de uma playlist.", description = "Atualiza os dados de uma playlist.")
    public Response update(@QueryParam("uid") String uid, PlaylistDto playlistDto) {
        try {
            return Response.status(Response.Status.OK).entity(playlistService.update(playlistDto, uid))
                    .build();
        } catch (NoSuchElementException e) {
            return Response.status(204).entity(e.getMessage()).build();
        }
    }


    @DELETE
    @APIResponses( value={
            @APIResponse(responseCode = "200", description = "Sucesso", content =
                    {
                            @Content(examples = {
                                    @ExampleObject(name = "200", description = "Sucesso")},
                                    schema = @Schema(implementation = PlaylistDto.class))
                    }
            ),
            @APIResponse(responseCode = "204", description = "Não encontrado", content =
                    {
                            @Content(examples = {
                                    @ExampleObject(name = "204", description = "Não encontrado")},
                                    schema = @Schema(implementation = String.class))
                    }
            )
    }
    )
    @Operation(summary = "Deleta uma playlist.", description = "Apaga o registro de uma playlist.")
    public Response delete(@QueryParam("uid") String uid) {
        try {
            operationService.deletarPlaylist(uid);
            return Response.status(Response.Status.OK).entity("Playlist deletada com sucesso").build();
        } catch (NoSuchElementException e) {
            return Response.status(204).entity(e.getMessage()).build();
        }
    }

}

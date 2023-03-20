package org.atividade.resource;

import org.atividade.dto.musica.MusicaPostDto;
import org.atividade.factory.DtoFactory;
import org.atividade.model.Musica;
import org.atividade.service.MusicaService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
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
import java.util.stream.Collectors;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/musica")
@ApplicationScoped
@Tag(name = "Músicas", description = "Endpoints de acesso às músicas")
public class MusicaResource {
    @Inject
    MusicaService musicaService;
    
    @GET
    @Path("/list")
    @Operation(summary = "Retorna todas as músicas")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso")},
                            schema = @Schema(implementation = Musica.class))
            }
    )
    public Response readAll() {
        return Response.status(Response.Status.OK).entity(musicaService.readAll().stream().map(DtoFactory::modelToDto).collect(Collectors.toList())).build();
    }

    @GET
    @Operation(summary = "Obtem uma música por seu UUID")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso"),
                            @ExampleObject(name = "204", description = "Música não encontrada")},
                            schema = @Schema(implementation = Musica.class))
            }
    )
    public Response read(@QueryParam("uid") String uid) {
        try {
            Musica musica = musicaService.read(uid);
            return Response.status(Response.Status.OK).entity(DtoFactory.modelToDto(musica)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
    }

    @POST
    @Operation(summary = "Cria uma música")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso"),
                            @ExampleObject(name = "204", description = "Gênero não encontrado")},
                            schema = @Schema(implementation = Musica.class))
            }
    )
    public Response create(MusicaPostDto musicaDto) {
        try {
            Musica musica = musicaService.create(musicaDto);
            return Response.status(Response.Status.CREATED).entity(DtoFactory.modelToDto(musica)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @DELETE
    @Operation(summary = "Deleta uma música por seu UUID")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso")})

            }
    )
    public Response delete(@QueryParam("uid") String uid) {
        musicaService.delete(uid);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Operation(summary = "Altera uma música a partir do seu UUID")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso"),
                            @ExampleObject(name = "201", description = "Música não encontrado, criada com sucesso")},
                            schema = @Schema(implementation = Musica.class))
            }
    )
    public Response update(@QueryParam("uid") String uid, MusicaPostDto musicaDto) {
        try {
            Musica musica = musicaService.update(musicaDto, uid);
            return Response.status(Response.Status.OK).entity(DtoFactory.modelToDto(musica)).build();
        } catch (NoResultException e) {
            Musica musica = musicaService.create(musicaDto);
            return Response.status(Response.Status.CREATED).entity(DtoFactory.modelToDto(musica)).build();
        }
    }
}

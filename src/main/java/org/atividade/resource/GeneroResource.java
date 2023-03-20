package org.atividade.resource;

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

import org.atividade.dto.GeneroDto;
import org.atividade.model.Genero;
import org.atividade.service.GeneroService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/genero")
@ApplicationScoped
@Tag(name = "Gêneros", description = "Endpoints de acesso aos Gêneros")
public class GeneroResource {

    @Inject
    GeneroService generoService;
    
    @GET
    @Operation(summary = "Retorna todos os gêneros")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso")},
                            schema = @Schema(implementation = Genero.class))
            }
    )
    public Response getAll() {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(generoService.getAll())
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("{uid}")
    @Operation(summary = "Obtem um gênero por seu UUID")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso"),
                            @ExampleObject(name = "204", description = "Gênero não encontrado")},
                            schema = @Schema(implementation = Genero.class))
            }
    )
    public Response getBy(@QueryParam("uid") UUID uid) {
        try {
            Genero genero = generoService.getByUid(uid);

            return Response
                    .status(Response.Status.OK)
                    .entity(genero)
                    .build();
        } catch (NoResultException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Operation(summary = "Cria um gênero")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso")},

                            schema = @Schema(implementation = Genero.class))
            }
    )
    public Response create(GeneroDto generoDto) {
        try {
            var genero = generoService.create(generoDto);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(genero)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Operation(summary = "Deleta um gênero por seu UUID")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso")})

            }
    )
    public Response delete(@QueryParam("uid") UUID uid) {
        try {
            generoService.delete(uid);

            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Operation(summary = "Altera um gênero a partir do seu UUID")
    @APIResponse(responseCode = "200", description = "Sucesso", content =
            {
                    @Content(examples = {
                            @ExampleObject(name = "200", description = "Sucesso"),
                            @ExampleObject(name = "201", description = "Gênero não encontrado, criado com sucesso")},
                            schema = @Schema(implementation = Genero.class))
            }
    )
    public Response update(@QueryParam("uid") UUID uid, GeneroDto generoDto) {
        try {
            Genero genero = generoService
                    .update(generoDto, uid);

            return Response
                    .status(Response.Status.OK)
                    .entity(genero)
                    .build();
        } catch (NoResultException e) {
            Genero genero = generoService.create(generoDto);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(genero)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}

package org.atividade.resource;


import org.atividade.dto.musica.CurtirMusicaPostDto;
import org.atividade.dto.musica.MusicaGetDto;
import org.atividade.dto.pessoa.PessoaGetDto;
import org.atividade.dto.pessoa.PessoaPostDto;
import org.atividade.service.OperationService;
import org.atividade.service.PessoaService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.NoSuchElementException;

@Path("/pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Pessoas", description = "Endpoints de acesso a Pessoas")
public class PessoaResource {

    @Inject
    PessoaService service;

    @Inject
    OperationService operationService;

    @GET
    @Operation(summary = "Retorna todas as pessoas")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso", content =
                    {
                            @Content(examples = {
                                    @ExampleObject(name = "200", description = "Sucesso")},
                                    schema = @Schema(implementation = PessoaGetDto.class))
                    }
            )
    })
    public Response obterTodos() {
        return Response.status(Response.Status.OK).entity(service.obterTodos()).build();
    }

    @GET
    @Path("/{uuid}")
    @Operation(summary = "Obtem uma pessoa por seu UUID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso", content = @Content(examples = @ExampleObject(name = "200", description = "Sucesso"),
                    schema = @Schema(implementation = PessoaGetDto.class))),
            @APIResponse(responseCode = "418", description = "O erro vira ná mensagem")
    })
    public Response obterPorUUID(@PathParam("uuid") String uuid) {
        try {
            return Response.status(Response.Status.OK).entity(service.obterPessoaUuid(uuid)).build();
        } catch (NoSuchElementException e) {
            return Response.status(418).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{uuid}")
    @Operation(summary = "Altera uma pessoa a partir do seu UUID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso", content = @Content(examples = @ExampleObject(name = "200", description = "Sucesso"),
                    schema = @Schema(implementation = PessoaGetDto.class))),
            @APIResponse(responseCode = "418", description = "O erro vira ná mensagem")
    })
    public Response editar(@PathParam("uuid") String uuid, PessoaPostDto dto) {
        try {
            return Response.status(Response.Status.OK).entity(service.editarPessoa(uuid, dto)).build();
        } catch (NoSuchElementException e) {
            return Response.status(418).entity(e.getMessage()).build();
        }
    }

    @POST
    @Operation(summary = "Cria uma pessoa")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso", content = @Content(examples = @ExampleObject(name = "200", description = "Sucesso"),
                    schema = @Schema(implementation = PessoaGetDto.class))),
    })
    public Response criar(PessoaPostDto dto) {
        return Response.status(Response.Status.CREATED).entity(service.criarPessoa(dto)).build();
    }

    @DELETE
    @Path("/{uuid}")
    @Operation(summary = "Deleta uma pessoa por seu UUID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso", content = @Content(examples = @ExampleObject(name = "200", description = "Sucesso"),
                    schema = @Schema(implementation = PessoaGetDto.class))),
            @APIResponse(responseCode = "418", description = "O erro vira ná mensagem")
    })
    public Response deletar(@PathParam("uuid") String uuid) {
        try {
            service.deletar(uuid);
            return Response.status(Response.Status.OK).build();
        } catch (NoSuchElementException e) {
            return Response.status(418).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{uuid}/curtir")
    @Operation(summary = "A Pessoa de UUID informado curte uma Musica a partir de seu UUID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso", content = @Content(examples = @ExampleObject(name = "200", description = "Sucesso"),
                    schema = @Schema(implementation = MusicaGetDto.class))),
    })
    public Response curtirMusica(@PathParam("uuid") String uuid, CurtirMusicaPostDto musicaPostDTO) {
        try {
            return Response.status(Response.Status.CREATED).entity(operationService.curteMusica(uuid, musicaPostDTO.uuidMusica)).build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{uuid}/descurtir")
    @Operation(summary = "A Pessoa de UUID informado descurte uma Musica a partir de seu UUID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso", content = @Content(examples = @ExampleObject(name = "200", description = "Sucesso"),
                    schema = @Schema(implementation = MusicaGetDto.class))),
    })
    public Response criar(@PathParam("uuid") String uuid, CurtirMusicaPostDto musicaPostDTO) {
        try {
            return Response.status(Response.Status.CREATED).entity(operationService.descurteMusica(uuid, musicaPostDTO.uuidMusica)).build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}



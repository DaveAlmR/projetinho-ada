package org.atividade.dto.pessoa;

import javax.validation.constraints.NotBlank;

public class PessoaPostDto {
    @NotBlank(message = "O nome não pode estar vazio")
    public String nome;

}

package br.edu.ifsul.sapucaia.topicos.exercicio1.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private final UUID id = UUID.randomUUID();
    private String nome;
    private String email;
    private String telefone;
    private Date dataDeNascimento;
    private String genero;
    private ArrayList<String> interesses;
}

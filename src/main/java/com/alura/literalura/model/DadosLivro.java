package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Deque;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(@JsonAlias("title") String titulo,
                         @JsonAlias("authors") List<DadosAutor> nomeAutores,
                         @JsonAlias("languages") List<String> idiomaLivro,
                         @JsonAlias("download_count") Integer totalDeDownloads) {
}
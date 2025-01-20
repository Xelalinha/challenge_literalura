package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String autor;
    private String titulo;
    private String idiomaLivro;
    private Integer totalDeDownloads;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> nomeAutores;

    public Livro(DadosLivro dadosLivro) {
        this.autor = !dadosLivro.nomeAutores().isEmpty() ? dadosLivro.nomeAutores().get(0).nomeAutor() : null;
        this.titulo = dadosLivro.titulo();
        this.idiomaLivro = dadosLivro.idiomaLivro().toString();
        this.totalDeDownloads = dadosLivro.totalDeDownloads();
        this.nomeAutores = new ArrayList<>();

        for (DadosAutor dadosAutor : dadosLivro.nomeAutores()) {
            Autor autor = new Autor(dadosAutor);
            autor.setLivro(this);
            this.nomeAutores.add(autor);
        }
    }

    public Livro() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAutor() {
        return autor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.autor = nomeAutor;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public List<Autor> getNomeAutores() {
        return nomeAutores;
    }

    public void setNomeAutores(List<Autor> nomeAutores) {
        this.nomeAutores = nomeAutores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomaLivro() {
        return idiomaLivro;
    }

    public void setIdiomaLivro(String idiomaLivro) {
        this.idiomaLivro = idiomaLivro;
    }

    public Integer getTotalDeDownloads() {
        return totalDeDownloads;
    }

    public void setTotalDeDownloads(Integer totalDeDownloads) {
        this.totalDeDownloads = totalDeDownloads;
    }

    @Override
    public String toString() {
        return "Livro: " + titulo +
                "\nAutores: '" + autor + '\'' +
                "\nIdioma do Livro: " + idiomaLivro +
                "\nTotal de Downloads do Livro: " + totalDeDownloads+
                "\n------------------------";
    }
}

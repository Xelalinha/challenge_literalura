package com.alura.literalura.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeAutor;
    private Integer dataNascimento;
    private Integer dataDeMorte;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "livro_id")
    private Livro livro;

    public Autor(DadosAutor dadosAutor) {
        this.nomeAutor = dadosAutor.nomeAutor();
        this.dataNascimento = dadosAutor.dataNascimento();
        this.dataDeMorte = dadosAutor.dataDeMorte();
    }

    public Autor() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Integer getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Integer dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getDataDeMorte() {
        return dataDeMorte;
    }

    public void setDataDeMorte(Integer dataDeMorte) {
        this.dataDeMorte = dataDeMorte;
    }

    @Override
    public String toString() {
        return "Autor: "+ nomeAutor + '\'' +
                "\nData de Nascimento: " + dataNascimento +
                "\nData da Morte: " + dataDeMorte +
                "\nLivros: " + livro.getTitulo()+
                "\n------------------------";
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}

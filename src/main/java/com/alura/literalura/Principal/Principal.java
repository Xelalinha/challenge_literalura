package com.alura.literalura.Principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados converteDados = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public Principal() {}

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ------------------------Menu--------------------------
                    
                      1 - Buscar Livro pelo Título
                      2 - Listar Livros Registrados
                      3 - Listar Autores Registrados
                      4 - Listar Autores Vivos em um Determinado Ano
                      5 - Listar Livros em um Determinado Idioma
                    
                      0 - Sair
                    ------------------------------------------------------
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorPeriodo();
                    break;
                case 5:
                    listarLivroPorIdioma();
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void listarLivroPorIdioma() {
        System.out.println("Digite o idioma para consulta: ");
        String idiomaSelecionado = leitura.nextLine();

        long quantidadeLivros = livroRepository.findAll().stream()
                .filter(livro -> livro.getIdiomaLivro().contains(idiomaSelecionado))
                .count();

        System.out.println("A quantidade de livros nesse idioma é: " + quantidadeLivros);
    }

    private void listarAutoresVivosPorPeriodo() {
        System.out.println("Digite o ano em que deseja buscar: ");
        int ano = leitura.nextInt();

        List<Autor> autoresVivos = autorRepository.findAutoresVivosPorPeriodo(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor encontrado nesse período.");
        } else {
            autoresVivos.forEach(autor -> System.out.println("Autor Vivo no Ano: "+autor.getNomeAutor()));
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autoresEncontrados = autorRepository.findAll();
        autoresEncontrados.forEach(autor -> {
            System.out.println("Autor: " + autor.getNomeAutor() + "\nData de Nascimento: "
                    + autor.getDataNascimento() + "\nData de Morte: " + autor.getDataDeMorte() + "\n----------------------------------------");


        });
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            livros.forEach(livro -> System.out.println(livro));
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Digite o nome do livro que deseja buscar: ");
        var nomeLivro = leitura.nextLine();
        var json = consumoAPI.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        DadosResults dadosResults = converteDados.obterDados(json, DadosResults.class);


        for (DadosLivro dadosLivro : dadosResults.livros()) {
            Livro livro = new Livro(dadosLivro);
            for (DadosAutor dadosAutor : dadosLivro.nomeAutores()) {
                Autor autor = new Autor(dadosAutor);
                if (dadosAutor.dataNascimento() == null) {
                    autor.setDataNascimento(0);
                }
                if (dadosAutor.dataDeMorte() == null) {
                    autor.setDataDeMorte(0);
                }
                livro.getNomeAutores().add(autor);
            }
            livroRepository.save(livro);
        }
    }
}

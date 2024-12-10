package com.example.library;

public class Livro {
    private String titulo;
    private String autor;
    private String editora;
    private String genero;
    private int anoLancamento;
    private int quantidade;
    private String usuarioEmprestado;
    private double preco; // Novo atributo para o preço do livro

    public Livro(String titulo, String autor, String editora, String genero, int anoLancamento, int quantidade) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.quantidade = quantidade;
        this.usuarioEmprestado = null; // Nenhum empréstimo inicial
        this.preco = 0.0; // Inicializa o preço com valor padrão
    }

    // Sobrecarga do construtor para incluir o preço
    public Livro(String titulo, String autor, String editora, String genero, int anoLancamento, int quantidade, double preco) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.quantidade = quantidade;
        this.usuarioEmprestado = null; // Nenhum empréstimo inicial
        this.preco = preco; // Inicializa o preço informado
    }

    public boolean isDisponivel() {
        return quantidade > 0;
    }

    public void emprestar(String usuario) {
        if (quantidade > 0) {
            this.quantidade--;
            this.usuarioEmprestado = usuario;
        }
    }

    public void devolver() {
        if (usuarioEmprestado != null) {
            this.quantidade++;
            this.usuarioEmprestado = null;
        }
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditora() {
        return editora;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getUsuarioEmprestado() {
        return usuarioEmprestado;
    }

    public double getPreco() { // Getter para o preço
        return preco;
    }

    public void setPreco(double preco) { // Setter para o preço
        this.preco = preco;
    }
}
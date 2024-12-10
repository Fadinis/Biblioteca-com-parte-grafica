package com.example.library;

public class Aluno {
    private String nome;
    private String matricula;
    private String curso;

    // Construtor
    public Aluno(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " | Matrícula: " + matricula + " | Curso: " + curso;
    }
}
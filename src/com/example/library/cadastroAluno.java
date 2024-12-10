package com.example.library;

import com.example.library.blibioteca_forms.EditorLivroBiblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cadastroAluno {
    private final JFrame cadastroFrame;
    private final JTextField nomeField;
    private final JTextField raField;
    private final EditorLivroBiblioteca editorLivroBiblioteca;

    // Construtor que recebe a referência de EditorLivroBiblioteca
    public cadastroAluno(EditorLivroBiblioteca editor) {
        this.editorLivroBiblioteca = editor;

        cadastroFrame = new JFrame("Cadastro de Aluno");
        cadastroFrame.setSize(300, 200);
        cadastroFrame.setLayout(new GridLayout(3, 2));

        nomeField = new JTextField();
        raField = new JTextField();

        cadastroFrame.add(new JLabel("Nome:"));
        cadastroFrame.add(nomeField);
        cadastroFrame.add(new JLabel("RA:"));
        cadastroFrame.add(raField);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String ra = raField.getText();
                if (!nome.isEmpty() && !ra.isEmpty()) {
                    Aluno novoAluno = new Aluno(nome, ra);
                    editorLivroBiblioteca.adicionarAluno(novoAluno); // Atualiza a lista de alunos na janela principal
                    cadastroFrame.dispose(); // Fecha a janela de cadastro sem afetar o aplicativo
                } else {
                    JOptionPane.showMessageDialog(cadastroFrame, "Por favor, preencha todos os campos.");
                }
            }
        });

        cadastroFrame.add(salvarButton);
        cadastroFrame.setVisible(true);
    }
}
package com.example.library.blibioteca_forms;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

import com.example.library.*;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class EditorLivroBiblioteca {
    private final JFrame frame;
    private final JList<String> livrosList;
    private final JList<String> emprestimosList;
    private final DefaultListModel<String> livrosModel;
    private final DefaultListModel<String> emprestimosModel;
    private final List<Livro> livros;
    private final List<Emprestimo> emprestimos;
    private final JTextField pesquisaField;

    private final List<Aluno> alunos; // Lista de alunos
    private final DefaultListModel<String> alunosModel; // Modelo de lista para exibir alunos na interface
    private final JList<String> alunosList; // JList para exibir os alunos

    public EditorLivroBiblioteca() {
        livros = new ArrayList<>();
        emprestimos = new ArrayList<>();
        alunos = new ArrayList<>(); // Inicializa a lista de alunos
        livrosModel = new DefaultListModel<>();
        emprestimosModel = new DefaultListModel<>();
        alunosModel = new DefaultListModel<>(); // Inicializa o modelo de lista de alunos

        // Inicializa a interface
        frame = new JFrame("Biblioteca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1)); // Ajuste para acomodar a lista de alunos

        // Lista de livros
        livrosList = new JList<>(livrosModel);
        JScrollPane scrollLivros = new JScrollPane(livrosList);

        // Lista de empréstimos
        emprestimosList = new JList<>(emprestimosModel);
        JScrollPane scrollEmprestimos = new JScrollPane(emprestimosList);

        // Lista de alunos
        alunosList = new JList<>(alunosModel); // Cria a JList para exibir os alunos
        JScrollPane scrollAlunos = new JScrollPane(alunosList); // Cria o JScrollPane para os alunos

        // Campo de pesquisa
        pesquisaField = new JTextField(20);
        JButton pesquisarButton = new JButton("Pesquisar");
        pesquisarButton.addActionListener(e -> pesquisarLivro());

        JPanel panelPesquisa = new JPanel();
        panelPesquisa.add(new JLabel("Pesquisar Livro:"));
        panelPesquisa.add(pesquisaField);
        panelPesquisa.add(pesquisarButton);

        frame.add(panelPesquisa, BorderLayout.NORTH);
        frame.add(scrollLivros);
        frame.add(scrollEmprestimos);
        frame.add(scrollAlunos); // Adiciona a lista de alunos

        // Botoes de acao
        JButton emprestarButton = new JButton("Emprestar");
        emprestarButton.addActionListener(e -> emprestarLivro());
        JButton devolverButton = new JButton("Devolver");
        devolverButton.addActionListener(e -> devolverLivro());

        // Botão para Cadastro de Aluno
        JButton cadastrarAlunoButton = new JButton("Cadastrar Aluno");
        cadastrarAlunoButton.addActionListener(e -> {
            // Passa a referência da janela principal para o CadastroAluno
            new cadastroAluno(this);
        });

        JPanel panel = new JPanel();
        panel.add(emprestarButton);
        panel.add(devolverButton);
        panel.add(cadastrarAlunoButton); // Adiciona o botão para cadastro de aluno

        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Metodo para adicionar um aluno à lista e atualizar a interface
    public void adicionarAluno(Aluno aluno) {
        // Adiciona o aluno à lista interna
        alunos.add(aluno);

        // Atualiza o modelo de lista de alunos
        alunosModel.addElement(aluno.toString()); // Exibe o nome e RA do aluno
    }

    private void emprestarLivro() {
        int selectedIndex = livrosList.getSelectedIndex();
        if (selectedIndex != -1) {
            Livro livro = livros.get(selectedIndex);
            if (livro.getQuantidade() > 0) { // Verifica se há unidades disponíveis
                // Formulário para entrada de dados
                JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
                JTextField nomeField = new JTextField();
                JTextField raField = new JTextField();
                panel.add(new JLabel("Nome do Aluno:"));
                panel.add(nomeField);
                panel.add(new JLabel("Número do RA:"));
                panel.add(raField);

                int result = JOptionPane.showConfirmDialog(frame, panel,
                        "Dados do Empréstimo", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String nome = nomeField.getText().trim();
                    String ra = raField.getText().trim();

                    if (!nome.isEmpty() && !ra.isEmpty()) {
                        livro.setQuantidade(livro.getQuantidade() - 1); // Decrementa uma unidade
                        emprestimos.add(new Emprestimo(livro, nome + " (RA: " + ra + ")",
                                new java.util.Date(), new java.util.Date()));
                        atualizarListas();
                        JOptionPane.showMessageDialog(frame, "Livro emprestado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Todas as unidades deste livro já foram emprestadas.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Selecione um livro para emprestar.");
        }
    }

    private void devolverLivro() {
        int selectedIndex = emprestimosList.getSelectedIndex();
        if (selectedIndex != -1) {
            Emprestimo emprestimo = emprestimos.get(selectedIndex);
            emprestimo.getLivro().devolver();
            emprestimos.remove(selectedIndex);
            atualizarListas();
        }
    }

    private void atualizarListas() {
        livrosModel.clear();
        emprestimosModel.clear();

        // Atualiza lista de livros
        for (Livro livro : livros) {
            String disponibilidade = livro.isDisponivel() ? "Disponível" : "Emprestado por " + livro.getUsuarioEmprestado();
            livrosModel.addElement(
                    livro.getTitulo() + " - " + livro.getAutor() +
                            " - " + livro.getEditora() +
                            " - " + livro.getGenero() +
                            " - " + livro.getAnoLancamento() +
                            " - R$ " + livro.getPreco() + // Exibe o preço
                            " - " + disponibilidade +
                            " - " + livro.getQuantidade() + " unidades"
            );
        }

        // Atualiza lista de empréstimos
        for (Emprestimo emprestimo : emprestimos) {
            emprestimosModel.addElement(
                    emprestimo.getLivro().getTitulo() + " - " +
                            emprestimo.getUsuario()
            );
        }
    }

    private void pesquisarLivro() {
        String pesquisa = pesquisaField.getText();
        if (!pesquisa.isEmpty()) {
            String apiKey = Config.getGoogleApiKey(); // Obtém a chave da API do Google Books
            Livro livro = buscarLivroNaAPI(pesquisa, apiKey);

            if (livro != null) {
                try {
                    String inputQuantidade = JOptionPane.showInputDialog(frame,
                            "Livro encontrado:\n" +
                                    "Título: " + livro.getTitulo() + "\n" +
                                    "Autor: " + livro.getAutor() + "\n" +
                                    "Editora: " + livro.getEditora() + "\n" +
                                    "Gênero: " + livro.getGenero() + "\n" +
                                    "Ano de Lançamento: " + livro.getAnoLancamento() + "\n" +
                                    "Preço: R$ " + livro.getPreco() + "\n\n" + // Exibe o preço
                                    "Digite a quantidade para adicionar:");
                    int quantidade = Integer.parseInt(inputQuantidade);
                    livro.setQuantidade(quantidade);

                    livros.add(livro); // Adiciona o livro à lista
                    atualizarListas(); // Atualiza a exibição das listas
                    JOptionPane.showMessageDialog(frame, "Livro adicionado com sucesso!");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Quantidade inválida.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Livro não encontrado.");
            }
        }
    }

    public Livro buscarLivroNaAPI(String pesquisa, String apiKey) {
        try {
            String url = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(pesquisa, "UTF-8") + "&key=" + apiKey;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            if (jsonResponse.getInt("totalItems") > 0) {
                JSONObject volumeInfo = jsonResponse.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");

                String titulo = volumeInfo.getString("title");
                String autor = volumeInfo.getJSONArray("authors").getString(0);
                String editora = volumeInfo.optString("publisher", "Desconhecida");
                String genero = volumeInfo.optJSONArray("categories") != null ?
                        volumeInfo.getJSONArray("categories").getString(0) : "Não especificado";
                int anoLancamento = 0;
                double preco = 0.0; // Valor padrão

                if (volumeInfo.has("publishedDate")) {
                    String publishedDate = volumeInfo.getString("publishedDate");
                    if (publishedDate.length() >= 4) {
                        anoLancamento = Integer.parseInt(publishedDate.substring(0, 4));
                    }
                }

                // Busca o preço na seção saleInfo
                if (jsonResponse.getJSONArray("items").getJSONObject(0).has("saleInfo")) {
                    JSONObject saleInfo = jsonResponse.getJSONArray("items").getJSONObject(0).getJSONObject("saleInfo");
                    if (saleInfo.has("retailPrice")) {
                        preco = saleInfo.getJSONObject("retailPrice").getDouble("amount");
                    }
                }

                return new Livro(titulo, autor, editora, genero, anoLancamento, 0, preco);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditorLivroBiblioteca::new);
    }
}
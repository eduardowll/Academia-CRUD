package org.app.academia.controller;

import org.app.academia.model.Aluno;
import org.app.academia.model.AlunoDAO;
import org.app.academia.model.Treino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import java.util.List;
import java.util.Optional;

public class PersonalController {

    // Campos de Cadastro
    @FXML
    private TextField nomeCadastroField;
    @FXML
    private TextField idadeCadastroField;
    @FXML
    private TextField exerciciosCadastroField;
    @FXML
    private TextField focoCadastroField;
    @FXML
    private TextField tipoCadastroField;
    @FXML
    private TextField duracaoCadastroField;

    // Campos de Edição
    @FXML
    private Label nomeEdicaoField;
    @FXML
    private TextField idadeEdicaoField;
    @FXML
    private TextField exerciciosEdicaoField;
    @FXML
    private TextField focoEdicaoField;
    @FXML
    private TextField tipoEdicaoField;
    @FXML
    private TextField duracaoEdicaoField;
    @FXML
    private TextField nomeFiltroField;
    @FXML
    private TextField focoFiltroField;
    @FXML
    private TextField tipoFiltroField;

    // Labels de Feedback
    @FXML
    private Label feedbackCadastroLabel;
    @FXML
    private Label feedbackEdicaoLabel;

    // Botões e ListView
    @FXML
    private Button salvarCadastroButton;
    @FXML
    private Button alterarButton;
    @FXML
    private Button excluirButton;
    @FXML
    private Button filtrarButton;
    @FXML
    private Button voltarButton;
    @FXML
    private ListView<Aluno> alunoListView;

    private AcademiaController academiaController = new AcademiaController();
    private Aluno alunoEmEdicao = null;

    private AlunoDAO alunoDAO = new AlunoDAO();

    public void initialize() {
        List<Aluno> alunos = alunoDAO.buscarTodosAlunos();
        ObservableList<Aluno> observableAlunos = FXCollections.observableArrayList(alunos);

        alunoListView.setItems(observableAlunos);
        alunoListView.setCellFactory(list -> new TextFieldListCell<>(new StringConverter<Aluno>() {
            @Override
            public String toString(Aluno aluno) {
                return aluno.getDadosFormatados();
            }
            @Override
            public Aluno fromString(String string) {
                return null;
            }

        }));

        alunoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                alunoEmEdicao = newValue;
                exibirDadosAluno();
            }
        });
    }

    @FXML
    public void voltarParaTelaPrincipal() {
        academiaController.voltarParaTelaPrincipal(voltarButton);
    }

    public void cadastrarAluno() {
        try {
            String nome = nomeCadastroField.getText();
            int idade = Integer.parseInt(idadeCadastroField.getText());
            String exercicios = exerciciosCadastroField.getText();
            String foco = focoCadastroField.getText();
            String tipo = tipoCadastroField.getText();
            int duracao = Integer.parseInt(duracaoCadastroField.getText());

            Aluno novoAluno = new Aluno();
            Treino novoTreino = new Treino();

            novoAluno.setNome(nome);
            novoAluno.setIdade(idade);
            novoAluno.setTreino(novoTreino);

            novoTreino.setExercicio(exercicios);
            novoTreino.setFoco(foco);
            novoTreino.setTipo(tipo);
            novoTreino.setDuracao(duracao);

            alunoDAO.cadastrarAluno(novoAluno, novoTreino);

            ObservableList<Aluno> alunosAtuais = alunoListView.getItems();

            alunosAtuais.add(novoAluno);
            alunoListView.setItems(alunosAtuais);

            feedbackCadastroLabel.setText("Aluno cadastrado com sucesso!");
            feedbackCadastroLabel.setStyle("-fx-text-fill: blue;");

            limparCamposCadastro();
        } catch (NumberFormatException e) {
            feedbackCadastroLabel.setText("Erro: Preencha todos os campos corretamente.");
            feedbackCadastroLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void alterarAluno() {
        if (alunoEmEdicao != null) {
            String nome = nomeEdicaoField.getText();
            int idade = Integer.parseInt(idadeEdicaoField.getText());
            String exercicios = exerciciosEdicaoField.getText();
            String foco = focoEdicaoField.getText();
            String tipo = tipoEdicaoField.getText();
            int duracao = Integer.parseInt(duracaoEdicaoField.getText());

            alunoEmEdicao.setNome(nome);
            alunoEmEdicao.setIdade(idade);
            Treino treino = alunoEmEdicao.getTreino();
            if (treino != null) {
                treino.setExercicio(exercicios);
                treino.setFoco(foco);
                treino.setTipo(tipo);
                treino.setDuracao(duracao);
            }

            alunoDAO.atualizarAluno(alunoEmEdicao);

            alunoListView.refresh();
            limparCamposEdicao();

            feedbackEdicaoLabel.setText("Aluno alterado com sucesso!");
            feedbackEdicaoLabel.setStyle("-fx-text-fill: blue;");
        } else {
            feedbackEdicaoLabel.setText("Erro: Nenhum aluno selecionado para alterar.");
            feedbackEdicaoLabel.setStyle("-fx-text-fill: red;");
        }
    }


    @FXML
    public void excluirAluno() {
        Aluno alunoSelecionado = alunoListView.getSelectionModel().getSelectedItem();

        if (alunoSelecionado != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação de exclusão");
            confirmacao.setHeaderText("Deseja realmente excluir o aluno selecionado?");
            confirmacao.setContentText("Aluno: " + alunoSelecionado.getNome());

            Optional<ButtonType> resultado = confirmacao.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                alunoDAO.excluirAluno(alunoSelecionado.getId());
                alunoListView.getItems().remove(alunoSelecionado);

                feedbackEdicaoLabel.setText("Aluno e treino excluídos com sucesso!");
                feedbackEdicaoLabel.setStyle("-fx-text-fill: blue;");
            }
        } else {
            feedbackEdicaoLabel.setText("Erro: Nenhum aluno selecionado para excluir.");
            feedbackEdicaoLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void exibirDadosAluno() {
        if (alunoEmEdicao != null) {
            nomeEdicaoField.setText(alunoEmEdicao.getNome());
            idadeEdicaoField.setText(String.valueOf(alunoEmEdicao.getIdade()));
            Treino treino = alunoEmEdicao.getTreino();
            if (treino != null) {
                exerciciosEdicaoField.setText(treino.getExercicio());
                focoEdicaoField.setText(treino.getFoco());
                tipoEdicaoField.setText(treino.getTipo());
                duracaoEdicaoField.setText(String.valueOf(treino.getDuracao()));
            }
        }
    }

    private void limparCamposCadastro() {
        nomeCadastroField.clear();
        idadeCadastroField.clear();
        exerciciosCadastroField.clear();
        focoCadastroField.clear();
        tipoCadastroField.clear();
        duracaoCadastroField.clear();
    }

    private void limparCamposEdicao() {
        nomeEdicaoField.setText("");
        idadeEdicaoField.clear();
        exerciciosEdicaoField.clear();
        focoEdicaoField.clear();
        tipoEdicaoField.clear();
        duracaoEdicaoField.clear();
    }

}

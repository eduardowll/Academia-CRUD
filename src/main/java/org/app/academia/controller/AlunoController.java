package org.app.academia.controller;

import org.app.academia.model.Aluno;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.app.academia.model.AlunoDAO;
import org.app.academia.model.Treino;

import java.util.Optional;

public class AlunoController {
    @FXML
    private TextField nomePesquisaField;

    @FXML
    private Label feedbackLabel;
    @FXML
    private Label idadeLabel;
    @FXML
    private Label exercicioLabel;
    @FXML
    private Label focoLabel;
    @FXML
    private Label tipoLabel;
    @FXML
    private Label duracaoLabel;

    @FXML
    private Button pesquisarButton;
    @FXML
    private Button sairButton;
    @FXML
    private Button voltarButton;

    private AcademiaController academiaController = new AcademiaController();

    @FXML
    public void voltarParaTelaPrincipal() {
        academiaController.voltarParaTelaPrincipal(voltarButton);
    }

    @FXML
    public void pesquisarAluno() {
        // Limpa os campos antes de buscar
        limparLabels();

        String nomePesquisado = nomePesquisaField.getText().trim();

        if (nomePesquisado.isEmpty()) {
            feedbackLabel.setText("Por favor, insira o nome para pesquisa.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        AlunoDAO alunoDAO = new AlunoDAO();
        ObservableList<Aluno> alunos = alunoDAO.buscarTodosAlunos();

        Optional<Aluno> alunoEncontrado = alunos.stream()
                .filter(aluno -> aluno.getNome().equalsIgnoreCase(nomePesquisado))
                .findFirst();

        if (alunoEncontrado.isPresent()) {
            Aluno aluno = alunoEncontrado.get();
            exibirDadosAluno(aluno);
            feedbackLabel.setText("Aluno encontrado!");
            feedbackLabel.setStyle("-fx-text-fill: blue;");
        } else {
            feedbackLabel.setText("Aluno não encontrado.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void exibirDadosAluno(Aluno aluno) {
        idadeLabel.setText("Idade: " + aluno.getIdade());

        Treino treino = aluno.getTreino();
        if (treino != null) {
            exercicioLabel.setText("Exercício: " + treino.getExercicio());
            focoLabel.setText("Foco: " + treino.getFoco());
            tipoLabel.setText("Tipo: " + treino.getTipo());
            duracaoLabel.setText("Duração: " + treino.getDuracao() + " dias");
        } else {
            exercicioLabel.setText("Exercício: Não informado");
            focoLabel.setText("Foco: Não informado");
            tipoLabel.setText("Tipo: Não informado");
            duracaoLabel.setText("Duração: Não informada");
        }
    }

    @FXML
    private void limparLabels() {
        feedbackLabel.setText("");
        idadeLabel.setText("");
        exercicioLabel.setText("");
        focoLabel.setText("");
        tipoLabel.setText("");
        duracaoLabel.setText("");
    }


}

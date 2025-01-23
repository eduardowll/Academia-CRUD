package org.app.academia.controller;

import org.app.academia.model.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    private ObservableList<Aluno> todosAlunos;
    private AcademiaController academiaController = new AcademiaController();

    public void initialize() {
        todosAlunos = FXCollections.observableArrayList();
        resetInterface();
    }

    @FXML
    public void voltarParaTelaPrincipal() {
        academiaController.voltarParaTelaPrincipal(voltarButton);
    }

    @FXML
    public void pesquisarAluno() {
        String nomeFiltro = nomePesquisaField.getText().trim().toLowerCase();

        if (!nomeFiltro.isEmpty()) {
            Aluno alunoEncontrado = todosAlunos.stream()
                    .filter(aluno -> aluno.getNome().toLowerCase().equals(nomeFiltro))
                    .findFirst()
                    .orElse(null);

            if (alunoEncontrado != null) {
                // Exibe os dados do aluno e treino
                feedbackLabel.setText(""); // Remove mensagens gerais
                idadeLabel.setText("Idade: " + alunoEncontrado.getIdade());
                exercicioLabel.setText("Exercício: " + alunoEncontrado.getTreino().getExercicio());
                focoLabel.setText("Foco: " + alunoEncontrado.getTreino().getFoco());
                tipoLabel.setText("Tipo: " + alunoEncontrado.getTreino().getTipo());
                duracaoLabel.setText("Duração: " + alunoEncontrado.getTreino().getDuracao() + " dias");

            } else {
                // Aluno não encontrado
                resetInterface();
                feedbackLabel.setText("Aluno não encontrado.");
            }
        } else {
            // Se o campo estiver vazio
            resetInterface();
            feedbackLabel.setText("Digite o nome do aluno.");
        }
    }

    private void resetInterface() {
        idadeLabel.setText("");
        exercicioLabel.setText("");
        focoLabel.setText("");
        tipoLabel.setText("");
        duracaoLabel.setText("");
    }

    public void sair() {
        resetInterface();
        nomePesquisaField.clear(); //
    }

}

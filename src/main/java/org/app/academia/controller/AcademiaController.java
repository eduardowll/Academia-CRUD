package org.app.academia.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class AcademiaController {

    @FXML
    private Button alunoButton;

    @FXML
    private Button personalButton;

    public void voltarParaTelaPrincipal(Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/app/academia/fxml/TelaMain.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) button.getScene().getWindow(); // Obtém a janela atual
            stage.setScene(new Scene(root)); // Define a nova cena
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void irParaTelaAluno() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/app/academia/fxml/TelaAluno.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) alunoButton.getScene().getWindow(); // Obtém a janela atual
            stage.setScene(new Scene(root)); // Define a nova cena
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void irParaTelaPersonal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/app/academia/fxml/TelaPersonal.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) personalButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

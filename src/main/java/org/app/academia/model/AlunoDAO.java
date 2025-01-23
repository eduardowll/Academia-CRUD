package org.app.academia.model;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AlunoDAO {

    public void cadastrarAluno(Aluno aluno, Treino treino) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlAluno = "INSERT INTO alunos (nome, idade) VALUES (?, ?) RETURNING alunos_id";
            try (PreparedStatement stmtAluno = conn.prepareStatement(sqlAluno)) {
                stmtAluno.setString(1, aluno.getNome());
                stmtAluno.setInt(2, aluno.getIdade());

                ResultSet rsAluno = stmtAluno.executeQuery();
                if (rsAluno.next()) {
                    int alunoId = rsAluno.getInt("alunos_id");

                    String sqlTreino = "INSERT INTO treino (exercicios, foco, tipo, duracao, alunos_id) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement stmtTreino = conn.prepareStatement(sqlTreino)) {
                        stmtTreino.setString(1, treino.getExercicio());
                        stmtTreino.setString(2, treino.getFoco());
                        stmtTreino.setString(3, treino.getTipo());
                        stmtTreino.setInt(4, treino.getDuracao());
                        stmtTreino.setInt(5, alunoId);  // ID do aluno associado

                        stmtTreino.executeUpdate();
                        conn.commit();
                        System.out.println("Aluno e treino cadastrados com sucesso!");
                    } catch (SQLException e) {
                        conn.rollback();
                        e.printStackTrace();
                        System.out.println("Erro ao cadastrar treino: " + e.getMessage());
                    }
                } else {
                    System.out.println("Erro ao cadastrar aluno, treino não cadastrado.");
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar aluno e treino.");
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void atualizarAluno(Aluno aluno) {
        String updateAlunoSQL = "UPDATE alunos SET nome = ?, idade = ? WHERE alunos_id = ?";
        String updateTreinoSQL = "UPDATE treino SET exercicios = ?, foco = ?, tipo = ?, duracao = ? WHERE alunos_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtAluno = conn.prepareStatement(updateAlunoSQL);
             PreparedStatement stmtTreino = conn.prepareStatement(updateTreinoSQL)) {

            stmtAluno.setString(1, aluno.getNome());
            stmtAluno.setInt(2, aluno.getIdade());
            stmtAluno.setInt(3, aluno.getId());
            stmtAluno.executeUpdate();

            Treino treino = aluno.getTreino();
            if (treino != null) {
                stmtTreino.setString(1, treino.getExercicio());
                stmtTreino.setString(2, treino.getFoco());
                stmtTreino.setString(3, treino.getTipo());
                stmtTreino.setInt(4, treino.getDuracao());
                stmtTreino.setInt(5, aluno.getId());
                stmtTreino.executeUpdate();
            }

            System.out.println("Aluno e treino atualizados com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar os dados do aluno no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void excluirAluno(int alunoId) {
        String deleteTreinoSQL = "DELETE FROM treino WHERE alunos_id = ?";
        String deleteAlunoSQL = "DELETE FROM alunos WHERE alunos_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtTreino = conn.prepareStatement(deleteTreinoSQL);
             PreparedStatement stmtAluno = conn.prepareStatement(deleteAlunoSQL)) {

            stmtTreino.setInt(1, alunoId);
            stmtTreino.executeUpdate();

            stmtAluno.setInt(1, alunoId);
            stmtAluno.executeUpdate();

            System.out.println("Aluno e treino excluídos com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir o aluno e o treino no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ObservableList<Aluno> buscarTodosAlunos() {
        ObservableList<Aluno> alunosList = FXCollections.observableArrayList();

        String sql = "SELECT a.alunos_id, a.nome, a.idade, t.exercicios, t.foco, t.tipo, t.duracao " +
                "FROM alunos a " +
                "LEFT JOIN treino t ON a.alunos_id = t.alunos_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("alunos_id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String exercicios = rs.getString("exercicios");
                String foco = rs.getString("foco");
                String tipo = rs.getString("tipo");
                int duracao = rs.getInt("duracao");

                Aluno aluno = new Aluno();
                aluno.setId(id);
                aluno.setNome(nome);
                aluno.setIdade(idade);

                Treino treino = new Treino();
                treino.setExercicio(exercicios);
                treino.setFoco(foco);
                treino.setTipo(tipo);
                treino.setDuracao(duracao);
                aluno.setTreino(treino);

                alunosList.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar alunos: " + e.getMessage());
        }

        return alunosList;
    }
}

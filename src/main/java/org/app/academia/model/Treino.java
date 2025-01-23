package org.app.academia.model;

public class Treino {
    private int id;
    private String exercicios;
    private String foco;
    private String tipo;
    private int duracao; // em dias
    private Aluno aluno;

    public Treino() {
    }

    public Treino(String exercicios, String foco, String tipo, int duracao, Aluno aluno) {
        this.id = id;
        this.exercicios = exercicios;
        this.foco = foco;
        this.tipo = tipo;
        this.duracao = duracao;
        this.aluno = aluno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExercicio() {
        return exercicios;
    }

    public void setExercicio(String exercicio) {
        this.exercicios = exercicio;
    }

    public String getFoco() {
        return foco;
    }

    public void setFoco(String foco) {
        this.foco = foco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

}

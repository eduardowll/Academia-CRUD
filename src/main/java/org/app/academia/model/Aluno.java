package org.app.academia.model;

public class Aluno {
    private int id;
    private String nome;
    private int idade;
    private Treino treino;


    public Aluno(){

    }

    public Aluno(String nome, int idade, int id){
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public String getDadosFormatados() {
        Treino treino = getTreino();

        return String.format(
                "Nome: %s | Foco: %s | Tipo: %s | Duração: %d dias",
                getNome(), treino.getFoco(), treino.getTipo(), treino.getDuracao()
        );
    }
}


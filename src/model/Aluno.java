/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Avell
 */
public class Aluno extends Pessoa {

    private String curso;
    public ArrayList questoesRespondidas = new ArrayList();
    public ArrayList questoesPendentes = new ArrayList();
    private int idAluno;
    private boolean pro;

    public Aluno(int id, String nome) {
        super(id, nome);
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public boolean isPro() {
        return pro;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
    }
}

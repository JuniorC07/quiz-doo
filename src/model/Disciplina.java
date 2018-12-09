/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Avell
 */
public class Disciplina {

    private String id;
    private String desc;
    private int periodo;

    private Professor professor;
    public ArrayList<Aluno> alunos = new ArrayList();
    public ArrayList<Questao> questoes = new ArrayList();

    public Disciplina(String id, String desc, int periodo, Professor professor) {
        this.id = null;
        this.desc = desc;
        this.periodo = periodo;
        this.professor = professor;
    }

    public Disciplina() {
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void mostrarDisciplina() {
        Iterator<Aluno> percorre = alunos.iterator();
        System.out.println("------------------------------------------");
        System.out.println("Disciplina: " + this.getDesc());
        System.out.println("------------------------------------------");
        System.out.println("Professor: " + this.getProfessor().getNome() + " Formacao:" + this.getProfessor().getFormacao());
        System.out.println("------------------------------------------");
        System.out.println("Alunos:");
        while (percorre.hasNext()) {
            Aluno tmp = percorre.next();
            System.out.println("------------------------------------------");
            System.out.println("Nome: " + tmp.getNome() + " Curso:" + tmp.getCurso());
            System.out.println("------------------------------------------");
        }
    }

}

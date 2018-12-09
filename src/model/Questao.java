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
public class Questao {

    private String id;
    private String desc;
    private Boolean acertou;
    private String id_resposta;
    private int id_disciplina;

    public ArrayList<Alternativa> alternativas = new ArrayList();

    public Questao() {
    }

    public Questao(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAcertou() {
        return acertou;
    }

    public void setAcertou(Boolean acertou) {
        this.acertou = acertou;
    }

    public int getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(int id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getId_resposta() {
        return id_resposta;
    }

    public void setId_resposta(String id_resposta) {
        this.id_resposta = id_resposta;
    }

}

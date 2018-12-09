/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Avell
 */
public class Alternativa {
    private String id;
    private String desc;
    private Boolean correta;

    public Alternativa(String id, String desc, Boolean correta) {
        this.id = null;
        this.desc = desc;
        this.correta = correta;
    }

    public Alternativa() {
    }
    

    public Boolean getCorreta() {
        return correta;
    }

    public void setCorreta(Boolean correta) {
        this.correta = correta;
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
    
    
    
}

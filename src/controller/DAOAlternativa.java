/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Alternativa;
import model.Conexao;
import model.Pessoa;

/**
 *
 * @author Avell
 */
public class DAOAlternativa {

    private Connection conectar;
    private Statement consulta;
    private ResultSet retorno;

    private void conectar() throws InstantiationException, IllegalAccessException {
        try {
            conectar = Conexao.abrirConexao();
            consulta = conectar.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void desconectar() {
        try {
            consulta.close();
            conectar.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Alternativa getAlternativa(String idp) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alternativa a = new Alternativa();
        int aux = 0;
        String sql = "SELECT * FROM dbquiz.alternativa where id = " + idp + ";";
        retorno = consulta.executeQuery(sql);

        while (retorno.next()) {
            a.setId(retorno.getString("id"));
            a.setDesc(retorno.getString("desc"));
            aux = retorno.getInt("correta");
            if (aux == 1) {
                a.setCorreta(true);
            } else {
                a.setCorreta(false);
            }
        }
        this.desconectar();
        return a;
    }

    public ArrayList<Alternativa> getAlternativas(String idq) throws SQLException {
        ArrayList<Alternativa> alternativas = new ArrayList();
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        int aux = 0;
        String sql = "SELECT * FROM dbquiz.alternativa where id_questao = " + idq + ";";
        retorno = consulta.executeQuery(sql);

        while (retorno.next()) {
            Alternativa a = new Alternativa();
            a.setId(retorno.getString("id"));
            a.setDesc(retorno.getString("desc"));
            aux = retorno.getInt("correta");
            if (aux == 1) {
                a.setCorreta(true);
            } else {
                a.setCorreta(false);
            }
            alternativas.add(a);
        }
        this.desconectar();
        return alternativas;
    }
}

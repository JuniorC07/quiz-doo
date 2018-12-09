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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Aluno;
import model.Conexao;
import model.Pessoa;

/**
 *
 * @author Avell
 */
public class DAOAluno {

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

    public Aluno getAluno(String id) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "SELECT * FROM dbquiz.aluno where id = " + id + ";";

        retorno = consulta.executeQuery(sql);
        Aluno a = new Aluno(1, "a");
        while (retorno.next()) {
            DAOPessoa p = new DAOPessoa();
            Pessoa pes = new Pessoa();
            pes = p.getPessoa(retorno.getString("id_pessoa"));
            a.setNome(pes.getNome());
            a.setCurso(retorno.getString("curso"));
            a.setId(retorno.getString("id"));
        }
        this.desconectar();
        return a;
    }
}

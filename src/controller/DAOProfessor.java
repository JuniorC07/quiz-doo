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
import model.Aluno;
import model.Conexao;
import model.Disciplina;
import model.Pessoa;
import model.Professor;

/**
 *
 * @author Avell
 */
public class DAOProfessor {

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

    public Professor getProfessor(String id) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "SELECT * FROM dbquiz.professor where id = " + id + ";";
        retorno = consulta.executeQuery(sql);
        Professor pr = new Professor(1, "a");
        while (retorno.next()) {
            DAOPessoa p = new DAOPessoa();
            pr.setFormacao(retorno.getString("formacao"));
            Pessoa pes = p.getPessoa(retorno.getString("id_pessoa"));
            pr.setNome(pes.getNome());
            pr.setId(retorno.getString("id"));
        }
        this.desconectar();
        return pr;
    }

    public ArrayList<Disciplina> getDisciplinas(Professor p) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Disciplina> d = new ArrayList();
        String sql = "SELECT * FROM dbquiz.disciplina WHERE id_professor = " + p.getId() + ";";
        retorno = consulta.executeQuery(sql);

        while (retorno.next()) {
            String id = retorno.getString("id");
            int periodo = retorno.getInt("periodo");
            String desc = retorno.getString("desc");
            Disciplina d1 = new Disciplina(id, desc, periodo, p);
            d.add(d1);
        }

        this.desconectar();
        return d;
    }
}

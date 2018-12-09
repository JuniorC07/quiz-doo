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
import model.Conexao;
import model.Pessoa;

/**
 *
 * @author Avell
 */
public class DAOPessoa {

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

    public void inserirPessoa(Pessoa pessoa) {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlInsert = "INSERT INTO dbquiz.pessoa VALUES(null,'" + pessoa.getNome() + "',null);";

        System.out.println("Vai executar: " + sqlInsert);
        try {
            consulta.executeUpdate(sqlInsert);
        } catch (SQLException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.desconectar();
    }

    public void editPessoa(Pessoa pessoa) {

        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlEdit = "UPDATE dbquiz.pessoa SET nome = '" + pessoa.getNome() + "' WHERE id = " + pessoa.getId() + ";";

        System.out.println("Vai executar: " + sqlEdit);
        try {
            consulta.executeUpdate(sqlEdit);
        } catch (SQLException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.desconectar();
    }

    public void deletePessoa(Pessoa pessoa) {

        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlDelete = "DELETE FROM dbquiz.pessoa WHERE id = " + pessoa.getId() + ";";

        System.out.println("Vai executar: " + sqlDelete);
        try {
            consulta.execute(sqlDelete);
        } catch (SQLException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.desconectar();
    }

    public Pessoa getPessoa(String idp) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        Pessoa p = new Pessoa();

        String sql = "SELECT * FROM dbquiz.pessoa where id = " + idp + ";";
        retorno = consulta.executeQuery(sql);

        while (retorno.next()) {
            p.setId(retorno.getString("id"));
            p.setNome(retorno.getString("nome"));
        }
        this.desconectar();
        return p;
    }
}

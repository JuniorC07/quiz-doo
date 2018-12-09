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
import model.User;

/**
 *
 * @author Avell
 */
public class DAOLogin {

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

    public User login(String user, String pass) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        DAOProfessor dp = new DAOProfessor();
        DAOAluno da = new DAOAluno();
        User u = new User();

        String sql = "SELECT * FROM dbquiz.users where login  = '" + user + "' and password = '" + pass + "';";
        retorno = consulta.executeQuery(sql);

        while (retorno.next()) {
            if (retorno.getInt("professor") == 1) {
                u.setExiste(true);
                u.setP(dp.getProfessor(retorno.getString("id")));
                u.setProfessor(true);
            } else {
                u.setExiste(true);
                u.setA(da.getAluno(retorno.getString("id")));
                u.setProfessor(false);
            }
        }
        this.desconectar();
        return u;
    }
}

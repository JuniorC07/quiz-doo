/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Avell
 */
public class Conexao {

    public static Connection abrirConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver: " + e.getMessage());
        }
        return DriverManager.getConnection("jdbc:mysql://localhost/dbquiz1?user=root&password=root&useTimezone=true&serverTimezone=UTC");
    }
}

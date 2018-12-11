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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Alternativa;
import model.Aluno;
import model.Conexao;
import model.Disciplina;
import model.Questao;

/**
 *
 * @author Avell
 */
public class DAODisciplina {

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

    public void registrarQuestao(Disciplina d, Questao q) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            if (q.alternativas.size() >= 2) {
                int id = 0;
                boolean insert = false;
                String sqlInsert = "INSERT INTO dbquiz.questao VALUES(null,'" + q.getDesc() + "'," + d.getId() + ");";
                String sql = "select max(id) from dbquiz.questao;";
                Iterator<Alternativa> percorre = q.alternativas.iterator();

                try {
                    consulta.executeUpdate(sqlInsert);
                    insert = true;
                } catch (SQLException ex) {
                    insert = false;
                    System.out.println("Oops, nao foi possível registrar, verifique os dados");
                }
                if (insert = true) {
                    retorno = consulta.executeQuery(sql);
                    while (retorno.next()) {
                        id = retorno.getInt("max(id)");
                    }
                    while (percorre.hasNext()) {
                        Alternativa tmp = percorre.next();
                        Statement consultaAlt = conectar.createStatement();
                        int c = 0;
                        if (tmp.getCorreta()) {
                            c = 1;
                        } else {
                            c = 0;
                        }
                        sqlInsert = "INSERT INTO dbquiz.alternativa VALUES(null," + id + ",'" + tmp.getDesc() + "'," + c + ");";
                        consultaAlt.executeUpdate(sqlInsert);
                    }
                }
            } else {
                System.out.println("Você precisa de no minimo 2 alternativas");
            }
            break;
        }
        this.desconectar();
    }

    public ArrayList<Questao> buscarQuestoesPendentesAluno(ArrayList<Questao> questoes, Aluno a, Disciplina d) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Questao> questoesPendentes = new ArrayList();
        String sql = "SELECT * FROM dbquiz.questao where id_disciplina = " + d.getId() + " and id != ";
        Iterator<Questao> percorre = questoes.iterator();
        int aux = 0;

        while (percorre.hasNext()) {
            Questao tmp = percorre.next();
            if (aux == 0) {
                sql += tmp.getId() + " ";
            } else {
                sql += "and id != " + tmp.getId() + " ";
            }
            aux = 5;
        }
        sql += ";";
        retorno = consulta.executeQuery(sql);

        while (retorno.next()) {
            Questao q = new Questao();
            q.setDesc(retorno.getString("desc"));
            q.setId(retorno.getString("id"));
            q.setId_disciplina(retorno.getString("id_disciplina"));
            sql = "SELECT * FROM dbquiz.alternativa where id_questao = " + q.getId() + ";";
            ResultSet returnAlternativa;
            Statement consultaAlternativa = conectar.createStatement();
            returnAlternativa = consultaAlternativa.executeQuery(sql);

            while (returnAlternativa.next()) {
                Alternativa alt = new Alternativa();
                alt.setId(returnAlternativa.getString("id"));
                alt.setDesc(returnAlternativa.getString("desc"));
                aux = returnAlternativa.getInt("correta");
                if (aux == 1) {
                    alt.setCorreta(true);
                } else {
                    alt.setCorreta(false);
                }
                q.alternativas.add(alt);
            }

            questoesPendentes.add(q);
        }

        this.desconectar();
        return questoesPendentes;
    }

    public ArrayList<Questao> buscarQuestoesRespondidasAluno(Disciplina d, Aluno a) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Questao> questoes = new ArrayList();
        String sql = "SELECT * FROM dbquiz.quizr where id_disciplina = " + d.getId() + " and id_aluno = " + a.getId() + ";";
        retorno = consulta.executeQuery(sql);
        while (retorno.next()) {
            Questao q = new Questao();
            int id = retorno.getInt("id");
            String id_questao = retorno.getString("id_questao");
            String id_alternativa = retorno.getString("id_alternativa");
            q.setId(id_questao);
            q.setId_resposta(id_alternativa);
            ResultSet returnAlternativa;
            Statement consultaAlternativa = conectar.createStatement();
            Statement consultaAlternativas = conectar.createStatement();
            sql = "SELECT * FROM dbquiz.alternativa where id = " + id_alternativa + ";";
            returnAlternativa = consultaAlternativa.executeQuery(sql);

            while (returnAlternativa.next()) {
                int correta = returnAlternativa.getInt("correta");
                if (correta == 1) {
                    q.setAcertou(true);
                } else {
                    q.setAcertou(false);
                }
            }
            sql = "SELECT * FROM dbquiz.questao where id = " + q.getId() + ";";
            returnAlternativa = consultaAlternativa.executeQuery(sql);

            while (returnAlternativa.next()) {
                String desc = returnAlternativa.getString("desc");
                q.setDesc(desc);
            }

            sql = "SELECT * FROM dbquiz.alternativa where id_questao = " + q.getId() + ";";
            returnAlternativa = consultaAlternativas.executeQuery(sql);

            while (returnAlternativa.next()) {
                Alternativa alt = new Alternativa();
                int aux;

                alt.setId(returnAlternativa.getString("id"));
                alt.setDesc(returnAlternativa.getString("desc"));
                aux = returnAlternativa.getInt("correta");
                if (aux == 1) {
                    alt.setCorreta(true);
                } else {
                    alt.setCorreta(false);
                }
                q.alternativas.add(alt);
            }
            questoes.add(q);
        }

        this.desconectar();
        return questoes;
    }

    public ArrayList<Questao> buscarQuestoesRespondidasProfessor(Disciplina d) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Questao> questoes = new ArrayList();
        String sql = "SELECT * FROM dbquiz.quizr where id_disciplina = " + d.getId() + ";";
        retorno = consulta.executeQuery(sql);
        while (retorno.next()) {
            Questao q = new Questao();
            int id = retorno.getInt("id");
            String id_questao = retorno.getString("id_questao");
            String id_alternativa = retorno.getString("id_alternativa");
            q.setId_resposta(id_alternativa);
            q.setId(id_questao);
            ResultSet returnAlternativa;
            Statement consultaAlternativa = conectar.createStatement();
            Statement consultaAlternativas = conectar.createStatement();
            sql = "SELECT * FROM dbquiz.alternativa where id = " + id_alternativa + ";";
            returnAlternativa = consultaAlternativa.executeQuery(sql);

            while (returnAlternativa.next()) {
                int correta = returnAlternativa.getInt("correta");
                if (correta == 1) {
                    q.setAcertou(true);
                } else {
                    q.setAcertou(false);
                }
            }
            sql = "SELECT * FROM dbquiz.questao where id = " + q.getId() + ";";
            returnAlternativa = consultaAlternativa.executeQuery(sql);

            while (returnAlternativa.next()) {
                String desc = returnAlternativa.getString("desc");
                q.setDesc(desc);
            }

            sql = "SELECT * FROM dbquiz.alternativa where id_questao = " + q.getId() + ";";
            returnAlternativa = consultaAlternativas.executeQuery(sql);

            while (returnAlternativa.next()) {
                Alternativa alt = new Alternativa();
                int aux;

                alt.setId(returnAlternativa.getString("id"));
                alt.setDesc(returnAlternativa.getString("desc"));
                aux = returnAlternativa.getInt("correta");
                if (aux == 1) {
                    alt.setCorreta(true);
                } else {
                    alt.setCorreta(false);
                }
                q.alternativas.add(alt);
            }
            questoes.add(q);
        }

        this.desconectar();
        return questoes;
    }

    public ArrayList<Aluno> buscaAluno(Disciplina d) throws SQLException {
        ArrayList<Aluno> alunos = new ArrayList();
        DAOAluno dAluno = new DAOAluno();
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "SELECT * FROM dbquiz.aluno_disciplina where id_disciplina = " + d.getId() + ";";
        retorno = consulta.executeQuery(sql);

        while (retorno.next()) {
            String id = retorno.getString("id_aluno");
            Aluno aluno = new Aluno(1, "a");

            aluno = dAluno.getAluno(id);
            alunos.add(aluno);
        }
        this.desconectar();
        return alunos;
    }

    public void responderQuestao(Aluno a, Questao q, Alternativa al) throws SQLException {

        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAODisciplina.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "INSERT INTO dbquiz.quizr VALUES(null," + q.getId() + "," + a.getId() + "," + al.getId() + "," + q.getId_disciplina() + ");";
        consulta.executeUpdate(sql);

        this.desconectar();
    }

    public Disciplina getDisciplina(String id) throws SQLException {
        try {
            this.conectar();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        Disciplina d = new Disciplina();
        DAOProfessor pr = new DAOProfessor();
        DAODisciplina dr = new DAODisciplina();

        String sql = "SELECT * FROM dbquiz.disciplina where id = " + id + ";";
        retorno = consulta.executeQuery(sql);

        while (retorno.next()) {
            d.setId(retorno.getString("id"));
            d.setDesc(retorno.getString("desc"));
            d.setPeriodo(retorno.getInt("periodo"));
            d.setProfessor(pr.getProfessor(retorno.getString("id_professor")));
        }
        d.alunos = dr.buscaAluno(d);

        this.desconectar();
        return d;
    }
}

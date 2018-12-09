package model;

import controller.DAOProfessor;
import controller.DAODisciplina;
import controller.DAOPessoa;
import view.TelaLogin;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Avell
 */
public class ProjetoFinal {

    /**
     * @param args the command line arguments
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, SQLException {

        TelaLogin tela = new TelaLogin();
        DAODisciplina d = new DAODisciplina();
        ArrayList<Questao> questoes = new ArrayList();
        questoes = d.buscarQuestoesRespondidasProfessor(d.getDisciplina("1"));
        Iterator perc = questoes.iterator();
        System.out.println("DEsc: " + questoes.get(0).getDesc() + "Acertou" + questoes.get(0).getAcertou());
    }

}

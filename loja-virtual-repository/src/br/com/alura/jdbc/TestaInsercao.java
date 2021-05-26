package br.com.alura.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.alura.jdbc.factory.ConnectionFactory;

public class TestaInsercao {

	public static void main(String[] args) throws SQLException {

		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.recuperarConexao();
		
		//AUTO COMMIT DESABILITADO PARA PODER FAZER MAIS DE UMA INSERCAO NA MESMA TRANSACAO
		connection.setAutoCommit(false);
		
		
		//TRY WHITH RESOURCES GARANTE QUE TODOS OS OBJETOS QUE ESTENDEM CLOSABLE VÃO SER FECHADO NO FINAL DO BLOCO
		try (PreparedStatement pstm = connection.prepareStatement("INSERT INTO PRODUTO (nome, descricao) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
				){
			
			
			adicionaVariavel("Smart TV", "45 polegadas",pstm);
			adicionaVariavel("Notebook", "AMD Ryzen 5",pstm);

			//ESSE METODO DEVE SER CHAMADO POIS O COMMIT FOI SETADO PARA MANUAL
			connection.commit();

			pstm.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
			//CASO ACONTEÇA ALGUM PROBLEMA DURANTE A TRANSAÇÃO TODAS AS OPERAÇÕES SÃO DESCARTADAS E AS TABELAS VOLTAM PARA O ESTADO INICIAL
			System.out.println("ROLLBACK");
			connection.rollback();
		}
	}

	private static void adicionaVariavel(String nome, String descricao, PreparedStatement pstm) throws SQLException {
		pstm.setString(1, nome);
		pstm.setString(2, descricao);
		pstm.executeUpdate();

		try (ResultSet rst = pstm.getGeneratedKeys()) {
			while(rst.next()) {
				Integer id = rst.getInt(1);
				System.out.println("O id criado foi: " + id);
			}
		}
	}
}

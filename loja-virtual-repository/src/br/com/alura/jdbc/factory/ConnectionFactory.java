package br.com.alura.jdbc.factory;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

	
	DataSource dataSource;
	
	public ConnectionFactory()
	{
		ComboPooledDataSource comboPoolDatasource = new ComboPooledDataSource();
		comboPoolDatasource.setJdbcUrl("jdbc:mysql://localhost/loja_virtual?useTimezone=true&serverTimezone=UTC");
		comboPoolDatasource.setUser("root");
		comboPoolDatasource.setPassword("root");
	
		comboPoolDatasource.setMaxPoolSize(10);
		
		this.dataSource = comboPoolDatasource;
	}
	
	public Connection recuperarConexao() throws SQLException {
		return this.dataSource.getConnection();
	}
}

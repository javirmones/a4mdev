package org.practicaISO.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Agente {
	
	private Connection conn;
	
	public Agente () {
		this.conn=this.getConexion();
	}
	
	public static Connection getConexion() {
		Connection con = null;
		try {
			System.out.println();
			System.out.println();
			System.out.println();
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/bbdd_iso?autoReconnect=true&useSSL=false";
			String user = "root";
			String pass = "root";
			con = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el driver.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos.");
			e.printStackTrace();
		}

		return con;
	}
}

package com.gvnrobles.amazonviewer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import static com.gvnrobles.amazonviewer.db.DataBase.*;

public interface IDBConnection {
	@SuppressWarnings("finally")
	default Connection connectToDB() {
		// Inicializando Connection
		Connection connection = null;
		try {
			// Llamando al driver
			Class.forName("com.mysql.jdbc.Driver");
			// Haciendo uso de DriverManager
			connection = DriverManager.getConnection(URL + DB, USER, PASSWORD);
			if(connection != null) {
				System.out.println("Se estlabecio la conexión");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return connection;
		}
	}
}

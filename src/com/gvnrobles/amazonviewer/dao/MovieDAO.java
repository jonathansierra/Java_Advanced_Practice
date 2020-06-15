package com.gvnrobles.amazonviewer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static com.gvnrobles.amazonviewer.db.DataBase.*;

import com.anncode.amazonviewer.model.Movie;
import com.gvnrobles.amazonviewer.db.IDBConnection;

public interface MovieDAO extends IDBConnection {
	
	default Movie setMovieViewed(Movie movie) {
		try(Connection connection = connectToDB()) {
			// Preparamos el objeto para que pueda ejecutar acciones en la db
			Statement statement = connection.createStatement();
			String query = "INSERT INTO " + TVIEWED + 
					"(" + TVIEWED_IDMATERIAL + ", " + TVIEWED_IDELEMENT + ", " + TVIEWED_IDUSUARIO + ")"
					+ " VALUES(" + ID_TMATERIALS[0] + ", " + movie.getId() + ", " + TUSER_IDUSUARIO + ")";
			if(statement.executeUpdate(query) > 0) {
				System.out.println("Se marcó en visto");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return movie;
	}
	
	default ArrayList<Movie> read() {
		ArrayList<Movie> movies = new ArrayList<>();
		try (Connection connection = connectToDB()) {
			String query = "SELECT * FROM " + TMOVIE;
			// Utilizando un Prepared Statment para poder leer datos
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			// Ejecutamos el Query y lo almacenamos en un ResultSet
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Movie movie = new Movie(
						rs.getString(TMOVIE_TITLE),
						rs.getString(TMOVIE_GENRE), 
						rs.getString(TMOVIE_CREATOR),
						rs.getInt(TMOVIE_DURATION),
						rs.getShort(TMOVIE_YEAR));
				movie.setId(rs.getInt(TMOVIE_ID));
				
				movie.setViewed(getMovieViewed(
						preparedStatement,
						connection,
						rs.getInt(TMOVIE_ID)));
				movies.add(movie);
			}
		} catch(SQLException e) {
			
		}
		return movies;
	}
	
	private boolean getMovieViewed(PreparedStatement preparedStatement, Connection connection, int id_movie) {
		boolean viewed = false;
		// Preparamos un query con el símbolo ?, el cual nos ayuda a poner párametros
		String query = "SELECT * FROM " + TVIEWED + 
				" WHERE " + TVIEWED_IDMATERIAL + " = ? " +  
				" AND " + TVIEWED_IDELEMENT + " = ? " + 
				" AND " + TVIEWED_IDUSUARIO + " = ? ";
		ResultSet rs = null;
		try {
			// Definimos las variables que pasarán por párametro
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, ID_TMATERIALS[0]);
			preparedStatement.setInt(2, id_movie);
			preparedStatement.setInt(3, TUSER_IDUSUARIO);
			rs = preparedStatement.executeQuery();
			viewed = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewed;
	}
}

package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenti;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setDirectors(Map<Integer,Director> mappa, int anno) {
		String sql = "SELECT DISTINCT d.id, d.first_name, d.last_name "
				+ "FROM directors AS d, movies_directors AS md, movies AS m "
				+ "WHERE d.id = md.director_id AND md.movie_id = m.id AND m.year = ?";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				if(!mappa.containsKey(director.getId())) {
					mappa.put(director.getId(), director);
				}
			}
			conn.close();

			
		} catch (SQLException e) {
			e.printStackTrace();

		}
		
	}
	
	public List<Adiacenti> getAdiacenti(Map<Integer,Director> mappa, int anno){
		String sql = "SELECT md1.director_id, md2.director_id, COUNT(*) AS count "
				+ "FROM roles AS r1, movies_directors AS md1, movies AS m1, "
				+ "roles AS r2, movies_directors AS md2, movies AS m2 "
				+ "WHERE r1.movie_id = m1.id AND md1.movie_id = m1.id AND m1.year = ? AND "
				+ "r2.movie_id = m2.id AND md2.movie_id = m2.id AND m2.year = m1.year AND "
				+ "r1.actor_id = r2.actor_id AND md1.director_id < md2.director_id "
				+ "GROUP BY md1.director_id, md2.director_id";
		List<Adiacenti> result = new ArrayList<>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Director d1 = mappa.get(res.getInt("md1.director_id"));
				Director d2 = mappa.get(res.getInt("md2.director_id"));
				if(d1 != null && d2 != null) {
					result.add(new Adiacenti(d1,d2,res.getInt("count")));
				}
			}
			conn.close();

			
		} catch (SQLException e) {
			e.printStackTrace();

		}
		
		return result;

	}
	
	
	public List<String> getAnni(){
		String sql = "SELECT Distinct year "
				+ "FROM movies "
				+ "WHERE YEAR<=2006";
		List<String> result = new ArrayList<>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				result.add(String.valueOf(res.getInt("year")));
			}
			conn.close();

			
		} catch (SQLException e) {
			e.printStackTrace();

		}
		
		return result;

		
	}
}

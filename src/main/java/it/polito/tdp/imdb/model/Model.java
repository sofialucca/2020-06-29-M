package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	private ImdbDAO dao;
	private Map<Integer,Director> idMap;
	private Graph<Director,DefaultWeightedEdge> grafo;
	
	public Model() {
		dao = new ImdbDAO();

	}
	
	public void creaGrafo(int anno) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//vertici
		idMap = new HashMap<>();
		dao.setDirectors(idMap, anno);
		Graphs.addAllVertices(grafo, idMap.values());
		
		//archi
		for(Adiacenti a: dao.getAdiacenti(idMap, anno)) {
			Graphs.addEdge(grafo, a.getDir1(), a.getDir2(), a.getnAttori());
		}
		
	}
	
	public List<RegistaNumero> getAffini(Director dir){
		List<RegistaNumero> result = new ArrayList<>();
		
		for(Director d : Graphs.neighborListOf(grafo, dir)) {
			DefaultWeightedEdge e = grafo.getEdge(d, dir);
			result.add(new RegistaNumero(d,grafo.getEdgeWeight(e)));
		}
		
		Collections.sort(result);
		return result;
	}
	
	public List<String> getAnni(){
		return dao.getAnni();
	}
	
	public List<Director> getVertex(){
		return new ArrayList<>(grafo.vertexSet());
	}
	
	public int getSizeEdge() {
		return grafo.edgeSet().size();
	}
}

package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
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
	private List<Director> risultato;
	private int costoOttimale;
	
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
	
	public List<Director> getPercorso(Director partenza, int c){
		risultato = new ArrayList<>();
		costoOttimale = 0;
		List<Director> parziale = new ArrayList<Director>();
		parziale.add(partenza);
		ricerca(parziale,this.getAffini(partenza) , c, 0);
		return risultato;
	}

	private void ricerca(List<Director> parziale, List<RegistaNumero> riferimento, int c, int costoCammino) {
		if(costoCammino > c) {
			return;
		}
		
		if(costoCammino > costoOttimale) {
			costoOttimale = costoCammino;
			risultato = new ArrayList<>(parziale);
			if(costoCammino == c) {
				return;
			}
			System.out.println(risultato);
			System.out.println(costoOttimale);
		}
		
		if(riferimento.isEmpty()) {
			return;
		}
		
		for(RegistaNumero rn: riferimento) {
			Director dir = rn.getDir();
			if(!parziale.contains(dir)){
				parziale.add(dir);
				ricerca(parziale, this.getAffini(dir), c, (int) (costoCammino + rn.getnAttori()));
				parziale.remove(dir);				
			}

		}
	}
	
	public int getNAttori() {
		return costoOttimale;
	}

	private int costoCammino(List<Director> parziale) {
		int costo = 0;
		for(int i = 0; i<parziale.size()-1; i++) {
			DefaultWeightedEdge e = grafo.getEdge(parziale.get(i), parziale.get(i+1));
			costo += (int) grafo.getEdgeWeight(e); 
		}
		return costo;
	}
}

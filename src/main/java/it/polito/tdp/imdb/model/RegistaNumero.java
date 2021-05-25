package it.polito.tdp.imdb.model;

public class RegistaNumero implements Comparable<RegistaNumero>{

	private Director dir;
	private double nAttori;
	
	public RegistaNumero(Director dir, double nAttori) {
		super();
		this.dir = dir;
		this.nAttori = nAttori;
	}
	public Director getDir() {
		return dir;
	}
	public void setDir(Director dir) {
		this.dir = dir;
	}
	public double getnAttori() {
		return nAttori;
	}
	public void setnAttori(int nAttori) {
		this.nAttori = nAttori;
	}
	
	@Override
	public int compareTo(RegistaNumero o) {
		// TODO Auto-generated method stub
		return (int) (o.nAttori - this.nAttori);
	}
	@Override
	public String toString() {
		return dir.toString() + " - # attori condivisi: " + (int) nAttori;
	}
	
	
	
}

package it.polito.tdp.imdb.model;

public class Adiacenti {
	
	private Director dir1;
	private Director dir2;
	private int nAttori;
	
	public Adiacenti(Director dir1, Director dir2, int nAttori) {
		super();
		this.dir1 = dir1;
		this.dir2 = dir2;
		this.nAttori = nAttori;
	}

	public Director getDir1() {
		return dir1;
	}

	public void setDir1(Director dir1) {
		this.dir1 = dir1;
	}

	public Director getDir2() {
		return dir2;
	}

	public void setDir2(Director dir2) {
		this.dir2 = dir2;
	}

	public int getnAttori() {
		return nAttori;
	}

	public void setnAttori(int nAttori) {
		this.nAttori = nAttori;
	}
	
	
}

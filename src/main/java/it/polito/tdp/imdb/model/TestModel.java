package it.polito.tdp.imdb.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo(2005);
		
		System.out.println(m.getAffini(new Director(498,"Keith", "Adler")));
	}

}

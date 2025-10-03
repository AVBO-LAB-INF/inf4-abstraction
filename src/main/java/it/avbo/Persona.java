package it.avbo;

public abstract class Persona {
    // ATTENZIONE: visibilità "protected"
    protected String nome;
    protected String cognome;
    protected int eta;

    // Costante accessibile ovunque
    public static final int FABBISOGNO_CALORICO = 2000;

    public Persona(String nome, String cognome, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
    }

    // Metodo concreto, comune a tutti i tipi di Persona
    public void saluta() {
        System.out.println("Ciao, mi chiamo " + nome + " " + cognome + " e ho " + eta + " anni!");
    }

    // Metodo astratto: non ha corpo, deve obbligatoriamente essere implementato
    // nelle sottoclassi
    public abstract int calcolaFabbisognoCalorico();
}

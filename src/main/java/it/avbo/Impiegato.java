package it.avbo;


public class Impiegato extends Persona {
    private String mansione;

    public Impiegato(String nome, String cognome, int eta, String mansione) {
        super(nome, cognome, eta);
        this.mansione = mansione;
    }

    @Override
    public void saluta() {
        super.saluta();
        System.out.println("Sono un impiegato con la mansione di " + mansione);
    }

    // Implementazione del metodo astratto
    @Override
    public int calcolaFabbisognoCalorico() {
        return (int)(FABBISOGNO_CALORICO * 0.8);
    }
}

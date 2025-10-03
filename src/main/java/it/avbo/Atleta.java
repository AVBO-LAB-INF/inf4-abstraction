package it.avbo;


// Classe concreta che estende Persona
public class Atleta extends Persona {
    private String sport;

    public Atleta(String nome, String cognome, int eta, String sport) {
        super(nome, cognome, eta);
        this.sport = sport;
    }

    @Override
    public void saluta() {
        super.saluta();
        System.out.println("Sono un atleta e il mio sport è " + sport);
    }

    // Implementazione personalizzata del metodo astratto
    @Override
    public int calcolaFabbisognoCalorico() {
        return (int)(FABBISOGNO_CALORICO * 1.5); // oppure direttamente return 3000;
    }
}

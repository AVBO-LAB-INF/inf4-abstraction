package it.avbo;


// Classe concreta che estende Persona
public class Atleta extends Persona implements IAtleta {
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

    @Override
    public void faiAllenamento(int ore) {
        System.out.println("Mi sono allenato per " + ore + " ore! Sono cotto...");
    }

    @Override
    public int getRisultatoGara(String nomeGara) {
        // suppongo di reperire il dato da un "registro" dei risultati delle gare che
        // l'atleta ha effettuato
        return 3; // comunque sul podio.. niente male!
    }
}

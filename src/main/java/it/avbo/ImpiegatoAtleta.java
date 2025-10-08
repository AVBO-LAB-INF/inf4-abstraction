package it.avbo;

public class ImpiegatoAtleta extends Impiegato implements IAtleta {

    private String sport;

    public ImpiegatoAtleta(String nome, String cognome, int eta, String mansione, String sport) {
        super(nome, cognome, eta, mansione);
        this.sport = sport;
    }

    @Override
    public int calcolaFabbisognoCalorico() {
        // suppongo che il fabbisogno sia un po' inferiore a quello di un atleta di professione
        return (int)(FABBISOGNO_CALORICO * 1.2);
    }

    @Override
    public void saluta() {
        super.saluta();
        System.out.println("Sono anche un atleta e il mio sport è: " + sport);
    }

    @Override
    public void faiAllenamento(int ore) {
        System.out.println("Mi sono allenato per " + ore + " ore: non voglio stare sempre seduto!");
    }

    @Override
    public int getRisultatoGara(String nomeGara) {
        return 5;
    }
}


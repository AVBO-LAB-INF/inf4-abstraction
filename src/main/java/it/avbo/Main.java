package it.avbo;

public class Main {
    public static void main(String[] args) {

        Persona impiegato =
                new Impiegato("Mario", "Rossi", 33, "Programmatore");

        Persona atleta =
                new Atleta("Luca", "Verdi", 24, "Nuoto");

        impiegato.saluta();
        System.out.println("Il mio fabbisogno calorico è di " + impiegato.calcolaFabbisognoCalorico() + " calorie.");


        atleta.saluta();
        System.out.println("Il mio fabbisogno calorico è di " + atleta.calcolaFabbisognoCalorico() + " calorie.");
    }
}
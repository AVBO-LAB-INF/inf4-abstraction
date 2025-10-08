# Astrazione ed ereditarietà

## Introduzione

Uno dei principi fondamentali e fondanti della programmazione orientata agli oggetti, è quello di 
**astrazione**.

L'idea è quella di ridurre la complessità di un sistema, concentrandosi sugli aspetti essenziali e 
tralasciando i dettagli superflui. 

Attraverso l’astrazione, possiamo **modellare concetti generali che fungono da base per entità più 
specifiche**.

Immaginiamo di voler rappresentare in un programma diverse categorie di persone, ad esempio musicisti 
e attori. 
Entrambe queste categorie condividono alcune caratteristiche comuni: un nome, un’età, un’altezza. 
Per evitare di ripetere questi campi più volte, possiamo raccoglierli in una classe più generale, 
che chiameremo `Persona`.

Dobbiamo tuttavia osservare un punto fondamentale: non ha senso istanziare direttamente un 
oggetto della classe `Persona`: nessuna persona è rappresentata soltanto da nome, età e altezza; 
ogni individuo possiede qualità (attributi) ulteriori come abilità, passioni e obiettivi che lo
caratterizzano "completamente". 
Per questa ragione, la classe `Persona` non rappresenta un’entità concreta, bensì un concetto astratto.

## Classi astratte
In Java, quando una classe serve soltanto a definire una struttura comune e non deve mai essere 
istanziata direttamente, la dichiariamo con la parola chiave `abstract`. 

Una classe astratta può contenere:
- Campi e metodi concreti, già implementati e pronti all’uso.
- Metodi astratti, cioè privi di implementazione, che dovranno essere necessariamente ridefiniti 
  dalle classi derivate.

Va notato che se in una certa classe sono presenti metodi astratti, a maggior ragione è "impossibile" 
istanziarla, in quanto non è disponibile il codice da eseguire laddove venissero invocati tali metodi.

Vediamo un esempio:

Un `Impiegato` è tipicamente una persona sedentaria. Se generalmente il fabbisogno calorico di un adulto
si può considerare di circa 2000 kcal al giorno, nel caso dell'impiegato esso si può assumere sia sensibilmente
ridotto rispetto al caso generale, in virtù della sua ridotta attività fisica.

Un `Atleta` invece si può considerare una persona con attività fisica intensa.  Il suo fabbisogno calorico sarà 
tendenzialmente maggiore rispetto alla media, poiché un allenamento costante comporta un consumo energetico elevato.
Possiamo ad esempio ipotizzare che un atleta possa aver bisogno di 3000–3500 kcal al giorno o più, 
anche a seconda del tipo di sport praticato.

Proviamo a modellare questi concetti in Java:

```java
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
```

Osserviamo che il metodo `calcolaFabbisognoCalorico()` è astratto e non ha corpo: non è possibile determinare
nella classe base quale sia il fabbisogno calorico di una persona qualsiasi, in quanto dipende dal tipo di
attività fisica che svolge. Deve quindi essere implementato nelle specifiche classi derivate, ciascuna con il
calcolo corretto in base alle caratteristiche della "categoria" di persona che si intende modellare.

```java
public class Impiegato extends Persona {
    private String mansione;

    public Impiegato(String nome, String cognome, int eta, String mansione) {
        super(nome, cognome, eta);
        this.mansione = mansione;
    }

    // Implementazione del metodo astratto
    @Override
    public int calcolaFabbisognoCalorico() {
        return (int)(FABBISOGNO_CALORICO * 0.8);
    }
}

// Classe concreta che estende Persona
public class Atleta extends Persona {
    private String sport;

    public Atleta(String nome, String cognome, int eta, String sport) {
        super(nome, cognome, eta);
        this.sport = sport;
    }

    // Implementazione personalizzata del metodo astratto
    @Override
    public int calcolaFabbisognoCalorico() {
        return (int)(FABBISOGNO_CALORICO * 1.5); // oppure direttamente return 3000;
    }
}
```

Un diagramma che rappresenta la gerarchia di persone che abbiamo
considerato nel nostro problema, è rappresentato nell'immagine seguente:

![GerarchiaAtleta-1.png](GerarchiaAtleta-1.png)

Proviamo ad istanziare queste classi e a determinare il fabbisogno di impiegato ed
atleta:

```java

public class Main {
    public static void main(String[] args) {

        Impiegato impiegato =
                new Impiegato("Mario", "Rossi", 33, "Programmatore");

        Atleta atleta =
                new Atleta("Luca", "Verdi", 24, "Nuoto");

        impiegato.saluta();
        System.out.println("Il mio fabbisogno calorico è di " + 
                impiegato.calcolaFabbisognoCalorico() + " calorie.");


        atleta.saluta();
        System.out.println("Il mio fabbisogno calorico è di " + 
                atleta.calcolaFabbisognoCalorico() + " calorie.");
    }
}
```

A questo punto, possiamo immaginare che quando una persona saluta, le si richieda di specificare anche a quale
categoria appartenga. Entra così in gioco il concetto di **Polimorformo**.

## Polimorfismo
Il termine polimorfismo deriva dal greco e significa “molte forme”.
Nella programmazione orientata agli oggetti, il termine polimorfismo indica la capacità di un riferimento dichiarato 
con un tipo generico (ad esempio il tipo di una classe base o di un’interfaccia) di riferirsi, **in fase di esecuzione**, a 
istanze di classi diverse che derivano da quel tipo.

In altre parole, una variabile dichiarata come appartenente a una superclasse (ad esempio `Persona p`) può contenere, 
a seconda del caso, un’istanza di una sua sottoclasse (`Impiegato`, `Atleta`, `Studente`, ecc.). 
Quando su quel riferimento viene invocato un metodo, l’implementazione effettivamente eseguita non è quella della 
classe base, ma quella specifica della classe concreta a cui appartiene l’oggetto istanziato.

Ad esempio, ridefiniamo il metodo `saluta()` già definito nella classe `Persona` anche nelle sue sottoclassi:
```java

public class Impiegato extends Persona {
    
    /* .. omissis ...*/

    // Ridefinizione del metodo saluta
    @Override
    public void saluta() {
        super.saluta();
        System.out.println("Sono un impiegato con la mansione di " + mansione);
}

// Classe concreta che estende Persona
public class Atleta extends Persona {

    /* .. omissis ...*/

    // Ridefinizione del metodo saluta    
    @Override
    public void saluta() {
        super.saluta();
        System.out.println("Sono un atleta e il mio sport è " + sport);
    }
}
```
Ora verifichiamo cosa succede se ad un riferimento ad un oggetto di tipo `Persona` associamo un'istanza di un oggetto
di una sua sottoclasse:

```java
public class Main {
    public static void main(String[] args) {

        // sostituisco alla definizione della variabile di tipo Impiegato il tipo Persona
        Persona impiegato =
                new Impiegato("Mario", "Rossi", 33, "Programmatore");

        // sostituisco alla definizione della variabile di tipo Atleta il tipo Persona
        Persona atleta =
                new Atleta("Luca", "Verdi", 24, "Nuoto");

        impiegato.saluta();
        System.out.println("Il mio fabbisogno calorico è di " + impiegato.calcolaFabbisognoCalorico() + " calorie.");


        atleta.saluta();
        System.out.println("Il mio fabbisogno calorico è di " + atleta.calcolaFabbisognoCalorico() + " calorie.");
    }
}
```
Sebbene la variabile p sia dichiarata come `Persona`, al momento dell’esecuzione il metodo `saluta()` invocato è quello 
specifico della classe `Atleta`, perché è questo il tipo reale dell’oggetto istanziato.

In sintesi, il polimorfismo permette di scrivere codice che opera su un **riferimento astratto**
(la superclasse o l’interfaccia), demandando al sistema l’individuazione del comportamento corretto in base al tipo 
concreto dell’oggetto. Questo rende il codice più flessibile, riutilizzabile ed estensibile.

**_IMPORTANTE:_** il principio del polimorfismo _non_ funziona solo se la classe base è astratta, ma vale in generale per
ogni gerarchia, anche di più livelli.

In precedenza abbiamo "enfatizzato" l'aspetto che la capacità di un riferimento ad un oggetto di tipo
generico, di riferirsi ad oggetti di classi diverse che derivano da esso, in Java si ha a **runtime**, ossia
**in fase di esecuzione, non di compilazione**.

Questo aspetto porta a definire alcuni importanti concetti relativi a come vengono associati i riferimenti
alle variabii e in particolare vogliamo approfondire i concetti di **Binding Statico e Dinamico**.

## Binding Statico e Dinamico

Quando in un linguaggio di programmazione viene invocato un metodo, il compilatore o l’ambiente di 
esecuzione deve stabilire quale versione di quel metodo eseguire. 
Questo collegamento tra invocazione del metodo e corpo del metodo effettivo prende il nome di 
**Binding** (o legame).

Esistono sostanzialmente due possibilità:

1. **Binding statico** (o legame "anticipato"): avviene a **tempo di compilazione**.

Il compilatore decide già in fase di compilazione quale metodo sarà invocato, basandosi sul tipo 
statico del riferimento (cioè sul tipo con cui la variabile è stata dichiarata).
È _molto efficiente_, perché non richiede ulteriori controlli in fase di esecuzione, ma per contro è
poco flessibile, perché non permette comportamenti polimorfici.

Il binding statico è presente in vari linguaggi come il C e il C++, ma in Java è utilizzato solo
per metodi `static`, `final` e `private`.

2. **Binding dinamico** (o legame "tardivo"): avviene a **tempo di esecuzione** (run-time).

La scelta del metodo da invocare dipende dal tipo dinamico dell’oggetto a cui il riferimento punta 
in quel momento dell'esecuzione del programma.

È _meno efficiente_ del binding statico, ma è molto più flessibile, in quanto _rende possibile il 
polimorfismo_.

Il binding dinamico _non_ è presente nel linguaggio C, mentre in C++ è possibile "richiederlo"
esplicitamente con il modificatore `virtual`.
In Java invece è la modalità di binding predefinita per tutti i metodi, ad eccezione appunto di quelli
sopra menzionati.

Infatti, i metodi _static_ _non appartengono all'istanza dell'oggetto_ ma bensì alla classe da cui
l'oggetto è stato istanziato. Non è quindi possibile avere polimorfismo con i metodi statici nè
è possibile ridefinirli (in realtà è possibile ma ad eccezione di alcuni casi molto particolari non è
assolutamente una best-practice ma rappresenta è un modo per "nascondere" il metodo della superclasse).

I metodi _final_ invece `non può essere ridefinito nelle sottoclassi`, perciò non ha senso utilizzare
il binding dinamico in quanto non può per definizione essere polimorfico. Anche in questo caso il 
compilatore può collegare direttamente in fase di compilazione il corpo del metodo alla sua
invocazione.

Infine, i metodi `private` infine, _non sono ereditabili dalle sottoclassi_ e non essendo visibili
"all'esterno" della classe, non fanno parte del contratto polimorfico della stessa.
Pertanto, se si dichiara un metodo in una sottoclasse con stessa signature di un metodo privato
presente nella classe base, si sta di fatto dichiarando un nuovo metodo "diverso" ed "indipendente"
da quello definito nella classe base. Pertanto, la chiamata ad un metodo `private` viene _sempre_ risolta
a compile-time.

# Il concetto di Interfaccia

Un altro elemento fondamentale che si collega al concetto di polimorfismo è quello di **interfaccia**.

Un’interfaccia in Java _non_ è una classe, ma un costrutto speciale che serve a definire un insieme di 
**metodi astratti** (detti anche _skeleton methods_) che altre classi si impegnano a implementare.

Possiamo considerarla come una sorta di "scheletro": non contiene dati né implementazioni concrete 
(salvo eccezioni introdotte nelle versioni più recenti di Java), ma **stabilisce un contratto** che le classi
che la implementano **devono** rispettare.

Essendo un insieme di metodi astratti, un’interfaccia _non_ può essere istanziata.

Al contrario, una classe che "dichiara" di implementare un’interfaccia (`implements`) _deve_ fornire l’implementazione
di tutti i metodi dichiarati dall’interfaccia, a meno che la classe stessa non sia astratta.

I metodi di un’interfaccia sono **implicitamente pubblici e astratti** (anche senza scriverlo).

Nella sua "evoluzione" il linguaggio Java ha visto l'introduzione di alcuni costrutti che rendono le interfacce
in parte non del tutto coerenti con la definizione di cui sopra, anche se di fatto non modificano la loro funzione
principale: definire contratti da rispettare.

 - Fino a Java 7 le interfacce potevano contenere solo metodi astratti e costanti (`public static final`), appunto
   come sopra definito. 
 - Da Java 8 sono stati introdotti i metodi default (con implementazione di base) e i metodi static. 
 - Da Java 9 è stata aggiunta anche la possibilità di definire metodi `private` nelle interfacce, utili come metodi 
   di supporto interni.

Le interfacce sono state introdotte in primo luogo per stabilire contratti chiari e condivisi, garantendo interoperabilità 
e disaccoppiamento tra le componenti di un sistema software, ma contestualmente consentono anche di "emulare"
l'ereditarietà multipla.

Definire contratti chiari e condivisi è di fondamentale importanza per definire una sorta di _linguaggio comune_
tra classi diversi, anche appartenenti a gerarchie distinte, affinché gli oggetti da esse ottenuti possano 
(laddove occorre) essere utilizzati in modo uniforme.
Ad esempio, l'interfaccia `Comparable` viene implementata da varie classi di base della piattaforma Java, con
l'obiettivo di consentire l'ordinamento di oggetti.
Tale interfaccia definisce il metodo `int compareTo()` che deve restituire:
 - un intero < 0 se l'oggetto corrente è "minore" dell'oggetto specificato come argomento
 - 0 se l'oggetto corrente è uguale all'oggetto specificato come argomento
 - un intero > 0 se l'oggetto corrente è "maggiore" dell'oggetto specificato come argomento

Qualsiasi oggetto implementi questa interfaccia, deve fornire un'implementazione per il metodo `compareTo` e
qualsiasi oggetto che implementa l'interfaccia `Comparable` può essere appunto "confrontato" secondo un
opportuno criterio di ordinamento totale con altri oggetti dello stesso tipo.
Pertanto, nella piattaforma sono stati previsti degli algoritmi di ordinamento, che possono funzionare su 
qualsiasi collezione di oggetti `Comparable`, perché appunto al loro interno possono invocare il metodo
`compareTo` che per contratto è stato definito e implementato in ogni oggetto della collezione da ordinare.

Inoltre, le intefacce supportano il polimorfirmo e consentono dunque di ottenere astrazione e disaccoppiamento
tra i diversi componenti del sistema software.
Ad esempio, possiamo scrivere:
```java
public class Allenatore {
    ...
    public void allena(IAtleta atleta) {
        atleta.faiAllenamento(2); // esempio
    }
}

Allenatore allenatore = new Allenatore();
Persona mario = new ImpiegatoAtleta(...);
mario.saluta();

Persona luca = new Atleta(...);
luca.saluta();

// dal punto di vista dell'allenatore non mi interessa di che tipo
// è la Persona, mi interessa solo che sia un Atleta, ossia che 
// abbia definito ed implementato metodo `faiAllenamento`
allenatore.allena(mario);
allenatore.allena(luca);

```
Infine, vediamo come le interfacce permettono un'_emulazione_ dell'ereditarietà multipla.
Java infatti, (come anche C# e la maggior parte dei linguaggi di programmazione OO) supporta **solo l'ereditarietà singola** 
tra classi: una classe può estendere **una sola** classe base. 
Questo può sembrare un limite, ma in realtà evita complicazioni e gravi errori di progettazione del software.

Ad esempio, uno dei problemi più comuni nei linguaggi che supportano l'ereditarietà multipla, ossia dove una classe può
derivare da più classi (come ad esempio C++), è quello che va sotto il nome di **Diamond Problem** 
(il problema del diamante).
Esso si verifica quando una classe eredita la _stessa caratteristica_ da più superclassi diverse.

![](DiamondProblem.png)

Vediamo nel dettaglio in cosa consiste questo problema: nella classe `ImpiegatoAtleta`, da quale classe sono ereditati 
gli attributi _nome_, _cognome_ ed _età_? `ImpiegatoAtleta` è infatti una `Persona` e deve avere tali attributi.

Inoltre, consideriamo il metodo `saluta()`, che è stato ridefinito sia in `Impiegato` sia in `Atleta`(v. sopra): quale
delle due implementazioni viene eseguita quando lo si invoca su un'istanza di `ImpiegatoAtleta`?

È evidente come l'ereditarietà multipla tra classi può dare luogo ad ambiguità e in generale a problemi non banali da
risolvere. Le interfacce ci permettono di "aggirare" questa limitazione, in quanto ogni classe può **implementare più 
interfacce contemporaneamente**.

**NOTA BENE**: non è una forma di "vera" ereditarietà multipla, ma una sorta di "emulazione" che ci permette di realizzare
una sorta di **ereditarietà multipla del comportamento**, evitando di ereditare campi duplicati e/o implementazioni di
metodi in conflitto.

Proviamo a fare un esempio, aggiungendo alcuni metodi "specifici" per l'Atleta, ma prima definendoli 
nell'interfaccia `IAtleta`:

```java
public interface IAtleta {
    void faiAllenamento(int ore);
    int getRisultatoGara(String nomeGara); // posizione 1,2,3...
}

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
```

In questo modo stiamo stabilendo che ogni oggetto con interfaccia IAtleta, dovrà fornire le funzionalità
 - `faiAllenamento`
 - `getRisultatoGara`

Queste funzionalità saranno implementate da qualche altra classe, che saprà comportarsi come un `IAtleta`,
come appunto previsto dal contratto definito dall'interfaccia stessa.
È chiaro che la classe più naturale a implementare questi comportamenti è la classe `Atleta`, che quindi
_implementa_ l'interfaccia `IAtleta`.

Tuttavia, noi vorremmo modellare un `ImpiegatoAtleta`, ossia un Impiegato -- e quindi una Persona --  che però
si comporta _anche_ come un Atleta.
Per risolvere questo problema, si può estendere la classe Impiegato e contemporaneamente implementare l'interfaccia
`IAtleta`:
```java
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
```
In questo modo abbiamo utiilizzato l'ereditarietà singola per derivare da `Persona` campi e metodi comuni, ma poi abbiamo
implementato _anche_ l'interfaccia `IAtleta`, per aggiungere agli oggetti di tipo ImpiegatoAtleta anche comportamenti che
sono propri dell'atleta, come `faiAllenamento` e `getRisultatoGara`.

In Java (così come in C#), ogni classe può implementare molteplici interfacce, implementando vari comportamenti definiti
per contratto.
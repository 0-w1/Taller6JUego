import java.util.Random;

public class Taller6 {

    public static void main(String[] args) {

        Guerrero guerrero1 = new Guerrero(1, "Thor", 1, 100, 80);
        Guerrero guerrero2 = new Guerrero(2, "Kratos", 1, 110, 70);

        Mago mago1 = new Mago(3, "Merlin", 1, 90, 60, 50);
        Mago mago2 = new Mago(4, "Gandalf", 1, 95, 55, 70);

        Arquero arquero1 = new Arquero(5, "Robin", 1, 85, 50, 20);
        Arquero arquero2 = new Arquero(6, "Legolas", 1, 90, 45, 25);

        System.out.println("========================================");
        System.out.println("       PERSONAJES DEL VIDEOJUEGO");
        System.out.println("========================================");

        guerrero1.mostrarInformacion();
        guerrero2.mostrarInformacion();
        mago1.mostrarInformacion();
        mago2.mostrarInformacion();
        arquero1.mostrarInformacion();
        arquero2.mostrarInformacion();

        realizarCombate(guerrero1, mago1);
        realizarCombate(mago2, arquero1);
        realizarCombate(guerrero2, arquero2);
    }

    public static void realizarCombate(Personaje personaje1, Personaje personaje2) {

        System.out.println("\n========================================");
        System.out.println("             NUEVO COMBATE");
        System.out.println("========================================");

        System.out.println(personaje1.getNombre() + " VS " + personaje2.getNombre());

        Personaje atacante = personaje1;
        Personaje defensor = personaje2;
        int turno = 1;

        while (personaje1.estaVivo() && personaje2.estaVivo()) {

            System.out.println("\n------------- TURNO " + turno + " -------------");

            int dano = atacante.atacar();

            System.out.println(atacante.getNombre() + " ataca a "
                    + defensor.getNombre());

            System.out.println("Daño causado: " + dano);

            defensor.recibirDano(dano);

            System.out.println("Vida de " + defensor.getNombre() + ": "
                    + defensor.getPuntosVida() + "/"
                    + defensor.getVidaMaxima());

            if (!defensor.estaVivo()) {
                System.out.println(defensor.getNombre() + " ha sido derrotado.");
                break;
            }

            Personaje temporal = atacante;
            atacante = defensor;
            defensor = temporal;

            turno++;
        }

        Personaje ganador;

        if (personaje1.estaVivo()) {
            ganador = personaje1;
        } else {
            ganador = personaje2;
        }

        System.out.println("\n========================================");
        System.out.println("              RESULTADO");
        System.out.println("========================================");

        System.out.println("Ganador: " + ganador.getNombre());

        ganador.ganarExperiencia(100);

        System.out.println("Experiencia obtenida: 100 XP");
        System.out.println("Experiencia actual: " + ganador.getExperiencia() + " XP");
        System.out.println("Nivel actual: " + ganador.getNivel());
    }
}

abstract class Personaje {

    private int identificador;
    private String nombre;
    private int nivel;
    private int puntosVida;
    private int vidaMaxima;
    private int experiencia;

    public Personaje(int identificador, String nombre, int nivel, int puntosVida) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.nivel = nivel;
        this.puntosVida = puntosVida;
        this.vidaMaxima = puntosVida;
        this.experiencia = 0;
    }

    public abstract int atacar();

    public void recibirDano(int dano) {
        puntosVida = puntosVida - dano;

        if (puntosVida < 0) {
            puntosVida = 0;
        }
    }

    public boolean estaVivo() {
        return puntosVida > 0;
    }

    public void ganarExperiencia(int cantidad) {
        experiencia = experiencia + cantidad;

        int experienciaNecesaria = nivel * 100 + 100;

        if (experiencia >= experienciaNecesaria) {
            subirNivel();
        }
    }

    public void subirNivel() {

        int experienciaNecesaria = nivel * 100 + 100;

        if (experiencia >= experienciaNecesaria) {

            nivel = nivel + 1;
            vidaMaxima = vidaMaxima + 20;
            puntosVida = vidaMaxima;

            System.out.println(nombre + " ha subido al nivel " + nivel + ".");
            System.out.println("Vida máxima: " + vidaMaxima);
            System.out.println("Puntos de vida recuperados.");
        }
    }

    public void mostrarInformacion() {
        System.out.println("\n----------------------------------------");
        System.out.println("ID: " + identificador);
        System.out.println("Nombre: " + nombre);
        System.out.println("Nivel: " + nivel);
        System.out.println("Vida: " + puntosVida + "/" + vidaMaxima);
        System.out.println("Experiencia: " + experiencia + " XP");
        System.out.println("----------------------------------------");
    }

    public int getIdentificador() {
        return identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getExperiencia() {
        return experiencia;
    }
}

class Guerrero extends Personaje {

    private int fuerza;

    public Guerrero(int identificador, String nombre, int nivel,
            int puntosVida, int fuerza) {
        super(identificador, nombre, nivel, puntosVida);
        this.fuerza = fuerza;
    }

    @Override
    public int atacar() {

        int dano = fuerza * getNivel();

        if (getPuntosVida() <= getVidaMaxima() * 0.30) {
            dano = (int) (dano * 1.20);
            System.out.println(getNombre()
                    + " recibe una bonificación del 20% por vida baja.");
        }

        return dano;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo: Guerrero");
        System.out.println("Fuerza: " + fuerza);
    }
}

class Mago extends Personaje {

    private int poderMagico;
    private int mana;

    public Mago(int identificador, String nombre, int nivel,
            int puntosVida, int poderMagico, int mana) {
        super(identificador, nombre, nivel, puntosVida);
        this.poderMagico = poderMagico;
        this.mana = mana;
    }

    @Override
    public int atacar() {

        if (mana >= 20) {
            return ataqueEspecial();
        }

        return poderMagico * getNivel();
    }

    public int ataqueEspecial() {

        mana = mana - 20;

        int dano = poderMagico * getNivel() * 2;

        System.out.println(getNombre() + " utiliza ATAQUE ESPECIAL.");
        System.out.println("Mana utilizado: 20");
        System.out.println("Mana restante: " + mana);

        return dano;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo: Mago");
        System.out.println("Poder mágico: " + poderMagico);
        System.out.println("Mana: " + mana);
    }
}

class Arquero extends Personaje {

    private int fuerza;
    private int precision;
    private Random random;

    public Arquero(int identificador, String nombre, int nivel,
            int puntosVida, int fuerza, int precision) {
        super(identificador, nombre, nivel, puntosVida);
        this.fuerza = fuerza;
        this.precision = precision;
        this.random = new Random();
    }

    @Override
    public int atacar() {

        int dano = (fuerza * getNivel()) + precision;
        int numeroAleatorio = random.nextInt(100);

        if (numeroAleatorio < 20) {
            dano = dano * 2;
            System.out.println(getNombre()
                    + " realizó un ATAQUE CRÍTICO.");
        }

        return dano;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo: Arquero");
        System.out.println("Fuerza: " + fuerza);
        System.out.println("Precisión: " + precision);
    }
}

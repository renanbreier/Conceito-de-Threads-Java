class CorridaCavalos implements Runnable {
    String nomeCavalo;
    private int distanciaTotal;
    private boolean terminou;
    static String vencedor;
    private static int cavalosTerminados;

    public CorridaCavalos(String nomeCavalo, int distanciaTotal) {
        this.nomeCavalo = nomeCavalo;
        this.distanciaTotal = distanciaTotal;
        this.terminou = false;
    }

    public synchronized static void setVencedor(String cavaloNome) {
        if (vencedor == null) {
            vencedor = cavaloNome;
        }
    }

    @Override
    public void run() {
        try {
            for (int percorrido = 0; percorrido < distanciaTotal; percorrido += 10) {
                System.out.println(nomeCavalo + " correu " + percorrido + " metros.");
                Thread.sleep(100); // Apenas para simular a corrida, o atraso é de 100 milissegundos
            }
            System.out.println(nomeCavalo + " terminou a corrida!");
            terminou = true;
            setVencedor(nomeCavalo); // Define este cavalo como vencedor
            synchronized (CorridaCavalos.class) {
                cavalosTerminados++;
                if (cavalosTerminados == Main.numeroCavalos) {
                    CorridaCavalos.class.notify(); // Notifica a thread principal
                }
            }
        } catch (InterruptedException e) {
            System.out.println(nomeCavalo + " foi interrompido.");
        }
    }
}
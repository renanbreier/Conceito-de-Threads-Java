import java.util.Scanner;

public class Main {
    public static int numeroCavalos;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o número total de cavalos na corrida: ");
        numeroCavalos = scanner.nextInt();

        CorridaCavalos[] cavalos = new CorridaCavalos[numeroCavalos];
        Thread[] threads = new Thread[numeroCavalos];

        // Pedindo nomes para os cavalos
        for (int i = 0; i < numeroCavalos; i++) {
            System.out.print("Digite o nome do cavalo " + (i + 1) + ": ");
            String nomeCavalo = scanner.next();
            cavalos[i] = new CorridaCavalos(nomeCavalo, 0); // Inicializa a distância total como 0
            threads[i] = new Thread(cavalos[i]);
        }

        // Pedindo a distância total da corrida
        System.out.print("Digite a distância total da corrida (em metros): ");
        int distanciaTotal = scanner.nextInt();

        // Configurando a distância total para todos os cavalos
        for (int i = 0; i < numeroCavalos; i++) {
            cavalos[i] = new CorridaCavalos(cavalos[i].nomeCavalo, distanciaTotal);
            threads[i] = new Thread(cavalos[i]);
        }

        // Iniciando a corrida
        System.out.println("A corrida começou!");

        // Iniciando as threads para todos os cavalos
        for (int i = 0; i < numeroCavalos; i++) {
            threads[i].start();
        }

        // Aguardando todos os cavalos terminarem
        synchronized (CorridaCavalos.class) {
            try {
                CorridaCavalos.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Apresentando o vencedor
        System.out.println("A corrida terminou!");
        System.out.println("O vencedor é: " + CorridaCavalos.vencedor);
    }
}
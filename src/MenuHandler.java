import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.time.StopWatch;

public class MenuHandler {
    private final Scanner scanner = new Scanner(System.in);

    // Metoda pentru afișarea meniului și gestionarea alegerilor utilizatorului
    public void runMenu() {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start(); // Pornim cronometrul

        // Meniul pentru selectarea fișierului TSP
        System.out.println("\nSelectați fișierul TSP sau fișierul TXT cu date predefinite:");
        System.out.println("1. berlin52.tsp");
        System.out.println("2. att48.tsp");
        System.out.println("3. Fișier TXT cu date predefinite");
        System.out.print("Introduceți opțiunea: ");
        String tspChoice = scanner.nextLine();

        String filePath;
        switch (tspChoice) {
            case "1":
                filePath = "data/tsp/berlin52.tsp";
                break;
            case "2":
                filePath = "data/tsp/att48.tsp";
                break;
            case "3":
                filePath = "predefined_data.txt";
                if (!Files.exists(Paths.get(filePath))) {
                    System.err.println("Fișierul nu există.");
                    return;
                }
                break;
            default:
                System.err.println("Opțiune invalidă.");
                return;
        }
        try {
            double[][] distances = TSPLibParser.parse(filePath);

            // Meniul pentru selectarea metodei de căutare
            System.out.println("\nSelectați Metoda de Căutare:");
            System.out.println("1. Căutare în Adâncime (DFS)");
            System.out.println("2. Căutare cu Cost Minim (LCS)");
            System.out.println("3. Căutare A*");
            System.out.print("Introduceți opțiunea: ");
            String searchChoice = scanner.nextLine();

            switch (searchChoice) {
                case "1":
                    runDFS(distances);
                    break;
                case "2":
                    runLCS(distances);
                    break;
                case "3":
                    runAStar(distances);
                    break;
                default:
                    System.err.println("Opțiune invalidă.");
            }

        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului TSP: " + e.getMessage());
        }

        stopwatch.stop(); // Oprim cronometrul
        System.out.println("Timpul total de execuție: " + stopwatch.getTime() + " milisecunde");
    }

    // Metoda pentru rularea căutării în adâncime
    private void runDFS(double[][] distances) {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start(); // Pornim cronometrul

        DepthFirstSearch dfs = new DepthFirstSearch(distances);
        List<Integer> dfsRoute = dfs.findShortestRoute();
        double dfsCost = calculateCost(dfsRoute, distances);
        System.out.println("Traseu Căutare în Adâncime (DFS): " + dfsRoute);
        System.out.println("Cost Căutare în Adâncime (DFS): " + dfsCost);

        stopwatch.stop(); // Oprim cronometrul
        System.out.println("Timp de Execuție pentru DFS: " + stopwatch.getTime() + " milisecunde");
    }

    // Metoda pentru rularea căutării cu cost minim
    private void runLCS(double[][] distances) {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start(); // Pornim cronometrul

        LeastCostSearch lcs = new LeastCostSearch(distances);
        List<Integer> lcsRoute = lcs.findShortestRoute();
        double lcsCost = lcs.getMinCost();
        System.out.println("Traseu Căutare cu Cost Minim (LCS): " + lcsRoute);
        System.out.println("Cost Căutare cu Cost Minim (LCS): " + lcsCost);

        stopwatch.stop(); // Oprim cronometrul
        System.out.println("Timp de Execuție pentru LCS: " + stopwatch.getTime() + " milisecunde");
    }

    // Metoda pentru rularea căutării A*
    private void runAStar(double[][] distances) {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start(); // Pornim cronometrul

        AStarSearch aStar = new AStarSearch(distances);
        List<Integer> aStarRoute = aStar.findShortestRoute();
        double aStarCost = calculateCost(aStarRoute, distances);
        System.out.println("Traseu Căutare A*: " + aStarRoute);
        System.out.println("Cost Căutare A*: " + aStarCost);

        stopwatch.stop(); // Oprim cronometrul
        System.out.println("Timp de Execuție pentru A*: " + stopwatch.getTime() + " milisecunde");
    }

    // Metoda pentru calcularea costului unui traseu
    private double calculateCost(List<Integer> route, double[][] distances) {
        double cost = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            int city1 = route.get(i);
            int city2 = route.get(i + 1);
            cost += distances[city1][city2];
        }
        // Adăugăm costul pentru a reveni la orașul de start
        cost += distances[route.get(route.size() - 1)][route.get(0)];
        return cost;
    }
}

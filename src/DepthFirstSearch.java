import java.util.LinkedList;
import java.util.List;

public class DepthFirstSearch {
    private final double[][] distances; // Matricea de distanțe între orașe
    private final boolean[] visited; // Vectorul pentru marcarea vizitării orașelor
    private double minCost; // Costul minim
    private List<Integer> bestRoute; // Cel mai bun traseu

    // Constructorul clasei DepthFirstSearch care primește matricea de distanțe
    public DepthFirstSearch(double[][] distances) {
        this.distances = distances;
        this.visited = new boolean[distances.length]; // Inițializăm vectorul de vizitare
        this.minCost = Double.MAX_VALUE; // Inițializăm costul minim cu o valoare mare
        this.bestRoute = new LinkedList<>(); // Inițializăm lista pentru cel mai bun traseu
    }

    // Metoda pentru găsirea celui mai scurt traseu folosind căutarea în adâncime
    public List<Integer> findShortestRoute() {
        LinkedList<Integer> currentRoute = new LinkedList<>(); // Lista pentru traseul curent
        dfs(0, currentRoute, 0, 0); // Apelăm metoda dfs pentru a începe căutarea în adâncime din orașul de start
        return bestRoute; // Returnăm cel mai bun traseu găsit
    }

    // Metoda recursivă pentru căutarea în adâncime
    private void dfs(int city, LinkedList<Integer> currentRoute, double currentCost, double longestEdge) {
        currentRoute.add(city); // Adăugăm orașul curent în traseul curent
        if (currentRoute.size() == distances.length) {
            // Completăm turul revenind la orașul de start
            currentCost += distances[city][0]; // Adăugăm distanța de la orașul curent la orașul de start
            if (currentCost < minCost) {
                minCost = currentCost; // Actualizăm costul minim
                bestRoute = new LinkedList<>(currentRoute); // Actualizăm cel mai bun traseu
                bestRoute.add(0); // Adăugăm orașul de start pentru a completa turul
            }
            currentRoute.removeLast(); // Eliminăm orașul curent din traseul curent
            return;
        }

        visited[city] = true; // Marcăm orașul curent ca vizitat
        for (int nextCity = 0; nextCity < distances.length; nextCity++) {
            if (!visited[nextCity]) {
                double newCost = currentCost + distances[city][nextCity]; // Calculăm noul cost
                double newLongestEdge = Math.max(longestEdge, distances[city][nextCity]); // Calculăm cea mai lungă muchie
                dfs(nextCity, currentRoute, newCost, newLongestEdge); // Apelăm recursiv dfs pentru următorul oraș
            }
        }
        visited[city] = false; // Resetăm marcarea pentru orașul curent
        currentRoute.removeLast(); // Eliminăm orașul curent din traseul curent
    }

    // Metoda pentru obținerea costului minim
    public double getMinCost() {
        return minCost;
    }
}

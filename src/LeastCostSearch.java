import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class LeastCostSearch {
    private final double[][] distances; // Matricea de distanțe între orașe
    private double minCost; // Costul minim
    private List<Integer> bestRoute; // Cel mai bun traseu

    // Constructorul clasei LeastCostSearch care primește matricea de distanțe
    public LeastCostSearch(double[][] distances) {
        this.distances = distances;
        this.minCost = Double.MAX_VALUE; // Inițializăm costul minim cu o valoare mare
        this.bestRoute = new ArrayList<>(); // Inițializăm lista pentru cel mai bun traseu
    }

    // Metoda pentru găsirea celui mai scurt traseu
    public List<Integer> findShortestRoute() {
        // Coada de priorități pentru noduri, ordonată după cost
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.cost));
        pq.add(new Node(0, new ArrayList<>(), 0, 0)); // Adăugăm nodul de pornire în coadă

        // Parcurgem nodurile în coadă
        while (!pq.isEmpty()) {
            Node current = pq.poll(); // Extragem primul nod din coadă
            if (current.visited.size() == distances.length) {
                double totalCost = current.cost + distances[current.city][0]; // Revenire la început
                if (totalCost < minCost) {
                    minCost = totalCost;
                    bestRoute = new ArrayList<>(current.visited);
                    bestRoute.add(0); // Completăm traseul adăugând orașul de start
                }
                continue;
            }

            if (current.cost > minCost) {
                // Trecem la următoarea ramură dacă costul acumulat depășește costul minim actual
                continue;
            }

            // Parcurgem orașele nevizitate
            for (int nextCity = 0; nextCity < distances.length; nextCity++) {
                if (!current.visited.contains(nextCity)) {
                    // Creăm o nouă listă de orașe vizitate și adăugăm orașul curent în aceasta
                    List<Integer> newVisited = new ArrayList<>(current.visited);
                    newVisited.add(nextCity);
                    double newCost = current.cost + distances[current.city][nextCity]; // Calculăm noul cost
                    // Adăugăm noul nod în coadă cu informațiile actualizate
                    pq.add(new Node(nextCity, newVisited, newCost, Math.max(current.longestEdge, distances[current.city][nextCity])));
                }
            }
        }

        return bestRoute; // Returnăm cel mai bun traseu găsit
    }

    // Metoda pentru obținerea costului minim
    public double getMinCost() {
        return minCost;
    }

    // Clasa internă Node pentru reprezentarea nodurilor în coadă
    private static class Node {
        int city; // Orașul curent
        List<Integer> visited; // Lista de orașe vizitate până la acest nod
        double cost; // Costul total pentru a ajunge la acest nod
        double longestEdge; // Cea mai lungă distanță între orașele parcurse până la acest nod

        // Constructorul pentru crearea unui nou nod
        public Node(int city, List<Integer> visited, double cost, double longestEdge) {
            this.city = city;
            this.visited = visited;
            this.cost = cost;
            this.longestEdge = longestEdge;
        }
    }
}

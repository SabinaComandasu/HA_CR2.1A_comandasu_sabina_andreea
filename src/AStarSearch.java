import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class AStarSearch {
    private double[][] distances;

    // Constructorul clasei AStarSearch care primește matricea de distanțe
    public AStarSearch(double[][] distances) {
        this.distances = distances;
    }

    // Metoda pentru găsirea celui mai scurt traseu
    public List<Integer> findShortestRoute() {
        // Prioritatea cozii pentru noduri, ordonată după costul estimat
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.estimatedCost));
        pq.add(new Node(0, new ArrayList<>(), 0, 0, heuristic(0))); // Adăugăm nodul de pornire în coadă

        // Parcurgem nodurile în coadă
        while (!pq.isEmpty()) {
            Node current = pq.poll(); // Extragem primul nod din coadă
            if (current.visited.size() == distances.length) {
                current.visited.add(0);
                return current.visited; // Dacă toate orașele au fost vizitate, returnăm traseul completat
            }

            // Parcurgem orașele nevizitate
            for (int nextCity = 0; nextCity < distances.length; nextCity++) {
                if (!current.visited.contains(nextCity)) {
                    // Creăm o nouă listă de orașe vizitate și adăugăm orașul curent în aceasta
                    List<Integer> newVisited = new ArrayList<>(current.visited);
                    newVisited.add(nextCity);
                    double newCost = current.cost + distances[current.city][nextCity]; // Calculăm noul cost
                    double newLongestEdge = Math.max(current.longestEdge, distances[current.city][nextCity]); // Calculăm noulă cea mai lungă distanță
                    double estimatedCost = newCost + heuristic(nextCity); // Calculăm costul estimat al traseului
                    // Adăugăm noul nod în coadă cu informațiile actualizate
                    pq.add(new Node(nextCity, newVisited, newCost, newLongestEdge, estimatedCost));
                }
            }
        }
        return new ArrayList<>(); // Returnăm o listă goală în cazul în care nu găsim niciun traseu
    }

    // Metoda heuristică pentru estimarea costului de la un oraș la cel mai apropiat oraș nevizitat
    private double heuristic(int city) {
        double minEdge = Double.MAX_VALUE;
        // Parcurgem distanțele de la orașul dat la celelalte orașe
        for (int i = 0; i < distances.length; i++) {
            if (i != city) {
                minEdge = Math.min(minEdge, distances[city][i]);
            }
        }
        return minEdge; // Returnăm cea mai mică distanță găsită
    }

    // Clasa internă Node pentru reprezentarea nodurilor în coadă
    private static class Node {
        int city; // Orașul curent
        List<Integer> visited; // Lista de orașe vizitate până la acest nod
        double cost; // Costul total pentru a ajunge la acest nod
        double longestEdge; // Cea mai lungă distanță între orașele parcurse până la acest nod
        double estimatedCost; // Costul estimat total pentru a ajunge la nodul final

        // Constructorul pentru crearea unui nou nod
        public Node(int city, List<Integer> visited, double cost, double longestEdge, double estimatedCost) {
            this.city = city;
            this.visited = visited;
            this.cost = cost;
            this.longestEdge = longestEdge;
            this.estimatedCost = estimatedCost;
        }
    }
}

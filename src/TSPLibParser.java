import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSPLibParser {
    // Metoda pentru parsarea fișierului TSP și obținerea matricei de distanțe
    public static double[][] parse(String filePath) throws IOException {
        // Inferăm tipul de fișier din extensia fișierului
        String fileType = getFileType(filePath);

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        List<double[]> cities = new ArrayList<>();

        // Verificăm dacă tipul de fișier este TSP sau TXT
        boolean isTSP = fileType.equalsIgnoreCase("TSP");

        while ((line = br.readLine()) != null) {
            if (isTSP) {
                if (line.startsWith("NODE_COORD_SECTION")) {
                    break;
                }
            } else {
                if (line.startsWith("NODE_COORD_SECTION") || line.startsWith("NODE_COORD_SECTION ")) {
                    break;
                }
            }
        }

        while ((line = br.readLine()) != null) {
            if (line.equals("EOF")) {
                break;
            }
            String[] parts = line.trim().split("\\s+");
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            cities.add(new double[]{x, y});
        }
        br.close();

        int n = cities.size();
        double[][] distances = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                double dist = Math.sqrt(Math.pow(cities.get(i)[0] - cities.get(j)[0], 2) +
                        Math.pow(cities.get(i)[1] - cities.get(j)[1], 2));
                distances[i][j] = dist;
                distances[j][i] = dist;
            }
        }
        return distances; // Returnăm matricea de distanțe
    }

    // Metoda pentru obținerea tipului de fișier din calea fișierului
    private static String getFileType(String filePath) {
        String[] parts = filePath.split("\\.");
        if (parts.length > 0) {
            return parts[parts.length - 1].toUpperCase();
        }
        return ""; // Întoarcem un șir gol în cazul în care extensia fișierului nu este găsită
    }
}

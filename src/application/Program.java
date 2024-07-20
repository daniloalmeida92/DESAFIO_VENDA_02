package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    private static int num;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();

        List<Sale> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();

            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Sale(Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[1]),
                        fields[2],
                        Integer.parseInt(fields[3]),
                        Double.parseDouble(fields[4])));

                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        list.sort(Comparator.comparingDouble(Sale::averagePrice).reversed());
        System.out.println();

        Set<String> sellers = list.stream()
                .map(Sale::getSeller)
                .collect(Collectors.toSet());

        Map<String, Double> totalSeller = new HashMap<>();

        for (String seller : sellers) {
            double totalSales = list.stream()
                    .filter(x -> x.getSeller().equals(seller))
                    .mapToDouble(Sale::getTotal)
                    .sum();
            totalSeller.put(seller, totalSales);
        }

        System.out.println("Total de vendas por vendedor:");
        totalSeller.forEach((Cliente, total) ->
                System.out.println(Cliente + " - R$ " + String.format("%.2f", total)));


        sc.close();
    }
}
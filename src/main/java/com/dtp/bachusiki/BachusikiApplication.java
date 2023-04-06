package com.dtp.bachusiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class BachusikiApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BachusikiApplication.class, args);

        String pathOfCSVFile = "Bachusiki.csv";

        File inputFile = ResourceUtils.getFile(pathOfCSVFile);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        File outputFile = new File("informacje-o-bachusikach.txt");

        List<String[]> data = Files.lines(Paths.get(pathOfCSVFile))
                .map(line -> line.split(","))
                .toList();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            PrintStream printStream = new PrintStream(fileOutputStream);
            System.setOut(printStream);

            System.out.println("Liczba wszystkich figurek: " + countFigures(data));
            System.out.println("\nLista miejsc i liczba figurek, które można w nich znaleźć: ");
            displayLocationsAndNumber(data);
            System.out.println("\nLista autorów Bachusików: ");
            display(authors(data));
            System.out.println("\nLista fundatorów Bachusików: ");
            display(sponsors(data));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("\nNajstarszy Bachusik:");
            findEldest(data, formatter);
            System.out.println("\nNajmłodszy Bachusik:");
            findYoungest(data, formatter);

            printStream.close();

            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.out.println("Successfully wrote to file: " + outputFile);

        } catch (IOException e) {
            System.out.println("An error occurred during writing data into file.");
            e.printStackTrace();
        }

        reader.close();
    }

    private static int countFigures(List<String[]> data) {
        return data.size();
    }

    private static void display(List<String> list) {
        for (String element : list) {
            System.out.println(element);
        }
    }

    private static List<String> locations(List<String[]> data) {
        return data.stream()
                .map(line -> line[1])
                .distinct()
                .toList();
    }

    private static void displayLocationsAndNumber(List<String[]> data) {
        Map<String, Long> locationCount = data
                .stream()
                .collect(Collectors.groupingBy(line -> line[1],
                        Collectors.counting()));

        for (Map.Entry<String, Long> entry : locationCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static List<String> authors(List<String[]> data) {
        return data.stream()
                .map(line -> line[2])
                .distinct()
                .toList();
    }

    private static List<String> sponsors(List<String[]> data) {
        return data.stream()
                .map(line -> line[3])
                .distinct()
                .toList();
    }

    private static void findYoungest(List<String[]> data, DateTimeFormatter formatter) {
        String[] mostRecent = data.stream()
                .max(Comparator.comparing(line -> LocalDate.parse(line[4], formatter)))
                .orElse(null);

        if (mostRecent != null) {
            ArrayList<Integer> date = parseToAbsoluteValue(mostRecent[4]);
            displayInfoAboutBacchus(mostRecent, date.get(0), date.get(1), date.get(2));
        } else {
            System.out.println("Nie znaleziono najmłodszego Bachusika.");
        }
    }

    private static void findEldest(List<String[]> data, DateTimeFormatter formatter) {
        String[] oldest = data.stream()
                .min(Comparator.comparing(line -> LocalDate.parse(line[4], formatter)))
                .orElse(null);

        if (oldest != null) {
            ArrayList<Integer> date = parseToAbsoluteValue(oldest[4]);
            displayInfoAboutBacchus(oldest, date.get(0), date.get(1), date.get(2));
        } else {
            System.out.println("Nie znaleziono najstarszego Bachusika.");
        }
    }

    private static ArrayList<Integer> parseToAbsoluteValue(String date){
        Period period = Period.between(LocalDate.now(), LocalDate.parse(date));
        return new ArrayList<>(Arrays.asList(Math.abs(period.getDays()), Math.abs(period.getMonths()), Math.abs(period.getYears())));
    }

    private static void displayInfoAboutBacchus(String[] line, int year, int month, int day){
        System.out.println("Bachusik: " + line[0]);
        System.out.println("Wiek: " + year + " lat " + month + " miesięcy " + day + " dni");
        System.out.println("Data odsłonięcia: " + line[4]);
    }

}

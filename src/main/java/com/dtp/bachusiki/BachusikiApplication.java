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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
            displayAuthors(data);
            System.out.println("\nLista fundatorów Bachusików: ");
            displaySponsors(data);
            System.out.println("\nNajstarszy Bachusik:");
            findEldest(data);
            System.out.println("\nNajmłodszy Bachusik:");
            findYoungest(data);

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

    private static void displayLocations(List<String[]> data) {
        List<String> locationsOfFigures = data.stream()
                .map(line -> line[1])
                .distinct()
                .toList();

        for (String location : locationsOfFigures) {
            System.out.println(location);
        }
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

    private static void displayAuthors(List<String[]> data) {
        List<String> authorsOfFigures = data.stream()
                .map(line -> line[2])
                .distinct()
                .toList();

        for (String author : authorsOfFigures) {
            System.out.println(author);
        }
    }

    private static void displaySponsors(List<String[]> data) {
        List<String> sponsorsOfFigures = data.stream()
                .map(line -> line[3])
                .distinct()
                .toList();

        for (String sponsor : sponsorsOfFigures) {
            System.out.println(sponsor);
        }
    }

    private static void findYoungest(List<String[]> data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] mostRecent = data.stream()
                .max(Comparator.comparing(line -> LocalDate.parse(line[4], formatter)))
                .orElse(null);

        if (mostRecent != null) {
            Period period = Period.between(LocalDate.now(), LocalDate.parse(mostRecent[4]));
            int absoluteDays = Math.abs(period.getDays());
            int absoluteMonths = Math.abs(period.getMonths());
            int absoluteYears = Math.abs(period.getYears());

            System.out.println("Bachusik: " + mostRecent[0]);
            System.out.println("Wiek: " + absoluteYears + " lat " + absoluteMonths + " miesięcy " + absoluteDays + " dni");
            System.out.println("Data odsłonięcia: " + mostRecent[4]);
        } else {
            System.out.println("Nie znaleziono najmłodszego Bachusika.");
        }
    }

    private static void findEldest(List<String[]> data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] oldest = data.stream()
                .min(Comparator.comparing(line -> LocalDate.parse(line[4], formatter)))
                .orElse(null);

        if (oldest != null) {
            Period period = Period.between(LocalDate.now(), LocalDate.parse(oldest[4]));
            int absoluteDays = Math.abs(period.getDays());
            int absoluteMonths = Math.abs(period.getMonths());
            int absoluteYears = Math.abs(period.getYears());

            System.out.println("Bachusik: " + oldest[0]);
            System.out.println("Wiek: " + absoluteYears + " lat " + absoluteMonths + " miesięcy " + absoluteDays + " dni");
            System.out.println("Data odsłonięcia: " + oldest[4]);
        } else {
            System.out.println("Nie znaleziono najmłodszego Bachusika.");
        }
    }

}

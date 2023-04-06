package com.dtp.bachusiki.REST;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BacchusService {
    private static List<Bacchus> bacchusList;
    private static Long id = 1L;

    @PostConstruct
    public static void loadDataFromCSV() throws IOException, CsvValidationException {
        String pathOfCSVFile = "Bachusiki.csv";
        InputStream inputStream = new FileInputStream(ResourceUtils.getFile(pathOfCSVFile));
        Reader reader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReaderBuilder(reader).build();

        bacchusList = new ArrayList<>();

        String[] line;
        while ((line = csvReader.readNext()) != null) {
            Bacchus bacchus = new Bacchus(id, line[0], line[1], line[2], line[3], line[4]);
            bacchusList.add(bacchus);
            id++;
        }
    }

    public List<Bacchus> getBacchusList() {
        return bacchusList;
    }

    public List<Bacchus> createBacchus(final Bacchus newBacchus) {
        Optional<Bacchus> lastId = getBacchusList()
                .stream()
                .reduce((first, last) -> last);
        if (lastId.isPresent()) {
            Long nextId = lastId.get().getId() + 1;
            bacchusList.add(new Bacchus(nextId, newBacchus.getName(), newBacchus.getLocation(), newBacchus.getAuthor(), newBacchus.getSponsor(), newBacchus.getDate()));
        }
        return bacchusList;
    }

    public List<Bacchus> updateBacchus(final Long id, final Bacchus updatedBacchus) {
        Optional<Bacchus> optionalData = getBacchusList().stream()
                .filter(bacchus -> bacchus.getId().equals(id))
                .findFirst();

        if (optionalData.isPresent()) {
            Bacchus bacchusEdited = optionalData.get();
            bacchusEdited.setName(updatedBacchus.getName());
            bacchusEdited.setLocation(updatedBacchus.getLocation());
            bacchusEdited.setAuthor(updatedBacchus.getAuthor());
            bacchusEdited.setSponsor(updatedBacchus.getSponsor());
            bacchusEdited.setDate(updatedBacchus.getDate());

            int index = bacchusList.indexOf(bacchusEdited);
            if (index != -1) {
                bacchusList.set(index, bacchusEdited);
            }
        }

        return bacchusList;
    }

    public List<Bacchus> deleteBacchus(final Long id) {
        return bacchusList = getBacchusList().stream()
                .filter(bacchus -> !bacchus.getId().equals(id))
                .collect(Collectors.toList());
    }

}

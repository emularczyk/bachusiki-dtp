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
    private List<Bacchus> bacchusList;

    @PostConstruct
    public void loadDataFromCSV() throws IOException, CsvValidationException {
        String pathOfCSVFile = "Bachusiki.csv";
        InputStream inputStream = new FileInputStream(ResourceUtils.getFile(pathOfCSVFile));
        Reader reader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReaderBuilder(reader).build();

        bacchusList = new ArrayList<>();

        String[] line;
        Long id = 1L;
        while ((line = csvReader.readNext()) != null) {
            Bacchus bacchus = new Bacchus(id, line[0], line[1], line[2], line[3], line[4]);
            bacchusList.add(bacchus);
            id++;
        }
    }

    public List<Bacchus> getBacchusList() {
        return bacchusList;
    }

    public List<Bacchus> createBacchus(String name, String location, String author, String sponsor, String date) {
        Optional<Bacchus> lastId = getBacchusList()
                .stream()
                .reduce((first, last) -> last);
        if (lastId.isPresent()) {
            Long nextId = lastId.get().getId() + 1;
            bacchusList.add(new Bacchus(nextId, name, location, author, sponsor, date));
        }
        return bacchusList;
    }

    public List<Bacchus> updateBacchus(Long id, String name, String location, String author, String sponsor, String date) {
        Optional<Bacchus> optionalData = getBacchusList().stream()
                .filter(bacchus -> bacchus.getId().equals(id))
                .findFirst();

        if (optionalData.isPresent()) {
            Bacchus bacchusEdited = optionalData.get();
            bacchusEdited.setName(name);
            bacchusEdited.setLocation(location);
            bacchusEdited.setAuthor(author);
            bacchusEdited.setSponsor(sponsor);
            bacchusEdited.setDate(date);

            int index = bacchusList.indexOf(bacchusEdited);
            if (index != -1) {
                bacchusList.set(index, bacchusEdited);
            }
        }

        return bacchusList;
    }

    public List<Bacchus> deleteBacchus(Long id) {
        return bacchusList = getBacchusList().stream()
                .filter(bacchus -> !bacchus.getId().equals(id))
                .collect(Collectors.toList());
    }

}

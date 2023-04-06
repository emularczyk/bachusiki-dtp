package com.dtp.bachusiki.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
public class BacchusController {
    @Autowired
    private final BacchusService bacchusService;

    public BacchusController(BacchusService bacchusService) {
        this.bacchusService = bacchusService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bacchus> getBacchusById(@PathVariable final Long id) {
        Optional<Bacchus> optionalData = bacchusService.getBacchusList().stream()
                .filter(data -> data.getId().equals(id))
                .findFirst();

        if (optionalData.isPresent()) {
            return ResponseEntity.ok(optionalData.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/everything")
    public ResponseEntity<List<Bacchus>> getAll() {
        List<Bacchus> bacchusList = bacchusService.getBacchusList();

        if (!bacchusList.isEmpty()) {
            return ResponseEntity.ok(bacchusList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<List<Bacchus>> createBacchus(@RequestBody final Bacchus createdBacchus) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(createdBacchus.getDate(), formatter);
            System.out.println("Valid date: " + parsedDate);

            List<Bacchus> bacchusList = bacchusService.createBacchus(createdBacchus);
            if (!bacchusList.isEmpty()) {
                return ResponseEntity.ok(bacchusList);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date: " + createdBacchus.getDate());
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<List<Bacchus>> updateBacchus(@PathVariable final Long id, @RequestBody final Bacchus updatedBacchus) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(updatedBacchus.getDate(), formatter);
            System.out.println("Valid date: " + parsedDate);

            List<Bacchus> bacchusList = bacchusService.updateBacchus(id, updatedBacchus);
            if (!bacchusList.isEmpty()) {
                return ResponseEntity.ok(bacchusList);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date: " + updatedBacchus.getDate());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<Bacchus>> deleteBacchus(@PathVariable final Long id) {
        List<Bacchus> bacchusList = bacchusService.deleteBacchus(id);

        if (!bacchusList.isEmpty()) {
            return ResponseEntity.ok(bacchusList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}

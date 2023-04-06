## Info
Java 17, Spring Boot, Maven
Dependencies: Spring Web, OpenCSV

## PLIK .CSV

Utworzony został na podstawie spisu Bachusików ze strony zielonogorskiebachusiki.pl/
Daty odsłonięcia figurek zostały wymyślone na potrzeby zadania. 
Przyjmuje się, że lista Bachusików składa się z unikatowych elementów, zapisanych w formacie 'name,location,author,sponsor,date', format daty: 'YYYY-MM-DD'.

Plik .CSV znajduje się w katalogu głównym projektu.
Plik tekstowy z przetworzonymi danymi zostaje utworzony automatycznie po uruchomieniu programu.


## PRZYKŁADY WYWOŁAŃ CRUD

Należy uruchomić aplikację i ustawić header Content-Type: application/json

GET

Pobranie kolekcji wszystkich Bachusików:
http://localhost:8080/everything

Pobranie Bachusika o id = 1:
http://localhost:8080/1

CREATE

Dodanie do kolekcji Bachusika:
http://localhost:8080/create

Przykładowy JSON:
    {
        "name": "Test",
        "location": "Ulica",
        "author": "Autor",
        "sponsor": "Sponsor",
        "date": "2023-04-06"
    }

UPDATE

Modyfikacja Bachusika o id = 2:
http://localhost:8080/update/2

Przykładowy JSON:
    {
        "name": "Test2",
        "location": "Ulica",
        "author": "Autor",
        "sponsor": "Sponsor",
        "date": "2023-04-06"
    }

DELETE

Usunięcie Bachusika o id = 2:
http://localhost:8080/delete/2
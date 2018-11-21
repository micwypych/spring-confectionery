# spring-confectionery

## uruchamianie projektu

uruchomienie projektu odbywa się przez skrypt osłonowy **gradlew** (gradlew.bat pod windowsem), zadanie `bootRun` uruchamia aplikację:
```bash
./gradlew bootRun
```

w trakcie uruchamiania serwera, wypisywane są informacje o ładowaniu i uruchamianiu poszczególnych składników systemu, serwer jest w pełni gotowy po wypisaniu informacji o uruchomieniu całej apliacji (`Started ConfectioneryApplication`), co z dokładnością do czasu powinno wyglądać następująco:
```
2018-11-21 14:16:47.113  INFO 15489 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2018-11-21 14:16:47.119  INFO 15489 --- [           main] p.e.a.k.d.c.ConfectioneryApplication     : Started ConfectioneryApplication in 10.602 seconds (JVM running for 11.883)
<=========----> 75% EXECUTING [38s]
> :bootRun
```
następnie można zacząć kierwoać żadania do serwera.

## sending request to the server with curl

Komunikacyja z serwerem wykorzystuje protokół [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Message_format). Uformowanie żądania zgodnego z protokołem HTTP można osiągnąć za pomocą programu **curl**.
Obowiązkowym arguementem jest polecenia jest URI zasobu. 
Za pomocą przełącznika `--request HTTPMETHOD` możemy ustawić [metodę HTTP](https://www.w3schools.com/tags/ref_httpmethods.asp).
Za pomocą serii przełączników `--header 'Key: value'` możemy ustawić kolejne [nagłówki żądania](https://en.wikipedia.org/wiki/List_of_HTTP_header_fields#Request_fields). 
Za pomocą przełącznika `--data 'dane'` możemy ustawić dane wysyłane wraz z żadaniem do serwera.

 - żądanie o zwrócenie listy wszystkich czekoladek przechowywanych w aplikacji
```bash
curl http://localhost:8080/chocolates
```
 - żadnie dodania nowej czekoladki
```bash
curl --request POST http://localhost:8080/chocolates --header 'Content-Type: application/json' --data '{"id":"ch8","name":"kokosanka","nuts":"brak","chocolate":"gorzka","ganache":"kokosowe","description":"szeroki opis czekoladki","mass":18}'
```
 - żądanie zwrócenia danych czekoladki o identifykatorze 'ch8'
```bash
curl http://localhost:8080/chocolates/ch8
```
 - żądanie usunięcia czekoladki o identifykatorze 'ch8'
```bash
curl --request DELETE http://localhost:8080/chocolates/ch8
```

## opis projektu

### obsługa żądań

Nadchodzące żadania są filtrowane, a następnie uruchamiana jest pasująca procedura obsługi. 
Aktulanie zarejestrowane punkty wejścia są wyświetlane w logach aplikacji przy starcie serwera np:

```
2018-11-21 15:20:46.201  INFO 20073 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/chocolates],methods=[GET]}" onto public java.util.List<pl.edu.agh.kis.databases.confectionery.domain.Chocolate> pl.edu.agh.kis.databases.confectionery.infrastructure.ChocolateResource.retrieveAllChocolates()
2018-11-21 15:20:46.204  INFO 20073 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/chocolates/{id}],methods=[GET]}" onto public org.springframework.hateoas.Resource<pl.edu.agh.kis.databases.confectionery.domain.Chocolate> pl.edu.agh.kis.databases.confectionery.infrastructure.ChocolateResource.retrieveChocolate(java.lang.String)
```

informuje o tym, że żadania do URI **/chocolates** z metodą **GET** są obsługiwane przez metodę `public List<Chocolate> retrieveAllChocolates()` z klasy [ChocolateResource](src/main/java/pl/edu/agh/kis/databses/confectionary/infrastructure/ChocolateResource.java) znajdującą się w pakiecie `pl.edu.agh.kis.databases.confectionery.infrastructure`. Z kolei żądanie **GET** do URI **/chocolates/{id}**, gdzie zamiast ciągu `{id}` zostanie podstawiony inny ciąg są obsługiwane przez metodę `public Resource<Chocolate> retrieveChocolate(java.lang.String)` z tej samej klasy.

### opis mapowania obiektowo-relacyjnego

Projekt wykorzystuje standardowe adnotacja JPA do opisania sposobu mapowania klas na relacje. Klasy mapowane w projekcie znajdują się w pakiecie `pl.edu.agh.kis.databases.confectionery.domain` i jest to m.in. klasa [Chocolate](src/main/java/pl/edu/agh/kis/databses/confectionary/domain/Chocolate.java).

### opis dostępu do do bazy danych

Dostęp do bazy danych odbywa się za pomocą instancji implementującej interface [ChocolateRepository](src/main/java/pl/edu/agh/kis/databses/confectionary/infrastructure/ChocolateRepository.java), instancja ta jest tworzona dynamicznie przez bibliotekę [spring-data-jpa](https://spring.io/guides/gs/accessing-data-jpa/). 


## inny podobny projekty 

[jOOQ + spark](http://home.agh.edu.pl/~mwypych/doku.php?id=zimowy:java2016:labs:projekt2)

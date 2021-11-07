# Polnilne-postaje

Za zagon PostgreSQL podatkovne baze znotraj Docker okolja uporabimo naslednji ukaz:

`docker run -d --name PolnilnePostaje -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=polnilnepostaje -p 5432:5432 postgres:13`

Za prevajanje aplikacije uporabimo ukaz: `mvn clean package`

Za izvajanje dobljene .jar datoteke uporabimo ukaz: `java -jar .\api\target\api-1.0.0-SNAPSHOT.jar`

![Podatkovni model](MODEL.png "Podatkovni model")

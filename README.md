# JavaEE-AnnotationPhotos
# DURAND Virginie, GRIMARD Bettina & PITARCH Orlane

Requêtes SPARQL possibles avec notre ontologie

---

* Photos d'une personne spécifique : Yann
 ```sql
  SELECT ?pic
  WHERE {
    ?pic a ex:Picture;
        ex:subject ex:Yann.
  }
   ```

* Photos de plusieurs personnes : Margaux et Ophélie
   ```sql
  SELECT ?pic
  WHERE {
   ?pic a ex:Picture;
       ex:subject ex:Margaux;
       ex:subject ex:Ophelie.
  }
   ```

* Photos où il y a des personnes identifiées dessus   
 ```sql
  SELECT ?pic
  WHERE {
    ?pic a ex:Picture;
             ex:subject ?p.
    ?p a ex:People.
  }
   ```
   
* Photos où il n'y a personne dessus
 ```sql
  SELECT ?pic
  WHERE {
      ?pic a ex:Picture;
      ?s a ex:People.
      FILTER NOT EXISTS{?pic ex:subject ?s}
  }
   ```

* Photos des personnes qui sont amis  => ???????????
 ```sql
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT DISTINCT ?pic
WHERE {
  ?pic a ex:Picture;
      ex:subject ?People;
      ex:subject ?People.
}
 ```

* Photos de pot de départ 
 ```sql
  SELECT DISTINCT ?picture ?event
  WHERE {
    ?picture a ex:Picture;
        ex:event ?event.
    ?event a ex:Farewell_party.
  }
   ```

* Photos de crémaillère
 ```sql
  SELECT DISTINCT ?picture ?event
  WHERE {
    ?picture a ex:Picture;
        ex:event ?event.
    ?event a ex:House-warming_party.
  }
   ```

* Photos de crémaillère d'une personne
 ```sql
  SELECT DISTINCT ?picture ?event
  WHERE {
    ?picture a ex:Picture;
        ex:event ?event.
    ?people a ?People.

    ?event a ex:House-warming_party;
		ex:.
  }
   ```

* Photos d'un évènement : Voyage à Londres
 ```sql
  SELECT DISTINCT ?picture ?event
  WHERE {
    ?picture a ex:Picture;
        ex:event ?event.
    ?event a ex:Trip_to_London.
  }
 ```

* Photos d'un festival : Festival de Cannes
 ```sql
  SELECT DISTINCT ?picture
  WHERE {
    ?picture a ex:Picture;
             ex:event ?event.
    ?event a ex:Cannes_festival.
  }
 ```
* Photos d'un salon: salon de l'auto
   ```sql
  SELECT DISTINCT ?picture
  WHERE {
    ?picture a ex:Picture;
             ex:event ?event.
  
    ?event a ?eventType.
    ?events rdfs:subClassOf ex:Motor_show.
  }
   ```
  
* Photos d’un lieu particulier : Grenoble 
 ```sql
SELECT DISTINCT ?pic
WHERE {
   ?pic a ex:Picture;
    ex:Place ?city.
     ?city a ex:Grenoble.
}
 ```

* Photos d’un monument particulier : Eiffel_tower 
 ```sql
SELECT DISTINCT ?pic
WHERE {
   ?pic a ex:Picture;
    ex:Monument ?m.
     ?m a ex:Eiffel_tower.
}
 ```
 
* Photos prises durant une certaine période de temps : toutes celles prises entre le 24 et le 31 décembre 2019
   ```sql
  SELECT DISTINCT ?pic ?when
  WHERE {
    ?pic a ex:Picture;
             ex:when ?when.
    FILTER (?when > "2019-12-24T00:00:00"^^xsd:dateTime)
    FILTER (?when < "2019-12-31T23:59:59"^^xsd:dateTime)
  }
 ```



  
  

# JavaEE-AnnotationPhotos
# DURAND Virginie, GRIMARD Bettina & PITARCH Orlane

Requêtes SPARQL possibles avec notre ontologie, utiliser : 

PREFIX ex:<<http://www.semanticweb.org/ontologies/WebSemProject#>>

---

* Photos d'une personne spécifique : Yann
 ```sql
  SELECT DISTINCT ?pic
  WHERE {
    ?pic a ex:Picture;
        ex:Subject ex:Yann.
  }
   ```

* Photos de plusieurs personnes : Yann et Maxence
 ```sql
 SELECT DISTINCT ?pic
  WHERE {
   ?pic a ex:Picture;
       ex:Subject ex:Yann;
       ex:Subject ex:Maxence.
  }
   ```

* Photos où il y a des personnes identifiées dessus   
 ```sql
SELECT DISTINCT ?pic
  WHERE {
    ?pic a ex:Picture;
             ex:Subject ?p.
    ?p a ex:People.
  }
   ```
   
* Photos où il n'y a personne dessus
 ```sql
 SELECT DISTINCT ?pic
 WHERE {
     ?pic a ex:Picture.
     FILTER NOT EXISTS{
    	?pic ex:Subject ?s.
    	?s a ex:People.}
 }
   ```

* Photos de pot de départ 
 ```sql
SELECT ?picture
WHERE {
  ?picture a ex:Picture;
      ex:Events ?event.
  ?event a ex:Farewell_party.
}
   ```

* Photos de crémaillère
 ```sql
SELECT DISTINCT ?picture 
  WHERE {
    ?picture a ex:Picture;
        ex:Events ?event.
  ?event a ex:House-warming_party.
  }
   ```

 * Photos de crémaillère décrivant Yann
 ```sql
 SELECT ?pic
 WHERE {
    ?pic a ex:Picture;
	ex:Events ?event;
	ex:Subject ex:Yann.
  ?event a ex:House-warming_party;
  	
 }
  ```

* Photos de vacances :
 ```sql
 SELECT DISTINCT ?picture ?event
  WHERE {
    ?picture a ex:Picture;
        ex:Events ?e.
	?e a ex:Holidays.
  }
 ```

* Photos d'un show :
 ```sql
  SELECT DISTINCT ?picture
  WHERE {
    ?picture a ex:Picture;
         ex:Events ?e.
    ?e a ex:Show.
  }
   ```
  
* Photos d’un lieu particulier : Grenoble 
 ```sql
SELECT DISTINCT ?pic
WHERE {
   ?pic a ex:Picture;
    	ex:Where ex:Grenoble.
}
 ```

* Photos d’un monument particulier : Eiffel_tower 
 ```sql
SELECT ?pic 
WHERE {
   ?pic a ex:Picture;
        ex:Where ex:Eiffel_Tower.
}
 ```
 
* Photos prises durant une certaine période de temps : toutes celles prises entre le 24 et le 31 décembre 2019
 ```sql
  SELECT DISTINCT ?pic ?when
  WHERE {
    ?pic a ex:Picture;
             ex:Date ?when.
    FILTER (?when > "2019-12-24T00:00:00"^^xsd:dateTime)
    FILTER (?when < "2019-12-31T23:59:59"^^xsd:dateTime)
  }
  ```
  
* Selfie(s)
 ```sql
  SELECT DISTINCT ?pic 
  WHERE {
    ?pic a ex:Picture;
             ex:Subject ?p;
	     ex:Author ?p.
  }
  ```
  
* Photos d'un type d'animal : dog
```sql
SELECT DISTINCT ?pic
WHERE {
   ?pic a ex:Picture;
    ex:Subject ?s.
    ?s a ex:dog.
}
  ```
  
* Photos avec un ami de Maxence
```sql
SELECT ?pic
 WHERE {
    ?pic a ex:Picture;
	 ex:Subject ?ami.
  	ex:Maxence ex:Friend ?ami.
 }
   ```
   


  

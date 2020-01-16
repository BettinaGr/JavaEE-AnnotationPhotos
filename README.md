# JavaEE-AnnotationPhotos
# DURAND Virginie, GRIMARD Bettina & PITARCH Orlane

Requêtes SPARQL possibles avec notre ontologie

---

* Photos d'une personne spécifique : Yann

  SELECT ?pic
  WHERE {
    ?pic a ex:Picture;
        ex:subject ex:Yann.
  }

* Photos de plusieurs personnes : Margaux et Ophélie
  
  SELECT ?pic
  WHERE {
   ?pic a ex:Picture;
       ex:subject ex:Margaux;
       ex:subject ex:Ophelie.
  }

* Photos où il y a des personnes identifiées dessus   
  SELECT ?pic
  WHERE {
    ?pic a ex:Picture;
             ex:subject ?p.
    ?p a ex:People.
  }


* Photos où il n'y a personne dessus

  SELECT ?pic
  WHERE {
      ?pic a ex:Picture;
      ?s a ex:People.
      FILTER NOT EXISTS{?pic ex:subject ?s}
  }

* Photos des personnes qui sont amis  => ???????????

PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT DISTINCT ?pic
WHERE {
  ?pic a ex:Picture;
      ex:subject ?People;
      ex:subject ?People.

}

* Photos de pot de départ 

  SELECT DISTINCT ?picture ?event
  WHERE {
    ?picture a ex:Picture;
        ex:event ?event.
    ?event a ex:Farewell_party.
  }

* Photos de crémaillère

  SELECT DISTINCT ?picture ?event
  WHERE {
    ?picture a ex:Picture;
        ex:event ?event.
    ?event a ex:House-warming_party.
  }

* Photos de crémaillère d'une personne

  SELECT DISTINCT ?picture ?event
  WHERE {
    ?picture a ex:Picture;
        ex:event ?event.
    ?people a ?People.

    ?event a ex:House-warming_party;
		ex:.
  }

* Photos d'un évènement : Voyage à Londres 
  

  SELECT DISTINCT ?picture ?event
  WHERE {
    ?picture a ex:Picture;
        ex:event ?event.
    ?event a ex:Trip_to_London.
  }


* Photos d'un festival : Festival de Cannes
  

  SELECT DISTINCT ?picture
  WHERE {
    ?picture a ex:Picture;
             ex:event ?event.
    ?event a ex:Cannes_festival.


* Photos d'un salon : Salon de l'auto
  
  SELECT DISTINCT ?picture
  WHERE {
    ?picture a ex:Picture;
             ex:event ?event.
  
    ?event a ?eventType.
    ?events rdfs:subClassOf ex:Motor_show.
  }

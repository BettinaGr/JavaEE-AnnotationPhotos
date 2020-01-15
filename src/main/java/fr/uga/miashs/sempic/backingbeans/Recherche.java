/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.SempicException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import fr.uga.miashs.sempic.rdf.SempicRDFStore;
import fr.uga.miashs.sempic.rdf.Namespaces;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import org.apache.jena.rdf.model.Resource;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
/**
 *
 * @author Orlane
 */
@Named
@ViewScoped
public class Recherche implements Serializable{
    private String userId;
    
    private String who;
    
    private String where;
    
    private String what;
    
    private String takenBy;
    
    private List<Resource> depicts;
    
    private SempicRDFStore rdf = new SempicRDFStore();
    
    private Resource res;
    
    public void setUserId(String id) {
        System.out.println("set id list " + id); 
        this.userId = id;
    }
     
    public String getUserId() {
        return this.userId;
    }
    
    public void setWho(String who) { 
        this.who = who;
    }
     
    public String getWho() {
        return this.who;
    }
    public void setWhat(String what) {
        System.out.println("set id list " + what); 
        this.what = what;
    }
     
    public String getWhat() {
        return this.what;
    }
    
    public void setWhere(String where) {
        System.out.println("set id list " + where); 
        this.where = where;
    }
    
    public String getWhere() {
        return this.where;
    }
     
    public void setTakenBy(String takenBy) {
        System.out.println("set id list " + takenBy); 
        this.takenBy = takenBy;
    }
    public String getTakenBy() {
        return this.takenBy;
    }
    
//    private List<Resource> getDepicts() throws NoSuchFieldException {
//        try {
//            Projet.class.getDeclaredField("Yann");
//            depicts.add(Projet.Yann);
//        } catch (NoSuchFieldException ex) {
//            System.out.println("field n'existe pas");
//        }
//        return depicts;
//    }
    
    private String getResource(String r) {
        return Namespaces.photoNS+"#"+r;
    }
    
    public void search() throws NoSuchFieldException  { 
        System.out.println(rdf.searchPhotos(null, null, null, getResource(where), null, -1).size());
        System.out.println("what " + what + " where " + where + " who  " + who + "userId  " + userId);
    }
}

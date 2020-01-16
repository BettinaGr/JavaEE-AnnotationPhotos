/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import org.apache.jena.rdf.model.Resource;
import fr.uga.miashs.sempic.rdf.Namespaces;
import fr.uga.miashs.sempic.rdf.SempicRDFStore;

@Named
@ViewScoped
public class Recherche implements Serializable{
    private String userId;
    
    private String who;
    
    private String where;
    
    private String what;
    
    private String takenBy;
    
    private List<String> depicts;
    
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
        if(r!= null && r!= "") 
            return Namespaces.photoNS+"#"+r;
        else
            return null;
    }
    
    private List<String> getDepicts() {
        depicts = new ArrayList<String>();
        if (what != null) {
            String[] whatSplit = (what.split("\\s*(;\\s*)+"));
            for (String split : whatSplit) {
                if(!"".equals(split))
                    depicts.add(Namespaces.photoNS+"#"+split);
            }
        }     
        if (who != null) {
            String[] whoSplit = (who.split("\\s*(;\\s*)+"));
            for (String split : whoSplit) {
                if(!"".equals(split))
                    depicts.add(Namespaces.photoNS+"#"+split);
            }
        }  
        System.out.println("DEPICTS   " + depicts);
        return depicts;
    }
    
    public void search() { 
        System.out.println(rdf.searchPhotos(getDepicts(), getResource(takenBy), getResource(where), -1).size());
        System.out.println("what " + what + " where " + where + " who  " + who + "userId  " + userId);
    }
}

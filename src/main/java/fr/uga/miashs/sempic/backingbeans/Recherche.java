/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.SempicException;
import fr.uga.miashs.sempic.dao.PhotoFacade;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import org.apache.jena.rdf.model.Resource;
import fr.uga.miashs.sempic.rdf.Namespaces;
import fr.uga.miashs.sempic.rdf.SempicRDFStore;
import java.util.Arrays;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
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
    
    private List<String> depicts;
    
    private SempicRDFStore rdf = new SempicRDFStore();
    
    private List<Long> resultSearch = new ArrayList<>();
    
    private DataModel<SempicPhoto> dataModel;

    @Inject
    private PhotoFacade photoDao;
    
    
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
        this.what = what;
    }
     
    public String getWhat() {
        return this.what;
    }
    
    public void setWhere(String where) {
        this.where = where;
    }
    
    public String getWhere() {
        return this.where;
    }
     
    public void setTakenBy(String takenBy) {
        this.takenBy = takenBy;
    }
    public String getTakenBy() {
        return this.takenBy;
    }
    
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
        return depicts;
    }
    
    public void search() { 
        List<Resource> Resultat = rdf.searchPhotos(getDepicts(), getResource(takenBy), getResource(where), -1);
        resultSearch = new ArrayList<>();
        for (Resource res : Resultat ) {
            String id[] = res.getURI().split("/");
            resultSearch.add(Long.parseLong(id[id.length-1]));
        }
        System.out.println(resultSearch);
    }
    
    public DataModel<SempicPhoto> getDataModel() {
        if (dataModel == null && !resultSearch.isEmpty()) {
            System.out.println("dataPhoto " + photoDao.findPhotosByListId(resultSearch));
            dataModel = new ListDataModel<>(photoDao.findPhotosByListId(resultSearch));
        } else if (resultSearch.isEmpty()) {
            dataModel = null;
        }
        return dataModel;
    }
}

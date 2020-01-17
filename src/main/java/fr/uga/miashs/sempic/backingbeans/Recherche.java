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
    
    private String whoWhat;
    
    private String where;
    
    private String when;
    
    private String takenBy;
    
    private List<String> depicts;
    
    private SempicRDFStore rdf = new SempicRDFStore();
    
    private List<Long> resultSearch = new ArrayList<>();
    
    private String whoWhatType;
    
    private String whereType;
    
    private String whenType;
    
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
    
    public void setWhoWhat(String whoWhat) { 
        this.whoWhat = whoWhat;
    }
     
    public String getWhoWhat() {
        return this.whoWhat;
    }
    
    public void setWhoWhatType(String whoWhatType) { 
        this.whoWhatType = whoWhatType;
    }
     
    public String getWhoWhatType() {
        return this.whoWhatType;
    }
   
    
    public void setWhere(String where) {
        this.where = where;
    }
    
    public String getWhere() {
        return this.where;
    }
     
    public void setWhereType(String whereType) {
        this.whereType = whereType;
    }
    
    public String getWhereType() {
        return this.whereType;
    }
    
    public void setWhenType(String whenType) {
        this.whenType = whenType;
    }
    
    public String getWhenType() {
        return this.whenType;
    }
    
    public void setWhen(String when) {
        this.when = when;
    }
    
    public String getWhen() {
        return this.when;
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
        if (whoWhat != null) {
            String[] whatSplit = (whoWhat.split("\\s*(;\\s*)+"));
            for (String split : whatSplit) {
                if(!"".equals(split))
                    depicts.add(Namespaces.photoNS+"#"+split);
            }
        }     
        return depicts;
    }
    
    public void search() { 
        List<Resource> Resultat = rdf.searchPhotos(getDepicts(), getResource(takenBy), getResource(where), getResource(when), getResource(whenType), getResource(whereType), getResource(whoWhatType), Long.parseLong(userId));
        resultSearch = new ArrayList<>();
        for (Resource res : Resultat ) {
            String id[] = res.getURI().split("/");
            resultSearch.add(Long.parseLong(id[id.length-1]));
        }
        System.out.println(resultSearch);
    }
    
    public DataModel<SempicPhoto> getDataModel() {
        if (!resultSearch.isEmpty()) {
            dataModel = new ListDataModel<>(photoDao.findPhotosByListId(resultSearch));
        } else if (resultSearch.isEmpty()) {
            dataModel = null;
        }
        return dataModel;
    }
}

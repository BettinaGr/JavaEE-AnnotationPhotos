/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.SempicException;
import fr.uga.miashs.sempic.dao.PhotoFacade;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.*;
import fr.uga.miashs.sempic.rdf.SempicRDFStore;
import javax.enterprise.context.RequestScoped;
import fr.uga.miashs.sempic.rdf.Namespaces;
import javax.faces.model.DataModel;

/**
 *
 * @author Bettina
 */

@Named
@ViewScoped
public class AnnotatePhoto implements Serializable {
    
    private String photoId;
    
    private String objet;
    
    private String propriete;
    
    private SempicRDFStore rdf = new SempicRDFStore();
    
    
    @Inject
    private PhotoFacade photoDao;
    
    public void setPhotoId(String id) {
        this.photoId = id;
    }
     
    public String getPhotoId() {
        return this.photoId;
    }
    
       
    public String getThumbnailPathWeb() throws SempicException {
        return this.photoDao.read(Long.parseLong(photoId)).getThumbnailPathWeb();
    }
    
    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getPropriete() {
        return propriete;
    }

    public void setPropriete(String propriete) {
        this.propriete = propriete;
    }
    public void addAnnotation(){
        Resource photo = rdf.createPhoto(Long.parseLong(getPhotoId()), photoDao.findPhotoById(getPhotoId()).getAlbum().getId(), photoDao.findPhotoById(getPhotoId()).getAlbum().getOwner().getId());
        if (propriete.endsWith("Type")) {
            String prop = propriete.substring(0, propriete.length()-4);
            rdf.addAnnotationByType(photo, Namespaces.photoNS+"#"+prop, Namespaces.photoNS+"#"+objet);
            
        } else {
            rdf.addAnnotation(photo, Namespaces.photoNS+"#"+getPropriete(), Namespaces.photoNS+"#"+getObjet());
        }
        System.out.println("objt = "+ Namespaces.photoNS+"#"+objet);
        System.out.println("prop = "+ Namespaces.photoNS+"#"+propriete);
//        DisplayAnnotation();
        System.out.println("TYGHGBHB  " + photo);
        
    }
    
    public void DisplayAnnotation() {
        System.out.println("gggggggg    "+rdf.readPhoto(Long.parseLong(getPhotoId())));
        System.out.println("jjjjj "+rdf.readPhoto(Long.parseLong(getPhotoId())).split("#"));
    }
}

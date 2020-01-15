/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.SempicException;
import fr.uga.miashs.sempic.dao.PhotoFacade;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import fr.uga.miashs.sempic.rdf.BasicSempicRDFStore;
import fr.uga.miashs.sempic.rdf.SempicRDFStore;
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

/**
 *
 * @author Bettina
 */

@Named
@ViewScoped
public class AnnotatePhoto implements Serializable{
    private final static String ENDPOINT= "http://localhost:3030/sempic/";
    public final static String ENDPOINT_QUERY = ENDPOINT+"sparql"; // SPARQL endpoint
    public final static String ENDPOINT_UPDATE = ENDPOINT+"update"; // SPARQL UPDATE endpoint
    public final static String ENDPOINT_GSP = ENDPOINT+"data"; // Graph Store Protocol
    
    private Long photoId;
    
    private Resource objet;
    
    private Property propriete;
    
    @Inject
    private PhotoFacade photoDao;
    
    public void setPhotoId(String id) {
        this.photoId = Long.parseLong(id);
    }
     
    public String getPhotoId() {
        return String.valueOf(this.photoId);
    }
       
    public String getThumbnailPathWeb() throws SempicException {
        return this.photoDao.read(photoId).getThumbnailPathWeb();
    }
    
    public Resource getObjet() {
        return objet;
    }

    public void setObjet(Resource objet) {
        this.objet = objet;
    }

    public Property getPropriete() {
        return propriete;
    }

    public void setPropriete(Property propriete) {
        this.propriete = propriete;
    }
    public void addAnnotation(){
        
        System.out.print("objt = "+ objet);
        
        /*RDFConnection cnx = RDFConnectionFactory.connect(ENDPOINT_QUERY, ENDPOINT_UPDATE, ENDPOINT_GSP);
         
        BasicSempicRDFStore s = new BasicSempicRDFStore();
        Resource picture = s.readPhoto(photoId);

        SempicRDFStore s2 = new SempicRDFStore();
        s2.addAnnotation(picture, propriete, objet);
        
        cnx.close();  */
    }
}

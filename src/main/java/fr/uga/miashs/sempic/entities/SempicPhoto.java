/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.entities;

import fr.uga.miashs.sempic.SempicException;
import fr.uga.miashs.sempic.dao.PhotoStorage;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
@NamedQueries({
@NamedQuery(
        name = "query.SempicPhoto.findPhotoByAlbum", 
        query = "SELECT p FROM SempicPhoto p WHERE p.album =:album"
),
@NamedQuery(
    name = "query.SempicPhoto.removeById", 
    query = "DELETE FROM SempicPhoto p WHERE p.id =:id"
),
@NamedQuery(
    name = "query.SempicPhoto.removeByAlbum",
    query = "DELETE FROM SempicPhoto p WHERE p.album =:album"
),
@NamedQuery(
    name = "query.SempicPhoto.findPhotoById",
    query = "SELECT p FROM SempicPhoto p WHERE p.id =:id"
),
@NamedQuery(
    name = "query.SempicPhoto.findPhotosByListId",
    query = "SELECT p FROM SempicPhoto p WHERE p.id IN (:ListId)"
),
})
// Entité qui correspond à une photo
public class SempicPhoto implements Serializable{

    // Clé primaire
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    // Nom de la photo
    private String name;
    
    // Déclaration d'une relation ManyToOne entre SempicPhoto et SempicAlbum
    // Un album peut contenir plusieurs photos
    // Plusieurs photos peuvent être dans un même album
    @NotNull
    @ManyToOne
    private SempicAlbum album;
    
    public SempicPhoto(){
        
    }
    
    // Définition des accesseurs (get/set) pour que les propriétés d'un SempicPhoto soient utilisables
    
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SempicAlbum getAlbum() {
        return album;
    }

    public void setAlbum(SempicAlbum album) {
        this.album = album;
    }
    
    //Permet d'obtenir le chemin absolu de la copie de la photo créé à l'upload d'une photo sur le sit Web :
    public String getPicturePath() {
        return PhotoStorage.PICTURESTORE.resolve(name).toString();
    }
   
//    Permet d'avoir le chemin d'accès à la photo depuis la page internet, appelé dans le .xhtml pour afficher la photo :
    public String getThumbnailPathWeb() throws SempicException {
        Path pic = Paths.get(name);
        try {
            PhotoStorage ps = new PhotoStorage();   
            ps.getThumbnailPath(pic, 120);
            return PhotoStorage.buildAndVerify(PhotoStorage.THUMBNAILSWEB.resolve(String.valueOf(120)), pic).toString();
        
        } catch (IOException ex) {
            Logger.getLogger(SempicPhoto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    //Permet d'obtenir le chemin absolu du Thumbnails créé à l'afichage de la liste de photo sur la page Web :
    public String getThumbnailPath() throws SempicException {
        Path pic = Paths.get(name);
        try {
            PhotoStorage ps = new PhotoStorage();       
            ps.getThumbnailPath(pic, 120);
            return PhotoStorage.buildAndVerify(PhotoStorage.THUMBNAILS.resolve(String.valueOf(120)), pic).toString();
        } catch (IOException ex) {
            Logger.getLogger(SempicPhoto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    @Override
    public String toString() {
        return "Photo {" + "id=" + id + ", name=" + name + ", album=" + album + '}';
    }
}

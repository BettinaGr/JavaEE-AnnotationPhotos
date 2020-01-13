/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.entities;

import fr.uga.miashs.restservice.PhotoStore;
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
import javax.persistence.Table;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Bettina
 */
@Entity
@Table
public class SempicPhoto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String name;
    
    @NotNull
    @ManyToOne
    private SempicAlbum album;
    
    public SempicPhoto(){
        
    }
    
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
    
//    public String getPicturePath() {
//        return PhotoStorage.getPictureStore().resolve(name).toString();
//    }
//   
//    public String getThumbnailPath() throws SempicException {
//        Path pic = Paths.get(name);
//        try {
//            PhotoStorage ps = new PhotoStorage();
//            ps.getThumbnailPath(pic, 120);
//            return new PhotoStorage(PhotoStorage.getThumbnailStore().resolve(String.valueOf(120)), pic).toString();
//        } catch (IOException ex) {
//            Logger.getLogger(SempicPhoto.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "";
//    }
    
    @Override
    public String toString() {
        return "Photo {" + "id=" + id + ", name=" + name +'}';
    }
}

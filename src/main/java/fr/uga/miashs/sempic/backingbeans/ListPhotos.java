/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.dao.AlbumFacade;
import fr.uga.miashs.sempic.dao.PhotoFacade;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import java.io.Serializable;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ListPhotos implements Serializable {
    
    private Long albumId;
    
    private DataModel<SempicPhoto> dataModel;

    @Inject
    private AlbumFacade albumDao;
    
    @Inject
    private PhotoFacade photoDao;

    /**
     * Récupère l'ensemble des photos d'un album stockées dans la table SEMPICPHOTO
     *
     * @return le DataModel de tous les SempicPhoto d'un album de la base de données
     * 
     */
    public DataModel<SempicPhoto> getDataModel() {
        if (dataModel == null) {
            dataModel = new ListDataModel<>(photoDao.findPhotoByAlbum(albumDao.findAlbumById(albumId)));
        }
        return dataModel;
    }

    // Accesseurs (get/set)
    public void setAlbumId(String id) {
        this.albumId = Long.parseLong(id);
    }
     
    public String getAlbumId() {
        return String.valueOf(this.albumId);
    }
    
    public String getAlbumName() {
        return albumDao.findAlbumById(albumId).getName();
    }
        
    
}
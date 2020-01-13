/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.dao.AlbumFacade;
import fr.uga.miashs.sempic.dao.PhotoFacade;
import fr.uga.miashs.sempic.dao.SempicUserFacade;
import fr.uga.miashs.sempic.entities.SempicAlbum;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Orlane
 */

@Named
@ViewScoped
public class ListPhotos implements Serializable {
    
    private Long albumId;
    
    private DataModel<SempicPhoto> dataModel;

    @Inject
    private AlbumFacade albumDao;
    
    @Inject
    private PhotoFacade photoDao;

    
    public DataModel<SempicPhoto> getDataModel() {
        if (dataModel == null) {
            //dataModel = new ListDataModel<>(albumDao.findAll());
            System.out.println("dataPhoto " + photoDao.findPhotoByAlbum(albumDao.findAlbumById(albumId)));
            dataModel = new ListDataModel<>(photoDao.findPhotoByAlbum(albumDao.findAlbumById(albumId)));
        }
        return dataModel;
    }

    public void setAlbumId(String id) {
        this.albumId = Long.parseLong(id);
    }
     
    public String getAlbumId() {
        return String.valueOf(this.albumId);
    }
    
    public String getAlbumName() {
        System.out.println("dataAlbum " + albumDao.findAlbumById(albumId).getName());
        return albumDao.findAlbumById(albumId).getName();
    }
        
    
}
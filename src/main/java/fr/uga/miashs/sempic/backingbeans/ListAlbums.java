/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.dao.AlbumFacade;
import fr.uga.miashs.sempic.dao.SempicUserFacade;
import fr.uga.miashs.sempic.entities.SempicAlbum;
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
 * @author Bettina
 */

@Named
@ViewScoped
public class ListAlbums implements Serializable {
    
    private String ownerId;
    
    private DataModel<SempicAlbum> dataModel;

    @Inject
    private AlbumFacade albumDao;
    
    @Inject
    private SempicUserFacade userDao;

    
    public DataModel<SempicAlbum> getDataModel() {
        if (dataModel == null) {
            //dataModel = new ListDataModel<>(albumDao.findAll());
            System.out.println("data " + albumDao.findAlbum(userDao.read(Long.parseLong(getOwnerId()))).toString());
            dataModel = new ListDataModel<>(albumDao.findAlbum(userDao.read(Long.parseLong(getOwnerId()))));
        }
        return dataModel;
    }

    public void setOwnerId(String id) {
        System.out.println("set id list " + id); 
        this.ownerId = id;
    }
     
    public String getOwnerId() {
        return this.ownerId;
    }
    
    
}

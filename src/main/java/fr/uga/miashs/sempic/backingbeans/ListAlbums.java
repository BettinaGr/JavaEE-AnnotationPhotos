/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.dao.AlbumFacade;
import fr.uga.miashs.sempic.dao.SempicUserFacade;
import fr.uga.miashs.sempic.entities.SempicAlbum;
import fr.uga.miashs.sempic.entities.SempicUser;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Bettina
 */

@Named
@RequestScoped
public class ListAlbums implements Serializable{
    
    private SempicAlbum current;
    
    private DataModel<SempicAlbum> dataModel;

    @Inject
    private AlbumFacade albumDao;
    
    @Inject
    private SempicUserFacade userDao;

    @PostConstruct
    public void init() {
        current=new SempicAlbum();
    }
    
    public DataModel<SempicAlbum> getDataModel() {
        if (dataModel == null) {
            //dataModel = new ListDataModel<>(albumDao.findAll());
            dataModel = new ListDataModel<>(albumDao.findAlbum(userDao.read(Long.parseLong(getOwnerId()))));
        }
        return dataModel;
    }

    public void setOwnerId(String id) {
        System.out.println(id); 
        current.setOwner(userDao.read(Long.valueOf(id)));
    }
     
    public String getOwnerId() {
        if (current.getOwner()==null)
            return "-1";
        return ""+current.getOwner().getId();
    }
    
}

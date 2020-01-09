/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.dao.AlbumFacade;
import java.awt.Dimension;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author Bettina
 */
@Named
@RequestScoped
public class RemoveAlbum {
    
    @Inject
    private AlbumFacade albumDao;

    private String id;
    private Long id2;
  
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void remove(){
        JPanel panel = new JPanel();
        int input = JOptionPane.showConfirmDialog(panel, "Do you like bacon?");
        
        // 0=yes, 1=no, 2=cancel
        System.out.println(input);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        this.id = getIdParam(fc);
        this.id2 = Long.parseLong(this.id);
        albumDao.remove(this.id2);
        
    }
     //get value from "f:param"
    public String getIdParam(FacesContext fc){
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("albumId");
    }  
}

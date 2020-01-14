/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.SempicException;
import fr.uga.miashs.sempic.dao.PhotoFacade;
import fr.uga.miashs.sempic.dao.PhotoStorage;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import java.nio.file.Paths;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Bettina
 */
@Named
@RequestScoped
public class RemovePhoto {
    @Inject
    private PhotoFacade photoDao;
    
    @Inject
    private PhotoStorage photoStorage;
    
    /*@Inject
    private SempicPhoto current;*/
    
    private String id;
    private Long id2;
    private String picPath;
    private String thPath;
    
    @PostConstruct
    public void init() {
        //current=new SempicPhoto();
    }
    
    /*public SempicPhoto getCurrent() {
        return current;
    }

    public void setCurrent(SempicPhoto current) {
        this.current = current;
        //Logger.getLogger(UploadPhoto.class.getName()).log(Level.INFO, null, this.current);
    }*/
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
      this.id = id;
    }
    
    public String getpicPath() {
        return picPath;
    }

    public void setpicPath(String picPath) {
      this.picPath = picPath;
    }
    

    public void remove() throws SempicException {
        FacesContext fc = FacesContext.getCurrentInstance();
        
        this.id = getIdParam(fc);
        this.id2 = Long.parseLong(this.id);
        photoDao.remove(this.id2);
       
        photoStorage.deletePicture(Paths.get(getPicPathParam(fc)));
    }
    
    //get value from "f:param"
    public String getIdParam(FacesContext fc){
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("photoId");
    }  
    public String getPicPathParam(FacesContext fc){
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("photoPath");
    }  

    public String getAlbumId(FacesContext fc){
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("albumId");
    }  
}

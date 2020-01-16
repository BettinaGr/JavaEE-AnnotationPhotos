/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.SempicException;
import fr.uga.miashs.sempic.dao.PhotoFacade;
import fr.uga.miashs.sempic.dao.PhotoStorage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RemovePhoto {
    @Inject
    private PhotoFacade photoDao;
    
    @Inject
    private PhotoStorage photoStorage;
    
    private String id;
    private Long id2;
    private String picPath;
    
    @PostConstruct
    public void init() {
    }
    
    // Accesseurs (get/set)
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
    
    // Supprime une photo
    public void remove() throws SempicException, IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        
        this.id = getIdParam(fc);
        this.id2 = Long.parseLong(this.id);
        // On supprime la photo de la base de données
        photoDao.remove(this.id2, this.getAlbumId(fc));
        // On supprime la photo des dossiers files et thumbnails 
        photoStorage.deletePicture(Paths.get(getPicPathParam(fc)));
    }
    
    /**
     * Récupère la valeur du paramètre photoId
     *
     * @param fc = objet qui contient les paramètres de la page
     * @return l'identifiant correspondant à la photo à supprimer
     * 
     */
    public String getIdParam(FacesContext fc){
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("photoId");
    }  
    
    /**
     * Récupère la valeur du paramètre photoPath
     *
     * @param fc = objet qui contient les paramètres de la page
     * @return le path correspondant à la photo à supprimer
     * 
     */
    public String getPicPathParam(FacesContext fc){
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("photoPath");
    }  
    
    /**
     * Récupère la valeur du paramètre albumId
     *
     * @param fc = objet qui contient les paramètres de la page
     * @return l'id de l'album qui contient la photo à supprimer
     * 
     */
    public String getAlbumId(FacesContext fc){
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("albumId");
    }  
}

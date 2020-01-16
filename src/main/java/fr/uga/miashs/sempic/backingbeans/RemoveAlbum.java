/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.dao.AlbumFacade;
import java.io.IOException;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RemoveAlbum {
    
    @Inject
    private AlbumFacade albumDao;
    
    private String id;
    private Long id2;
  
     // Accesseurs (get/set)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

     // Supprime un album
    public void remove() throws IOException{
        FacesContext fc = FacesContext.getCurrentInstance();
        this.id = getIdParam(fc);
        this.id2 = Long.parseLong(this.id);
        albumDao.remove(this.id2, this.getOwnerId(fc)); 

    }
    
    /**
     * Récupère la valeur du paramètre albumId
     *
     * @param fc = objet qui contient les paramètres de la page
     * @return l'identifiant correspondant à l'album à supprimer
     * 
     */
    public String getIdParam(FacesContext fc){
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("albumId");
    }  
    
    /**
     * Récupère la valeur du paramètre ownerId
     *
     * @param fc = objet qui contient les paramètres de la page
     * @return l'identifiant correspondant au propriétaire de l'album à supprimer
     * 
     */
    public String getOwnerId(FacesContext fc){
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("ownerId");
    }  
}

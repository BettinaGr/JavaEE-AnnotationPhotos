/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.dao.SempicUserFacade;
import java.io.IOException;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RemoveUser {
    @Inject
    private SempicUserFacade userDao;

    private String id;
    private Long id2;
  
    // Accesseurs
    public String getId() {
        return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    // Supprime un utilisateur
    public void remove() throws IOException{
        FacesContext fc = FacesContext.getCurrentInstance();
        this.id = getIdParam(fc);
        this.id2 = Long.parseLong(this.id);
        userDao.remove(this.id2);
        
    }
    
    /**
     * Récupère la valeur du paramètre userId pour que l'on puisse supprimer l'utilisateur de la base de données
     *
     * @param fc = objet qui contient les paramètres de la page
     * @return l'identifiant correspondant à l'utilisateur à supprimer
     * 
     */
    public String getIdParam(FacesContext fc){

      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      return params.get("userId");

    }  
}

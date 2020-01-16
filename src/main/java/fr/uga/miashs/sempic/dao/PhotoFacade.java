/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.entities.SempicAlbum;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import java.io.IOException;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.Query;

// Exécution de requêtes dans la base de données SempicPhoto
@Stateless
public class PhotoFacade extends AbstractJpaFacade<Long,SempicPhoto>{
    
    // Constructeurs
    public PhotoFacade() {
        super(SempicPhoto.class);
    }
    
    public PhotoFacade(Class<SempicPhoto> entityClass) {
        super(entityClass);
    }
    
    /**
     * Listes des SempicPhoto présents dans un album
     *
     * @param album = SempicAlbum qui contient la liste des SempicPhoto retournée
     * @return la liste des SempicPhoto présents dans album
     */
    public List<SempicPhoto> findPhotoByAlbum(SempicAlbum album){
        Query q = getEntityManager().createNamedQuery("query.SempicPhoto.findPhotoByAlbum");
        q.setParameter("album", album);
        return  q.getResultList();
    }
    
    /**
     * Supprime la SempicPhoto de la base de données
     *
     * @param id = identifiant de la photo à supprimer
     * @param albumId = identifiant de l'album auquel appartient la photo supprimé (est utilisé pour la redirection après la suppresion de la photo)
     * @throws java.io.IOException
     */
    public void remove(Long id, String albumId) throws IOException{
        Query q = getEntityManager().createNamedQuery("query.SempicPhoto.removeById");
        q.setParameter("id", id).executeUpdate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/SempicJPA/faces/user/list-photo.xhtml?albumId="+albumId);
    }
    
     /**
     * Retrouve la SempicPhoto d'identifiant id dans la base de données
     *
     * @param id = identifiant de la photo à trouver 
     * @return la SempicPhoto dont l'identifiant unique est id 
     * 
     */
    public SempicPhoto findPhotoById(String id) {
        Query q = getEntityManager().createNamedQuery("query.SempicPhoto.findPhotoById");
        q.setParameter("id", Long.parseLong(id));
        return (SempicPhoto) q.getSingleResult();
    }
}

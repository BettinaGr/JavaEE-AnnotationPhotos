/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.SempicException;
import fr.uga.miashs.sempic.entities.SempicAlbum;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

/**
 *
 * @author Bettina
 */

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
     */
    public void remove(Long id){
        Query q = getEntityManager().createNamedQuery("query.SempicPhoto.removeById");
        q.setParameter("id", id).executeUpdate();
    }
}

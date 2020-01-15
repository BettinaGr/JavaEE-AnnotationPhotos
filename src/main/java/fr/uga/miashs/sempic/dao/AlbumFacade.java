/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.entities.SempicAlbum;
import fr.uga.miashs.sempic.entities.SempicUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Bettina
 */
// Exécution de requêtes dans la base de données SempicAlbum
@Stateless
public class AlbumFacade extends AbstractJpaFacade<Long,SempicAlbum>{
    // Constructeur
    public AlbumFacade() {
        super(SempicAlbum.class);
    }
    /**
     * Suppression de l'album de la base de données.
     *
     * @param id = id de l'album a supprimer
     */
    public void remove(Long id){
        // Lorsqu'un album a des photos à l'intérieur on les retire de la base de données SempicPhoto
        Query qPhoto = getEntityManager().createNamedQuery("query.SempicPhoto.removeByAlbum");
        qPhoto.setParameter("album", read(id)).executeUpdate();
        Query q = getEntityManager().createNamedQuery("query.SempicAlbum.remove");
        q.setParameter("id", id).executeUpdate();
    }
    
    /**
     * Liste des SempicAlbums d'un utilisateur SempicUser
     *
     * @param u = SempicUser propriétaire de la liste d'albums
     */
    public List<SempicAlbum> findAlbum(SempicUser u){
        Query q = getEntityManager().createNamedQuery("query.SempicAlbum.findAlbum");
        q.setParameter("owner", u);
        return q.getResultList();
    }
    
    /**
     * Retourne un SempicAlbum en fonction de son id 
     *
     * @param id = identifiant unique du SempicAlbum que l'on veut retourner
     */
    public SempicAlbum findAlbumById(Long id){
        Query q = getEntityManager().createNamedQuery("query.SempicAlbum.findAlbumById");
        q.setParameter("id", id);
        return (SempicAlbum) q.getSingleResult();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.entities.SempicAlbum;
import fr.uga.miashs.sempic.entities.SempicUser;
import java.io.IOException;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.Query;

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
     * @param ownerId
     * @throws java.io.IOException
     */
    public void remove(Long id, String ownerId) throws IOException{
        // Lorsqu'un album a des photos à l'intérieur on les retire de la base de données SempicPhoto
        Query qPhoto = getEntityManager().createNamedQuery("query.SempicPhoto.removeByAlbum");
        qPhoto.setParameter("album", read(id)).executeUpdate();
        Query q = getEntityManager().createNamedQuery("query.SempicAlbum.remove");
        q.setParameter("id", id).executeUpdate();
         FacesContext.getCurrentInstance().getExternalContext().redirect("/SempicJPA/faces/list-album.xhtml?userId="+ownerId);
    }
    
    /**
     * Liste des SempicAlbums d'un utilisateur SempicUser
     *
     * @param u = SempicUser propriétaire de la liste d'albums
     * @return Liste des SempicAlbums dont le propriétaire est u
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
     * @return le SempicAlbum dont l'identifiant est id
     */
    public SempicAlbum findAlbumById(Long id){
        Query q = getEntityManager().createNamedQuery("query.SempicAlbum.findAlbumById");
        q.setParameter("id", id);
        return (SempicAlbum) q.getSingleResult();
    }
}

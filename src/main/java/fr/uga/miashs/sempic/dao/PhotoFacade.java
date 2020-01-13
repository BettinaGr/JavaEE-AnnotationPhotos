/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.entities.SempicAlbum;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bettina
 */

@Stateless
public class PhotoFacade extends AbstractJpaFacade<Long,SempicPhoto>{

    public PhotoFacade() {
        super(SempicPhoto.class);
    }
    
    public PhotoFacade(Class<SempicPhoto> entityClass) {
        super(entityClass);
    }
    
    public List<SempicPhoto> findPhotoByAlbum(SempicAlbum album){
        Query q = getEntityManager().createNamedQuery("query.SempicPhoto.findPhotoByAlbum");
        q.setParameter("album", album);
        return  q.getResultList();
    }
}

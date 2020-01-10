/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.entities.SempicAlbum;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Bettina
 */
@Stateless
public class AlbumFacade extends AbstractJpaFacade<Long,SempicAlbum>{
    public AlbumFacade() {
        super(SempicAlbum.class);
    }
    public void remove(Long id){
        Query q = getEntityManager().createNamedQuery("query.SempicAlbum.remove");
        q.setParameter("id", id).executeUpdate();
    }
    public List<SempicAlbum> findAlbum(long id){
        Query q = getEntityManager().createNamedQuery("query.SempicAlbum.findAlbum");
        q.setParameter("id", id).executeUpdate();
        return super.find((CriteriaQuery) q);
    }
}

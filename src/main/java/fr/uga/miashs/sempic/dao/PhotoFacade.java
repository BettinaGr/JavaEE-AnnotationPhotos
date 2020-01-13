/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.entities.SempicPhoto;
import javax.ejb.Stateless;

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
    
}

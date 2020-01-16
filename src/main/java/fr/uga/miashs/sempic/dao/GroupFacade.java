/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.entities.SempicGroup;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Jerome David <jerome.david@univ-grenoble-alpes.fr>
 */
// Exécution de requêtes dans la base de données SempicGroup
@Stateless
public class GroupFacade extends AbstractJpaFacade<Long,SempicGroup>{
    
    public GroupFacade() {
        super(SempicGroup.class);
    }
    /**
     * Lie un membre avec un groupe dans la base de données SEMPICGROUP_SEMPICUSER
     *
     * @param groupId = id du groupe
     * @param userId = id de l'utilisateur (du membre)
     */
    public void addMember(long groupId, long userId) {
        Query q = getEntityManager().createNativeQuery("INSERT INTO SEMPICGROUP_SEMPICUSER(MEMBERS_ID,MEMBEROF_ID) VALUES (?1,?2)");
        q.setParameter(1,userId);
        q.setParameter(2,groupId);
        q.executeUpdate();
    }
    
    /**
     * Supprime un membre d'un groupe dans la base de données SEMPICGROUP_SEMPICUSER
     *
     * @param groupId = id du groupe
     * @param userId = id de l'utilisateur (du membre)
     */
    public void deleteMember(long groupId, long userId) {
        Query q = getEntityManager().createNativeQuery("DELETE FROM SEMPICGROUP_SEMPICUSER WHERE MEMBERS_ID=?1 AND MEMBEROF_ID=?2");
        q.setParameter(1,userId);
        q.setParameter(2,groupId);
        q.executeUpdate();
    }
}

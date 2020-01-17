/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.SempicModelException;
import fr.uga.miashs.sempic.entities.SempicAlbum;
import fr.uga.miashs.sempic.entities.SempicUser;
import java.io.IOException;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.Query;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;


/**
 *
 * @author Jerome David <jerome.david@univ-grenoble-alpes.fr>
 */

// Exécution de requêtes dans la base de données SempicUser
@Stateless
public class SempicUserFacade extends AbstractJpaFacade<Long,SempicUser> {

    @Inject
    private transient Pbkdf2PasswordHash hashAlgo;
    
    // Constructeur
    public SempicUserFacade() {
        super(SempicUser.class);
    }

    /**
     * Création d'un SempicUser grâce à la fonction create héritée de AbstractJpaFacade
     *
     * @param user = SempicUser a ajouter à la base de données
     * @return l'identifiant du SempicUser créé
     * @throws fr.uga.miashs.sempic.SempicModelException
     */
    @Override
    public Long create(SempicUser user) throws SempicModelException {
        if (user.getPassword()!=null) {
            user.setPasswordHash(hashAlgo.generate(user.getPassword().toCharArray()));
        }
        return super.create(user);
    }
    
     /**
     * 
     * @return Liste de tous les SempicUser 
     */
    @Override
    public List<SempicUser> findAll() {
        EntityGraph entityGraph = this.getEntityManager().getEntityGraph("graph.SempicUser.groups-memberOf");
        return getEntityManager().createQuery(this.findAllQuery())
            .setHint("javax.persistence.fetchgraph", entityGraph)
            .getResultList();
    }
    
    /**
     *
     * @param id = identifiant unique du SempicUser recherché
     * @return le SempicUser ayant l'identifiant unique = id
     */
    public SempicUser readEager(long id){
        EntityGraph entityGraph = this.getEntityManager().getEntityGraph("graph.SempicUser.groups-memberOf");
        return (SempicUser) getEntityManager().createQuery("SELECT u FROM SempicUser u WHERE u.id=:id")
                .setParameter("id", id)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getSingleResult();
    }
    
     /**
     * Retourne une exception si aucun utilisateur ne correspond
     *
     * @param email = email du SempicUser
     * @param password = mot de passe du SempicUser
     * @return le SempicUser dont l'email et le mot de passe hashé correspondent
     * @throws fr.uga.miashs.sempic.SempicModelException
     */
    public SempicUser login(String email, String password) throws SempicModelException {
        Query q = getEntityManager().createNamedQuery("query.SempicUser.readByEmail");
        q.setParameter("email", email);
        SempicUser u =  (SempicUser) q.getSingleResult();
        if (hashAlgo.verify(password.toCharArray(), u.getPasswordHash())) {
            return u;
        }
        throw new SempicModelException("login failed");
    }

    /**
     *
     * @param email = email du SempicUser
     * @return le SempicUser dont l'email et le mot de passe hashé correspondent
     */
    public SempicUser readByEmail(String email) {
        Query q = getEntityManager().createNamedQuery("query.SempicUser.readByEmail");
        q.setParameter("email", email);
        return (SempicUser) q.getSingleResult();
    }
    
     /**
     * Supprime le SempicUser de la base de données
     *
     * @param id = id de l'utilisateur à supprimer
     * @throws java.io.IOException
     * 
     */
    public void remove(Long id) throws IOException{
         // Lorsqu'un utilisateur a des albums on les récupère dans SempicAlbum
        Query qA = getEntityManager().createNamedQuery("query.SempicAlbum.selectByOwner");
        qA.setParameter("owner", read(id));
        // On les stockes dans une liste
        List<SempicAlbum> resultList = qA.getResultList();
        // On supprime les photos présentent dans l'album de l'utilisateur
        Query qPhoto = getEntityManager().createNamedQuery("query.SempicPhoto.removeByAlbum");
        int i = 0;
        while (i<resultList.size()){
            qPhoto.setParameter("album", resultList.get(i)).executeUpdate();
            i++;
        }
        // Lorsqu'un utilisateur a des albums on les retire de la base de données SempicAlbum
        Query qAlbum = getEntityManager().createNamedQuery("query.SempicAlbum.removeByOwner");
        qAlbum.setParameter("owner", read(id)).executeUpdate();
        Query q = getEntityManager().createNamedQuery("query.SempicUser.remove");
        q.setParameter("id", id).executeUpdate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/SempicJPA/faces/admin/list-users.xhtml");
    }
    
}

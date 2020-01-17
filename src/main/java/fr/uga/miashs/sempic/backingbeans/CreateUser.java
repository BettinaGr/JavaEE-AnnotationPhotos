/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.SempicModelException;
import fr.uga.miashs.sempic.SempicModelUniqueException;
import fr.uga.miashs.sempic.dao.SempicUserFacade;
import fr.uga.miashs.sempic.entities.SempicUser;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Jerome David <jerome.david@univ-grenoble-alpes.fr>
 */
@Named
@ViewScoped
public class CreateUser implements Serializable {
    
    private SempicUser current;
    
    @Inject
    private SempicUserFacade userDao;
   
    public CreateUser() {
    }
    
    @PostConstruct
    public void init() {
        current=new SempicUser();
    }

    // Accesseurs (get/set)
    public SempicUser getCurrent() {
        return current;
    }

    public void setCurrent(SempicUser current) {
        this.current = current;
    }
    
    public String getPassword() {
        return null;
    }
    
    public void setPassword(@NotBlank(message="Password is required") String password) {
        getCurrent().setPassword(password);
    }
    
    /**
     * Création de l'utilisateur 
     *
     * 
     * @return "failure" si ajout est un échec, "success" sinon
     */
    public String create() {
        try {
            userDao.create(current);
        } 
        catch (SempicModelUniqueException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Un utilisateur avec cette adresse mail existe déjà !"));
            return "failure";
        }
        catch (SempicModelException ex) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
            return "failure";
        }
        
        return "success";
    }
}

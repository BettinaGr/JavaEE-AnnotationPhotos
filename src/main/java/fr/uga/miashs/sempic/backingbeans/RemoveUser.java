/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.SempicModelException;
import fr.uga.miashs.sempic.dao.SempicUserFacade;
import fr.uga.miashs.sempic.entities.SempicUser;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Bettina
 */
@Named
@SessionScoped
public class RemoveUser {
    @Inject
    private SempicUserFacade userDao;

    public String id;
  
    public String getId() {
    return id;
  }

    public void setId(String id) {
      this.id = id;
    }

    public void remove(){

        FacesContext fc = FacesContext.getCurrentInstance();
        this.id = getIdParam(fc);
        userDao.remove(id);
    }
     //get value from "f:param"
    public String getIdParam(FacesContext fc){

      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      return params.get("userId");

    }  
}

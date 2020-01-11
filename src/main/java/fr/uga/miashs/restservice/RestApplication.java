/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.restservice;

import fr.uga.miashs.sempic.ApplicationConfig;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;

/**
 *
 * @author pitarcho
 */

@ApplicationPath(ApplicationConfig.WEB_API)
public class RestApplication extends Application {

    public RestApplication() {
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        Set res = new HashSet();
        String[] features = {
            "org.glassfish.jersey.moxy.json.MoxyJsonFeature"
        };
        /*
        classes présentes dans TomEE chargées dynamiquement à l'exécution 
        */
        for (String fName: features) {
            try {
                Class cls = Class.forName(fName);
                res.add(cls);
                     
            }catch (ClassNotFoundException ex) {
                Logger.getLogger(RestApplication.class.getName()).log(Level.WARNING, fName+"not available");
            }
        }
        /* 
        classe dans l'environnement de compilation mais pas dans l'environnement d'execution (tomEE)
        */
        res.add(MOXyJsonProvider.class);
        res.add(SempicUserService.class);
//        res.add(GroupService.class);
//        res.add(PhotoStore.class);
//        res.add(SempicExceptionMapper.class);

               return res;
    }
}

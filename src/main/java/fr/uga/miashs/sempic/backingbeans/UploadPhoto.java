/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.SempicException;
import fr.uga.miashs.sempic.SempicModelException;
import fr.uga.miashs.sempic.dao.AlbumFacade;
import fr.uga.miashs.sempic.dao.PhotoFacade;
import fr.uga.miashs.sempic.dao.PhotoStorage;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import jdk.internal.net.http.common.Utils;

/**
 *
 * @author Bettina
 */
@Named
@ViewScoped
public class UploadPhoto implements Serializable{
    
    private Part photo;
    
    private SempicPhoto current;
       
    @Inject
    private AlbumFacade albumDao;
    
    @Inject
    private PhotoStorage photoStorage;
    
    @Inject
    private PhotoFacade photoDao;
    
    
    private static final Map <String, String> mimeTypes; 
     
    static {
        Map <String, String> aMap = new HashMap <String, String>();
        aMap.put("image/png", "png");
        aMap.put("image/jpeg", "jpeg");
        mimeTypes = Collections.unmodifiableMap(aMap);
    }
    
    public UploadPhoto(){
         try {
            photoStorage = new PhotoStorage();
            System.out.println(photoStorage);
        } catch (IOException ex) {
            Logger.getLogger(UploadPhoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     
 
    @PostConstruct
    public void init() {
        current=new SempicPhoto();
    }
    
    public SempicPhoto getCurrent() {
        return current;
    }

    public void setCurrent(SempicPhoto current) {
        this.current = current;
        Logger.getLogger(UploadPhoto.class.getName()).log(Level.INFO, null, this.current);
    }
    
    public Part getPhoto(){
        
        return photo;
    }
    
    public void setPhoto(Part p){
        this.photo = p;
        System.out.println("apres " +this.photo);
    }
    
    public String getAlbumId() {
        if(current.getAlbum()==null)
            return "-1";
        return ""+current.getAlbum().getId();
    }
    
    public void setAlbumId(String id) {
        System.out.println("set id list " + id); 
        current.setAlbum(albumDao.read(Long.valueOf(id)));
    }
    
    public String save() throws Exception {
        try  {
            InputStream input = photo.getInputStream();
            System.out.println("input" + input);
            String mime = photo.getContentType();
            
            if (mimeTypes.containsKey(mime)){
                
                String fileName = createSha1(input) + "." + mimeTypes.get(mime);
                System.out.println("fileName" + fileName);
                input = photo.getInputStream(); // mark(0) doesnt work, so we initialize again
            //  String fileName = "test.jpeg";
                System.out.println("ffff " + input);
                System.out.println("resolve " + PhotoStorage.UPLOADS.resolve(fileName));
                photoStorage.savePicture(PhotoStorage.UPLOADS.resolve(fileName),input);
                SempicPhoto p = new SempicPhoto();
                p.setName(fileName);
                p.setAlbum(albumDao.findAlbumById(Long.parseLong(getAlbumId())));
                System.out.println("ffffccc" + p);
                System.out.println("creation en cours");
                photoDao.create(p);
            } 
            
        }
        catch (IOException e) {
            System.out.println("erreur upload");
        
            return "failure";
        }
        return "success";
    }
     private String createSha1(InputStream fis) throws IOException, NoSuchAlgorithmException  {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-1");
             int n = 0;
            byte[] buffer = new byte[8192];
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    digest.update(buffer, 0, n);
                }
            }
            return DatatypeConverter.printHexBinary(digest.digest());

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UploadPhoto.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
}

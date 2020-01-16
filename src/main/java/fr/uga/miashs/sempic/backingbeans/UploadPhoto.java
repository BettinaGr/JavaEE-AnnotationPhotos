/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.backingbeans;

import fr.uga.miashs.sempic.dao.AlbumFacade;
import fr.uga.miashs.sempic.dao.PhotoFacade;
import fr.uga.miashs.sempic.dao.PhotoStorage;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

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
    
    // Contient la liset des types des fichiers photos    
    private static final Map <String, String> mimeTypes; 
    
    static {
        Map <String, String> aMap = new HashMap <String, String>();
        aMap.put("image/png", "png");
        aMap.put("image/jpeg", "jpg");
        aMap.put("image/jpeg", "jpeg");
        mimeTypes = Collections.unmodifiableMap(aMap);
    }
    
    // Constructeur
    public UploadPhoto(){
         try {
            photoStorage = new PhotoStorage();
        } catch (IOException ex) {
            Logger.getLogger(UploadPhoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    @PostConstruct
    public void init() {
        current=new SempicPhoto();
    }
    
    // Définition des accesseurs (get/set) des propriétés
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
    }
    
    public String getAlbumId() {
        if(current.getAlbum()==null)
            return "-1";
        return ""+current.getAlbum().getId();
    }
    
    public void setAlbumId(String id) { 
        current.setAlbum(albumDao.read(Long.valueOf(id)));
    }
    
    /**
     * Vérification du type du fichier envoyé par le client et 
     * sauvegarde de la photo dans le path PICTURESTORE ainsi que dans la base de données.
     *
     * 
     * @return "failure" si ajout est un échec, "success" sinon
     * @throws java.lang.Exception
     */
    public String save() throws Exception {
        try  {
            InputStream input = photo.getInputStream();
            String mime = photo.getContentType();
            
            if (mimeTypes.containsKey(mime)){
                
                String fileName = createSha1(input) + "." + mimeTypes.get(mime);
                input = photo.getInputStream(); 
                photoStorage.savePicture(PhotoStorage.PICTURESTORE.resolve(fileName),input);
                SempicPhoto p = new SempicPhoto();
                p.setName(fileName);
                p.setAlbum(albumDao.findAlbumById(Long.parseLong(getAlbumId())));
                photoDao.create(p);                      
            }   
        }
        catch (IOException e) {
            return "failure";
        }
        return "success";
    }
    
    // tiré de : https://github.com/kwijik/PhotoMania
    /**
     * Permet de créer le nom de la photo
     * 
     * @param fis = InputStream à convertir en nom
     * @return l'InputStream fis convertit en String
     * 
     */
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

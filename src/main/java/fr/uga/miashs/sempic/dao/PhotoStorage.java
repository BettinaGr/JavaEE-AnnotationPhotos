/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.SempicException;
import fr.uga.miashs.sempic.entities.SempicPhoto;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletContext;

/**
 *
 * @author Jerome David <jerome.david@univ-grenoble-alpes.fr>
 */
@ApplicationScoped
public class PhotoStorage {
    private static Logger logger = Logger.getLogger(PhotoStorage.class.getName());

    /**
     * Définition des chemins absolus ou relatifs (pour ThumbnailsWeb) permettant l'accès aux photos insérées via notre site web.
     * 
     */
    public static final Path PICTURESTORE = Paths.get("C:/Users/Orlane/Documents/NetBeansProjects/Test/src/main/webapp/resources/files");
    public static final Path THUMBNAILS = Paths.get("C:/Users/Orlane/Documents/NetBeansProjects/Test/src/main/webapp/resources/thumbnails");
    public static final Path THUMBNAILSWEB =  Paths.get("/SempicJPA/faces/javax.faces.resource/thumbnails");
    
    

    /**
     * Constructeur permettant de créer les dossiers pour contenir les photos s'il n'existe pas.
     * 
     */
    public PhotoStorage() throws IOException {
        if (Files.notExists(PICTURESTORE)) {
            Files.createDirectories(PICTURESTORE);
        }
        if (Files.notExists(THUMBNAILS)) {
            Files.createDirectories(THUMBNAILS);
        }
    }
    

    // Normalize the path and check that we do not go before pictureStore
    // for security reasons
    // i.e. we do not allow /path/to/store + ../../ddsdd
    public static Path buildAndVerify(Path root, Path rel) throws SempicException {
                
        Path res = root;
        res = res.resolve(rel).normalize();
        if (!res.startsWith(root)) {
            throw new SempicException("Invalid path");
        }
        return res;
    }

    /**
     * Sauvegarde la photo à l'uplod de celle-ci sur le site (i.e. copie la photo dans l'endroit de stockage des photos
     * PICTURESTORE).
     * 
     */
    public void savePicture(Path p, InputStream data) throws SempicException {
        Path loc = buildAndVerify(PICTURESTORE, p);
        try {
            Files.createDirectories(loc.getParent());
            Files.copy(data, loc, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new SempicException("Failed to copy the photo", ex);
        }
    }

    /**
     * Supprime une photo dans les fichiers (copie de la photo et Thumbnails créé) si l'utilisateur souhaite supprimer sa photo
     * de notre site.
     * 
     */
    public void deletePicture(Path picPath) throws SempicException {
        Path loc = buildAndVerify(PICTURESTORE, picPath);

        try {
            if (Files.deleteIfExists(loc)) {

                if (!Files.newDirectoryStream(loc.getParent()).iterator().hasNext()) {
                    Files.delete(loc.getParent());
                }
                
                //Suppression dans le ThumbNails également :
                for (Path p : Files.newDirectoryStream(THUMBNAILS)) {
                    Path tp = buildAndVerify(p, picPath.getFileName());
                    Files.deleteIfExists(tp);
                    try {
                        Files.deleteIfExists(tp.getParent());
                    } catch (IOException e) {
                }
        }
                
            }
        } catch (IOException ex) {
            throw new SempicException("Failed to delete the photo", ex);
        }
    }
    
    /**
     * Retourne le chemin de stockage de la copie de la photo dans nos dossiers.
     * 
     */
    public Path getPicturePath(Path p) throws SempicException {
        Path picPath = buildAndVerify(PICTURESTORE, p);
        if (Files.notExists(picPath)) {
            throw new SempicException("The picture " + p + " does not exists");
        }
        return picPath;
    }

    /**
     * Retourne le chemin de stockage du Thumbnails de la photo dans nos dossiers.
     * Si le Thumbnails de la photo n'existe pas, on le crée à partir de la copie de la photo présente dans PICTURESTORE.
     * 
     */
    public Path getThumbnailPath(Path p, int width) throws SempicException, IOException {
        Path thumbnailPath = buildAndVerify(THUMBNAILS.resolve(String.valueOf(width)), p);
        
//        Test sur l'existance du fichier à transformer en Thumnails sinon erreur au remove car le fichier parent n'existe plus :
        if (Files.notExists(thumbnailPath) && Files.exists(buildAndVerify(PICTURESTORE, p))) {
            Path picPath = getPicturePath(p);
            Path parent = thumbnailPath.getParent();
            if (Files.notExists(parent)) {
                Files.createDirectories(parent);
            }

            BufferedImage bim = ImageIO.read(picPath.toFile());
            int height = (int) (bim.getHeight() * (((double) width) / bim.getWidth()));
            Image resizedImg = bim.getScaledInstance(width, height, Image.SCALE_FAST);
            BufferedImage rBimg = new BufferedImage(width, height, bim.getType());
            // Create Graphics object
            Graphics2D g = rBimg.createGraphics();

            // Draw the resizedImg from 0,0 with no ImageObserver
            g.drawImage(resizedImg, 0, 0, null);

            // Dispose the Graphics object, we no longer need it
            g.dispose();
            
            Files.createFile(thumbnailPath);
            ImageIO.write(rBimg, "png", thumbnailPath.toFile());

        }
        return thumbnailPath;
    }

}

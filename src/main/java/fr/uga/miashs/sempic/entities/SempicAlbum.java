/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
@NamedQueries({
    @NamedQuery(
        name = "query.SempicAlbum.remove",
        query = "DELETE FROM SempicAlbum a WHERE a.id=:id "
    ),
    @NamedQuery(
        name = "query.SempicAlbum.findAlbum",
        query = "SELECT a FROM SempicAlbum a WHERE a.owner =: owner"
    ),
    @NamedQuery(
        name = "query.SempicAlbum.removeByOwner",
        query = "DELETE FROM SempicAlbum a WHERE a.owner =: owner"
    ),
    @NamedQuery(
        name = "query.SempicAlbum.selectByOwner",
        query = "SELECT a FROM SempicAlbum a WHERE a.owner =: owner"
    ),     
    @NamedQuery(
        name = "query.SempicAlbum.findAlbumById",
        query = "SELECT a FROM SempicAlbum a WHERE a.id =: id"
    ),
})

// Entité qui correspond à un album
public class SempicAlbum implements Serializable{
    // Clé primaire
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    // Nom de l'album
    private String name;
    
    // Déclaration d'une relation ManyToOne entre SempicUser et SempicAlbum
    // Un utilisateur peut être propriétaire de plusieurs albums
    // Plusieurs albums peuvent être la propriété d'un seul utilisateur
    @NotNull
    @ManyToOne
    private SempicUser owner;

    public SempicAlbum() {
        
    }
    
    // Définition des accesseurs (get/set) pour que les propriétés d'un SempicUser soient utilisables
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SempicUser getOwner() {
        return owner;
    }

    public void setOwner(SempicUser owner) {
        this.owner = owner;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SempicAlbum other = (SempicAlbum) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "SempicAlbum={" + "id=" + id + ", name=" + name + ", owner=" + owner + '}';
    }
}

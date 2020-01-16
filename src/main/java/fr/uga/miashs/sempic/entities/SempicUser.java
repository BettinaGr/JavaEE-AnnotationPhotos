/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author Jerome David <jerome.david@univ-grenoble-alpes.fr>
 */

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "UniqueEmail", columnNames = {"email"})
})
@NamedQueries({
@NamedQuery(
        name = "query.SempicUser.findAllEager", 
        query = "SELECT DISTINCT u FROM SempicUser u LEFT JOIN FETCH u.groups LEFT JOIN FETCH u.memberOf"
),
@NamedQuery(
        name = "query.SempicUser.readByEmail",
        query = "SELECT DISTINCT u FROM SempicUser u WHERE u.email=:email "
),
@NamedQuery(
        name = "query.SempicUser.login",
        query = "SELECT DISTINCT u FROM SempicUser u WHERE u.email=:email AND u.passwordHash=:passwordHash"
),
@NamedQuery(
        name = "query.SempicUser.remove",
        query = "DELETE FROM SempicUser u WHERE u.id=:id "
)
})
@NamedEntityGraph(
  name = "graph.SempicUser.groups-memberOf",
  attributeNodes = {
    @NamedAttributeNode("groups"),
    @NamedAttributeNode("memberOf"),
  }
)

// Entité qui correspond à un utilisateur
public class SempicUser implements Serializable {
    public final static String PREFIX="/users/";
    
    // Clé primaire
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Nom
    private String lastname;
    
    // Prénom
    private String firstname;
    
    // Mail
    @Email
    private String email;
    
    // Mot de passe hashé
    private String passwordHash;
    
    // Mot de passe 
    @Transient
    private transient String password;
       
    // Déclaration d'une relation OneToMany entre SempicUser et SempicGroup
    // Un utilisateur peut être propriétaire de plusieurs groupes
    // Plusieurs groupes peuvent être la propriété d'un utilisateur
    @OneToMany(mappedBy = "owner",cascade = CascadeType.REMOVE)
    private Set<SempicGroup> groups;

    // Déclaration d'une relation ManyToMany entre SempicUser et SempicGroup
    // Un utilisateur peut être membre de plusieurs groupes
    // Un groupe peut avoir plusieurs membres c'est à dire plusieurs SempicUser
    @ManyToMany(mappedBy = "members" )//,cascade = CascadeType.REMOVE)
    private Set<SempicGroup> memberOf;
    
    // Type de l'utilisateur
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition="VARCHAR(5)")
    private SempicUserType userType;
    
    // Constructeur de SempicUser, initialise le type de l'utisation à User
    public SempicUser() {
        userType=SempicUserType.USER;
    }
    
    // Définition des accesseurs (get/set) pour que les propriétés d'un SempicUser soient utilisables
    public long getId() {
        return id;
    }
    
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public void setPassword(String p) {
        password=p;
    }
    
    public String getPassword() {
        return password;
    }

    public Set<SempicGroup> getGroups() {
        if (groups==null) return Collections.emptySet();
        return Collections.unmodifiableSet(groups);
    }

    public Set<SempicGroup> getMemberOf() {
        if (memberOf==null) return Collections.emptySet();
        return Collections.unmodifiableSet(memberOf);
    }
    
    public SempicUserType getUserType() {
        return userType;
    }

    public void setUserType(SempicUserType userType) {
        this.userType = userType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final SempicUser other = (SempicUser) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

 
    @Override
    public String toString() {
        return "SempicUser{id="+ id + ", "
                + "lastname=" + lastname + ", firstname=" + firstname + ", email=" + email + '}';
    }
}

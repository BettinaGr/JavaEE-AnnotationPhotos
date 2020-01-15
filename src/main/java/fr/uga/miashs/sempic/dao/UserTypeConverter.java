/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.miashs.sempic.dao;

import fr.uga.miashs.sempic.entities.SempicUserType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Jerome David <jerome.david@univ-grenoble-alpes.fr>
 */

// Conversion du type de l'utilisateur 
@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<SempicUserType,String>{

    // SempicUserType convertit en String
    @Override
    public String convertToDatabaseColumn(SempicUserType attribute) {
        return attribute.name();
    }

    // String convertit en SempicUserType
    @Override
    public SempicUserType convertToEntityAttribute(String dbData) {
        return SempicUserType.valueOf(dbData);
    }
    
}

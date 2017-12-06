package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Bignon on 11/27/17.
 */

@Component
public class TypeReponseService implements DatabaseConstants{

    TypeReponseCRUD typeReponseCRUD;

    @PostConstruct
    public void init(){
        typeReponseCRUD = new TypeReponseCRUD();
    }

    public void create(TypeReponse typeReponse) throws SQLException {
         typeReponseCRUD.create(typeReponse);
    }

    public void update(TypeReponse typeReponse) throws SQLException {
        typeReponseCRUD.update(typeReponse);
    }

    public TypeReponse delete(int id) throws SQLException {
        TypeReponse typeReponse = read(id);
        typeReponseCRUD.delete(typeReponse);

        return typeReponse;
    }

    public TypeReponse read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<TypeReponse> typeReponses = typeReponseCRUD.read(criterias);

        return typeReponses.get(0);
    }


    public List<TypeReponse> read(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<TypeReponse> typeReponses = null;
        if(criterias == null && fields == null)
           typeReponses =  typeReponseCRUD.read();
        else if(criterias!= null && fields==null)
            typeReponses = typeReponseCRUD.read(criterias);
        else if(criterias== null && fields!=null)
            typeReponses = typeReponseCRUD.read(fields);
        else
            typeReponses = typeReponseCRUD.read(criterias, fields);

        return typeReponses;
    }
    
    public int getCountTypeReponses() throws SQLException{
        return typeReponseCRUD.read().size();
    }

}

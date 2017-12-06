package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.ReponseCRUD;
import com.bootcamp.entities.Reponse;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */

@Component
public class ReponseService implements DatabaseConstants{

    ReponseCRUD reponseCRUD;

    @PostConstruct
    public void init(){
        reponseCRUD = new ReponseCRUD();
    }

    public void create(Reponse reponse) throws SQLException {
         reponseCRUD.create(reponse);
    }

    public void update(Reponse reponse) throws SQLException {
        reponseCRUD.update(reponse);
    }

    public Reponse delete(int id) throws SQLException {
        Reponse reponse = read(id);
        reponseCRUD.delete(reponse);

        return reponse;
    }

    public Reponse read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Reponse> reponses = reponseCRUD.read(criterias);

        return reponses.get(0);
    }



    public List<Reponse> read(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Reponse> reponses = null;
        if(criterias == null && fields == null)
            reponses =  reponseCRUD.read();
        else if(criterias!= null && fields==null)
            reponses = reponseCRUD.read(criterias);
        else if(criterias== null && fields!=null)
            reponses = reponseCRUD.read(fields);
        else
            reponses = reponseCRUD.read(criterias, fields);

        return reponses;
    }

}

package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.ReponseCRUD;
import com.bootcamp.entities.Reponse;
import com.bootcamp.entities.TypeReponse;
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
public class ReponseService implements DatabaseConstants {

//    public ReponseService() {
//    }
      
    public Reponse create(Reponse reponse) throws SQLException {
        ReponseCRUD.create(reponse);
        return reponse;
    }

    public Reponse update(Reponse reponse) throws SQLException {
        ReponseCRUD.update(reponse);
        return reponse;
    }

    public Reponse delete(int id) throws SQLException {
        Reponse reponse = read(id);
        ReponseCRUD.delete(reponse);

        return reponse;
    }

    public Reponse read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Reponse> reponses = ReponseCRUD.read(criterias);

        return reponses.get(0);
    }
    
    public int countResponseByTypeResponse(TypeReponse typeReponse) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("typeReponse", "=", typeReponse));
        List<Reponse> reponses = ReponseCRUD.read(criterias);

        return reponses.size();
    }

    public List<Reponse> read(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Reponse> reponses = null;
        if (criterias == null && fields == null) {
            reponses = ReponseCRUD.read();
        } else if (criterias != null && fields == null) {
            reponses = ReponseCRUD.read(criterias);
        } else if (criterias == null && fields != null) {
            reponses = ReponseCRUD.read(fields);
        } else {
            reponses = ReponseCRUD.read(criterias, fields);
        }

        return reponses;
    }

}

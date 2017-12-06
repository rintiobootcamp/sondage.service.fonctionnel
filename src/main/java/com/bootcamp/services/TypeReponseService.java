package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.ws.usecases.pivotone.TypeReponseWS;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.TypeReponseCRUD;
import com.bootcamp.entities.Question;
import com.bootcamp.entities.TypeReponse;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bignon on 11/27/17.
 */
@Component
public class TypeReponseService implements DatabaseConstants {

    public TypeReponse create(TypeReponse typeReponse) throws SQLException {
        TypeReponseCRUD.create(typeReponse);
        return typeReponse;
    }

    public TypeReponse update(TypeReponse typeReponse) throws SQLException {
        TypeReponseCRUD.update(typeReponse);
        return typeReponse;
    }

    public TypeReponse delete(int id) throws SQLException {
        TypeReponse typeReponse = read(id);
        TypeReponseCRUD.delete(typeReponse);
        return typeReponse;
    }

    public TypeReponse read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<TypeReponse> typeReponses = TypeReponseCRUD.read(criterias);
        return typeReponses.get(0);
    }

    public List<TypeReponseWS> getByQuestion(Question question) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("question", "=", question));
        List<TypeReponseWS> typeReponseWSs = new ArrayList<TypeReponseWS>();
        List<TypeReponse> typeReponses = TypeReponseCRUD.read(criterias);
        ReponseService reponseService = new ReponseService();

        for (TypeReponse typeReponse : typeReponses) {
            TypeReponseWS typeReponseWS = new TypeReponseWS();
            typeReponseWS.setTitre(typeReponse.getTitre());
            typeReponseWS.setId(typeReponse.getId());
            typeReponseWS.setNombreDeReponse(reponseService.countResponseByTypeResponse(typeReponse));
            typeReponseWSs.add(typeReponseWS);
        }

        return typeReponseWSs;
    }

    public TypeReponseWS toTypeReponseWS(TypeReponse typeReponse) throws SQLException {
        ReponseService reponseService = new ReponseService();

        TypeReponseWS typeReponseWS = new TypeReponseWS();
        typeReponseWS.setTitre(typeReponse.getTitre());
        typeReponseWS.setId(typeReponse.getId());
        typeReponseWS.setNombreDeReponse(reponseService.countResponseByTypeResponse(typeReponse));

        return typeReponseWS;
    }

//    public List<TypeReponse> read(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
//        Criterias criterias = RequestParser.getCriterias(request);
//        List<String> fields = RequestParser.getFields(request);
//        List<TypeReponse> typeReponses = null;
//        if(criterias == null && fields == null)
//           typeReponses =  TypeReponseCRUD.read();
//        else if(criterias!= null && fields==null)
//            typeReponses = TypeReponseCRUD.read(criterias);
//        else if(criterias== null && fields!=null)
//            typeReponses = TypeReponseCRUD.read(fields);
//        else
//            typeReponses = TypeReponseCRUD.read(criterias, fields);
//
//        return typeReponses;
//    }
//    
//    public int getCountTypeReponses() throws SQLException{
//        return TypeReponseCRUD.read().size();
//    }
}

package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.QuestionCRUD;
import com.bootcamp.entities.Question;
import com.bootcamp.repositories.QuestionRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class QuestionService implements DatabaseConstants {

    QuestionCRUD questionCRUD;

    @PostConstruct
    public void init() {
        questionCRUD = new QuestionCRUD();
    }

//    local.public int  create(QuestionUWs questionUWs) throws SQLException {
//         Question question = new Question();
//         question.setDescription(questionUWs.getDescription());
//         question.setNom(questionUWs.getNom());
//         question.setDateCreation(System.currentTimeMillis());
//         question.setDateMiseAJour(System.currentTimeMillis());
//         questionCRUD.create(question);
//
//         return question.getId();
//    }
    public void update(Question question) throws SQLException {
        questionCRUD.update(question);
    }

    public Question delete(int id) throws SQLException {
        Question question = read(id);
        questionCRUD.delete(question);

        return question;
    }

    public Question read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Question> questions = questionCRUD.read(criterias);

        return questions.get(0);
    }

    public List<Question> read(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Question> questions = null;
        if (criterias == null && fields == null) {
            questions = questionCRUD.read();
        } else if (criterias != null && fields == null) {
            questions = questionCRUD.read(criterias);
        } else if (criterias == null && fields != null) {
            questions = questionCRUD.read(fields);
        } else {
            questions = questionCRUD.read(criterias, fields);
        }

        return questions;
    }


//    private List<QuestionUWs> convertPilerToQuestionUWS(List<Question> questions){
//        List<QuestionUWs> questionUWss = new ArrayList<>();
//        for(Question question: questions){
//           QuestionUWs questionUWs = new QuestionUWs();
//        }
//    }

}

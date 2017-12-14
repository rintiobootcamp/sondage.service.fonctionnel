package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.models.Rule;
import com.bootcamp.commons.ws.usecases.pivotone.QuestionWS;
import com.bootcamp.crud.QuestionCRUD;
import com.bootcamp.entities.Question;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class QuestionService implements DatabaseConstants {

    public void create(Question question) throws SQLException {
        question.setDateCreation(System.currentTimeMillis());
        question.setDateMiseAJour(System.currentTimeMillis());

        QuestionCRUD.create(question);
        //return question;
    }

    public Question update(Question question) throws SQLException {
        question.setDateMiseAJour(System.currentTimeMillis());

        QuestionCRUD.update(question);
        return question;
    }

    public void participer(Question question, String reponse) throws SQLException {


            if (question.getTypeReponses().containsKey(reponse)) {
                Long l = question.getTypeReponses().get(reponse);
                HashMap lareponse=new HashMap();
                lareponse.put(reponse,++l);
                question.setTypeReponses(lareponse);
                QuestionCRUD.update(question);
            }
        }


    public Question delete(Question question) throws SQLException {
        QuestionCRUD.delete(question);
        return question;
    }

//    public QuestionWS toQuestionWS(Question question) throws SQLException {
//        TypeReponseService typeReponseService = new TypeReponseService();
//        QuestionWS questionWS = new QuestionWS();
//        questionWS.setId(question.getId());
//        questionWS.setSujet(question.getSujet());
//        questionWS.setEntityId(question.getEntityId());
//        questionWS.setEntityType(question.getEntityType());
//        questionWS.setDateCreationQuestion(question.getDateCreation());
//        questionWS.setDateMiseAJourQuestion(question.getDateMiseAJour());
//        questionWS.setReponsesType(typeReponseService.getByQuestion(question));
//        return questionWS;
//    }

    public List<Question> readAll() throws SQLException {
        List<Question> questions = QuestionCRUD.read();

        return questions;
    }

    public Question read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        Rule rule = new Rule();
        rule.setColumn("id");
        rule.setOperator("=");
        rule.setValue(id);

        Criteria criteria = new Criteria();
        criteria.setRule(rule);
        criteria.setEntityClass(Question.class);
        criterias.addCriteria(criteria);

        List<Question> questions = QuestionCRUD.read(criterias);


        return questions.get(0);
    }

//    private List<QuestionUWs> convertPilerToQuestionUWS(List<Question> questions){
//        List<QuestionUWs> questionUWss = new ArrayList<>();
//        for(Question question: questions){
//           QuestionUWs questionUWs = new QuestionUWs();
//        }
//    }

}

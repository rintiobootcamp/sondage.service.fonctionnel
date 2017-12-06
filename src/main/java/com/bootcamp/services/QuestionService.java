package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.ws.usecases.pivotone.QuestionWS;
import com.bootcamp.crud.QuestionCRUD;
import com.bootcamp.crud.TypeReponseCRUD;
import com.bootcamp.entities.Question;
import com.bootcamp.entities.TypeReponse;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class QuestionService implements DatabaseConstants {

    public Question create(Question question) throws SQLException {
        question.setDateCreation(System.currentTimeMillis());
        question.setDateMiseAJour(System.currentTimeMillis());
                List<TypeReponse> typeReponses = question.getTypeReponses();

        QuestionCRUD.create(question);
        
        for (TypeReponse typeReponse : typeReponses) {
            typeReponse.setQuestion(question);
           TypeReponseCRUD.update(typeReponse);
       }
        return question;
    }
    
    public Question update(Question question) throws SQLException {
        question.setDateMiseAJour(System.currentTimeMillis());
        QuestionCRUD.update(question);
        return question;
    }

    public Question delete(Question question) throws SQLException {
        QuestionCRUD.delete(question);
        return question;
    }

    public QuestionWS toQuestionWS(Question question) throws SQLException {
        TypeReponseService typeReponseService = new TypeReponseService();
        QuestionWS questionWS = new QuestionWS();
        questionWS.setId(question.getId());
        questionWS.setSujet(question.getSujet());
        questionWS.setEntityId(question.getEntityId());
        questionWS.setEntityType(question.getEntityType());
        questionWS.setDateCreationQuestion(question.getDateCreation());
        questionWS.setDateMiseAJourQuestion(question.getDateMiseAJour());
        questionWS.setReponsesType(typeReponseService.getByQuestion(question));
        return questionWS;
    }

    public List<QuestionWS> readAll() throws SQLException {
        List<QuestionWS> questionWSs = new ArrayList<QuestionWS>();
        List<Question> questions = QuestionCRUD.read();
        for (Question question : questions) {
            QuestionWS questionWS = new QuestionWS();
            questionWS = toQuestionWS(question);
            questionWSs.add(questionWS);
        }
        return questionWSs;
    }


//    private List<QuestionUWs> convertPilerToQuestionUWS(List<Question> questions){
//        List<QuestionUWs> questionUWss = new ArrayList<>();
//        for(Question question: questions){
//           QuestionUWs questionUWs = new QuestionUWs();
//        }
//    }

}

package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.enums.EntityType;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.models.Rule;
import com.bootcamp.commons.ws.usecases.pivotone.QuestionWS;
import com.bootcamp.commons.ws.usecases.pivotone.TypeReponseWS;
import com.bootcamp.crud.QuestionCRUD;
import com.bootcamp.entities.Question;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class QuestionService implements DatabaseConstants {
    QuestionWS questionWS;
    TypeReponseWS typeReponseWS;
    
    private static Logger logger = LogManager.getLogger(QuestionService.class);
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


    public Question participer(Question question, String reponse) throws SQLException {

        HashMap<String,Long> typeReponses =  question.getTypeReponses();
        if (typeReponses.containsKey(reponse)) {
            Long l = typeReponses.get(reponse);
            l = l + 1;
            typeReponses.put(reponse, l);
            question.setTypeReponses(typeReponses);
            logger.debug("-------------------- " + question.getTypeReponses().toString());
            QuestionCRUD.update(question);
        }
        return question;
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
    
    public int getAllQuestionByEntity(EntityType entityType) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria(new Rule("entityType", "=", entityType), null));
        return QuestionCRUD.read(criterias).size();
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
    
    public int countQuestion () throws SQLException{
        
        int count = QuestionCRUD.read().size();
        
        return count;
    }
    
    public StatQuestion getStatQuestion(int idQuestion) throws SQLException {
        
        
        
        StatQuestion statQuestion = new StatQuestion();
        
        QuestionService questionService = new QuestionService();
        Question question = questionService.read(idQuestion);
        HashMap<String,Long> typeReponses =  question.getTypeReponses();
        
        Long nombreReponseValeur1 = typeReponses.get("oui");
        Long nombreReponseValeur2 = typeReponses.get("non");
        Long nombreReponseValeur3 = typeReponses.get("autre");
        
        Long nombreReponse = nombreReponseValeur1+nombreReponseValeur2+nombreReponseValeur3;
        
        double pourcentageNombreReponseValeur1 = (double)nombreReponseValeur1/(double)nombreReponse;
        double pourcentageNombreReponseValeur2 = (double)nombreReponseValeur2/(double)nombreReponse;
        double pourcentageNombreReponseValeur3 = (double)nombreReponseValeur3/(double)nombreReponse;
//        List<Long> valeurs = (List<Long>) typeReponses.values();
//        for (valeurs valeur : val) {
//            
//        }
        
//        if ( n.toString()=="" || n.toString()==" "  || n.toString()=="0" ) {
//            statQuestion.setNombreReponse(0);
//            statQuestion.setNombreReponseValeur1(nombreReponseValeur1);
//            statQuestion.setNombreReponseValeur2(nombreReponseValeur2);
//            statQuestion.setNombreReponseValeur3(nombreReponseValeur3);
//            statQuestion.setPourcentageNombreReponseValeur1(pourcentageNombreReponseValeur1);
//            statQuestion.setPourcentageNombreReponseValeur2(pourcentageNombreReponseValeur2);
//            statQuestion.setPourcentageNombreReponseValeur3(pourcentageNombreReponseValeur3);
//        } else {
            statQuestion.setNombreReponse(nombreReponse);
            statQuestion.setNombreReponseValeur1(nombreReponseValeur1);
            statQuestion.setNombreReponseValeur2(nombreReponseValeur2);
            statQuestion.setNombreReponseValeur3(nombreReponseValeur3);
            statQuestion.setPourcentageNombreReponseValeur1(pourcentageNombreReponseValeur1);
            statQuestion.setPourcentageNombreReponseValeur2(pourcentageNombreReponseValeur2);
            statQuestion.setPourcentageNombreReponseValeur3(pourcentageNombreReponseValeur3);
//        }
        
        
//        for (HashMap.Entry<String, Long> entry : typeReponses.entrySet()) {
//            String key = entry.getKey();
//            Long valeur = entry.getValue();
//            
//        }
        
//        statQuestion.getTypeReponses();
//        int nombreReponse = question.getTypeReponses().size();
        
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria(new Rule("entityType", "=", entityType), null));
//        return QuestionCRUD.read(criterias).size();

        
        return statQuestion;
    }
    

}

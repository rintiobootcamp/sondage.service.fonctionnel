package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.enums.EntityType;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.models.Rule;
import com.bootcamp.commons.ws.usecases.pivotone.QuestionWS;
import com.bootcamp.commons.ws.usecases.pivotone.TypeReponseWS;
import com.bootcamp.crud.QuestionCRUD;
import com.bootcamp.entities.LikeTable;
import com.bootcamp.entities.Question;
import com.rintio.elastic.client.ElasticClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class QuestionService implements DatabaseConstants {

    QuestionWS questionWS;
    TypeReponseWS typeReponseWS;
    ElasticClient elasticClient ;

    private static Logger logger = LogManager.getLogger(QuestionService.class);

    @PostConstruct
    public void init(){
        elasticClient = new ElasticClient();
    }
    public void create(Question question) throws Exception {
        question.setDateCreation(System.currentTimeMillis());
        question.setDateMiseAJour(System.currentTimeMillis());

        QuestionCRUD.create(question);
        createAllIndexQuestion();
        //return question;
    }

    public void getprime(){

    }

    public Question update(Question question) throws Exception {
        question.setDateMiseAJour(System.currentTimeMillis());

        QuestionCRUD.update(question);
        createAllIndexQuestion();
        return question;
    }

    public Question participer(Question question, String reponse) throws Exception {

        HashMap<String, Long> typeReponses = question.getTypeReponses();
        if (typeReponses.containsKey(reponse)) {
            Long l = typeReponses.get(reponse);
            l = l + 1;
            typeReponses.put(reponse, l);
            question.setTypeReponses(typeReponses);
            logger.debug("-------------------- " + question.getTypeReponses().toString());
            QuestionCRUD.update(question);
        }
        createAllIndexQuestion();
        return question;
    }

    public Question delete(Question question) throws Exception {
        QuestionCRUD.delete(question);
        createAllIndexQuestion();
        return question;
    }

    public boolean delete(int id) throws Exception{
        Question question;
        try {
            question = this.read(id);
            this.delete(question);
            return true;
        } catch (SQLException ex) {
            logger.info(ex.getSQLState());
            return false;
        }

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
    public List<Question> readAll() throws Exception {
//        List<Question> questions = QuestionCRUD.read();

        return getAllQuestion();
    }

    public int getAllQuestionByEntity(EntityType entityType) throws Exception {
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria(new Rule("entityType", "=", entityType), null));
        return (int) getAllQuestion().stream().filter(t->t.getEntityType().equalsIgnoreCase(entityType.toString())).count();
    }

    public Question read(int id) throws Exception {
//        Criterias criterias = new Criterias();
//        Rule rule = new Rule();
//        rule.setColumn("id");
//        rule.setOperator("=");
//        rule.setValue(id);
//
//        Criteria criteria = new Criteria();
//        criteria.setRule(rule);
//        criteria.setEntityClass(Question.class);
//        criterias.addCriteria(criteria);

//        List<Question> questions = QuestionCRUD.read(criterias);
        Question question = getAllQuestion().stream().filter(t->t.getId()==id).findFirst().get();

        return question;
    }

    public List<Question> getUneQuestion() throws Exception{
        return  getAllQuestion().stream().filter(t->t.isUne()).collect(Collectors.toList());
    }

//    private List<QuestionUWs> convertPilerToQuestionUWS(List<Question> questions){
//        List<QuestionUWs> questionUWss = new ArrayList<>();
//        for(Question question: questions){
//           QuestionUWs questionUWs = new QuestionUWs();
//        }
//    }
    public int countQuestion() throws Exception {

//        int count = QuestionCRUD.read().size();
        int count = getAllQuestion().size();

        return count;
    }

    public StatQuestion getStatQuestion(int idQuestion) throws Exception {

        StatQuestion statQuestion = new StatQuestion();

        QuestionService questionService = new QuestionService();
        Question question = questionService.read(idQuestion);
        HashMap<String, Long> typeReponses = question.getTypeReponses();

//        for (Map.Entry<String, Long> entry : typeReponses.entrySet()) {
//            String key = entry.getKey();
//            Long value = entry.getValue();
//            
//        }
        Long nombreReponse = 0L;
        HashMap<String, Long> valeurReponse = new HashMap<>();
        HashMap<String, Double> pourcentageValeurReponse = new HashMap<>();

        for (String s : typeReponses.keySet()) {
            String key = s;
            Long value = typeReponses.get(s);
            valeurReponse.put(key, value);
            nombreReponse += value;

        }

        for (String s : typeReponses.keySet()) {
            String key = s;
            Long value = typeReponses.get(s);
            if ((double) nombreReponse == 0) {
                pourcentageValeurReponse.put(key, arrondi(0, 4));
            } else {
                pourcentageValeurReponse.put(key, arrondi((double) value / (double) nombreReponse, 4));
            }
        }

//        nombreReponse = 0L;
//        for (String s : typeReponses.keySet()) {
//            System.out.print(typeReponses.get(s)+"\n");
//            nombreReponse+=typeReponses.get(s);
//        }
//        System.out.println("-------\n"+nombreReponse);
        statQuestion.setNombreReponse(nombreReponse);
        statQuestion.setValeurReponse(valeurReponse);
        statQuestion.setPourcentageValeurReponse(pourcentageValeurReponse);

        return statQuestion;
    }

    /**
     * Check if a question exists
     *
     * @param id
     * @return a boolean
     * @throws Exception
     */
    public boolean exists(int id) throws Exception {
        Question question = read(id);
        if (question != null) {
            return true;
        }
        return false;
    }

    /**
     * rounded a double number
     *
     * @return a double
     */
    public static double arrondi(double A, int B) {
        return (double) ((int) (A * Math.pow(10, B) + 0.5)) / Math.pow(10, B);
    }

    public List<Question> getAllQuestion() throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Object> objects = elasticClient.getAllObject("questions");
        ModelMapper modelMapper = new ModelMapper();
        List<Question> rest = new ArrayList<>();
        for(Object obj:objects){
            rest.add(modelMapper.map(obj,Question.class));
        }
        return rest;
    }

    public boolean createAllIndexQuestion()throws Exception{
        List<Question> questions = QuestionCRUD.read();
        for (Question question : questions){
            elasticClient.creerIndexObjectNative("questions","question",question,question.getId());
        }
        return true;
    }
}

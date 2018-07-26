package com.bootcamp.controllers;

import com.bootcamp.commons.enums.EntityType;
import com.bootcamp.commons.ws.usecases.pivotone.TypeReponseWS;
import com.bootcamp.entities.Question;
import com.bootcamp.services.QuestionService;
import com.bootcamp.services.StatQuestion;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController("QuestionController")
@RequestMapping("/sondages")
@Api(value = "Question API", description = "Question API")
@CrossOrigin(origins = "*")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    //    @Autowired
//    TypeReponseService questionService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/elasticdata",method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create Elasticsearch indexes", notes = "Create Elasticsearch indexes")
    public ResponseEntity<String> createIndexs() throws Exception {
        String retour = "NOT DONE";
        if (questionService.createAllIndexQuestion())
            retour = "DONE";
        return new ResponseEntity<>(retour, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new question", notes = "Create a new question")
    public ResponseEntity<Question> create(@RequestBody @Valid Question question) throws Exception {
//
        HttpStatus httpStatus = null;

        questionService.create(question);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(question, httpStatus);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{idQuestion}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Delete a question", notes = "Create a question")
    public ResponseEntity<Boolean> delete(@PathVariable("idQuestion") int idQuestion) throws Exception {
//
        HttpStatus httpStatus = null;

        boolean b = questionService.delete(idQuestion);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(b, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read all the questions", notes = "Read all the questions")
    public ResponseEntity<List<Question>> read() throws Exception {

        HttpStatus httpStatus = null;
        List<Question> questions = questionService.readAll();
        httpStatus = HttpStatus.OK;

        return new ResponseEntity<>(questions, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats/{entityType}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read all debat on entity", notes = "Read all debat on entity")
    public ResponseEntity<Integer> readAllQuestionByEntity(@PathVariable("entityType") String entityType) throws Exception {
        EntityType entite = EntityType.valueOf(entityType);
        int nbQuestion = 0;
        HttpStatus httpStatus = null;

        try {
            nbQuestion = questionService.getAllQuestionByEntity(entite);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Integer>(nbQuestion, httpStatus);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/{idQuestion}/{reponse}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a response for a question", notes = "Create a response for a question")
    public ResponseEntity<Question> particiter(@PathVariable(name = "idQuestion") int idQuestion, @PathVariable(name = "reponse") String reponse) throws Exception {

        //String reponse = request.getQueryString();
        Question question = questionService.read(idQuestion);
        question = questionService.participer(question, reponse);
        HttpStatus httpStatus = null;
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(question, httpStatus);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/count")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Count number of Questions", notes = "Count number of Questions")
    public ResponseEntity<Integer> count() throws Exception {
        int count = questionService.countQuestion();
        HttpStatus httpStatus = null;
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(count, httpStatus);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/statistiques/{idSondage}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get statistiques for Sondage", notes = "Get statistiques for Sondage")
    public ResponseEntity<StatQuestion> getStatQuestion(@PathVariable(name = "idSondage") int idQuestion) throws Exception {
        StatQuestion statQuestion = questionService.getStatQuestion(idQuestion);
        HttpStatus httpStatus = null;
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<StatQuestion>(statQuestion, httpStatus);

    }

}

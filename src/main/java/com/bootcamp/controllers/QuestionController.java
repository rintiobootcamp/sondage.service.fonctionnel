package com.bootcamp.controllers;

import com.bootcamp.entities.Question;
import com.bootcamp.services.QuestionService;
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

    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new question", notes = "Create a new question")
    public ResponseEntity<Question> create(@RequestBody @Valid Question question) throws SQLException {
//
        HttpStatus httpStatus = null;
       
            questionService.create(question);
            httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(question, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read all the questions", notes = "Read all the questions")
    public ResponseEntity<List<Question>> read() throws SQLException {

        HttpStatus httpStatus = null;
        List<Question> questions = questionService.readAll();
        httpStatus = HttpStatus.OK;

        return new ResponseEntity<>(questions, httpStatus);
    }
    
    @RequestMapping(method = RequestMethod.POST, value="/{idQuestion}/{reponse}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a post for a question", notes = "Create a post for a question")
    public ResponseEntity<Question> particiter(@PathVariable(name = "idQuestion") int idQuestion, @PathVariable(name = "reponse") String reponse) throws SQLException {

        //String reponse = request.getQueryString();
        Question question = questionService.read(idQuestion);
        questionService.participer(question, reponse);
        HttpStatus httpStatus = null;
        httpStatus = HttpStatus.OK;

        return new ResponseEntity<>(question, httpStatus);
        
    }
}

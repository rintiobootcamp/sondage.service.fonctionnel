package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.entities.Reponse;
import com.bootcamp.services.ReponseService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController("ReponseController")
@RequestMapping("/reponses")
@Api(value = "Reponse API", description = "Reponse API")
public class ReponseController {

    @Autowired
    ReponseService reponseService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(method = RequestMethod.POST,value = "/")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new Reponse", notes = "Create a new Reponse")
    public ResponseEntity<Reponse> create(@RequestBody @Valid Reponse reponse) {

        HttpStatus httpStatus = null;

        try {
            reponseService.create(reponse);
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Reponse>(reponse, httpStatus);
    }

//
//    @RequestMapping(method = RequestMethod.PUT, value = "/")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Update a new Reponse", notes = "Update a new Reponse")
//    public ResponseEntity<Reponse> update(@RequestBody @Valid Reponse Reponse) {
//
//        HttpStatus httpStatus = null;
//
//        try {
//            reponseService.update(Reponse);
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException exception){
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<Reponse>(Reponse, httpStatus);
//    }
//
//    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Delete a Reponse", notes = "Delete a Reponse")
//    public ResponseEntity<Reponse> delete(@PathVariable(name = "id") int id) {
//        Reponse reponse = new Reponse();
//        HttpStatus httpStatus = null;
//
//        try {
//            reponse = reponseService.delete(id);
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException exception){
//            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<Reponse>(reponse, httpStatus);
//    }
//
//
//    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Read a Reponse", notes = "Read a Reponse")
//    public ResponseEntity<Reponse> read(@PathVariable(name = "id") int id) {
//
//        Reponse reponse = new Reponse();
//        HttpStatus httpStatus = null;
//
//        try {
//            reponse = reponseService.read(id);
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException exception){
//            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<Reponse>(reponse, httpStatus);
//    }
//
//    @RequestMapping(method = RequestMethod.GET)
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Read a Reponse", notes = "Read a Reponse")
//    public ResponseEntity<List<Reponse>> read() {
//        List<Reponse> reponses=new ArrayList<Reponse>();
//        HttpStatus httpStatus = null;
//
//        try {
//             reponses = reponseService.read(request);
//
//            httpStatus = HttpStatus.OK;
//        }catch (SQLException | IllegalAccessException | DatabaseException | InvocationTargetException exception){
//            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
//
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<List<Reponse>>(reponses, httpStatus);BignonFebron amuztadji
//    }
}

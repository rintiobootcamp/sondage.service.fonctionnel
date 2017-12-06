package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.ws.constants.CommonsWsConstants;
import com.bootcamp.entities.TypeReponse;
import com.bootcamp.services.TypeReponseService;
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
import java.util.HashMap;
import java.util.List;


@RestController("TypeReponseController")
@RequestMapping("/typeReponse")
@Api(value = "TypeReponse API", description = "TypeReponse API")
public class TypeReponseController {

    @Autowired
    TypeReponseService typeReponseService;
    @Autowired
    HttpServletRequest request;


    @RequestMapping(method = RequestMethod.GET, value = "/")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a typeReponse", notes = "Read a typeReponse")
    public ResponseEntity<List<TypeReponse>> read() throws InvocationTargetException, SQLException, DatabaseException, IllegalAccessException {
        List<TypeReponse> typeReponses = typeReponseService.read(request);
        return new ResponseEntity<List<TypeReponse>>(typeReponses, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/count")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Count typeReponses", notes = "Count typeReponses")
    public ResponseEntity<HashMap<String,Integer>> countTypeReponses() throws SQLException {
        
        int count = typeReponseService.getCountTypeReponses();
        HashMap<String,Integer> map = new HashMap<>();
        map.put(CommonsWsConstants.MAP_COUNT_KEY, count);
        

        return new ResponseEntity<HashMap<String,Integer>>(map, HttpStatus.OK);
    }

}

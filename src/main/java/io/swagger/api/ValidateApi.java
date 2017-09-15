package io.swagger.api;


import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@Api(value = "validate", description = "the validate API")
public interface ValidateApi {

    @ApiOperation(value = "token validate check", notes = "token validate check", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/validate",
        method = RequestMethod.HEAD)
    ResponseEntity<Void> validateHead(@ApiParam(value = "User Authorization BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization, @RequestParam String client);

}

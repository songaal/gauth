package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.PendingUserRequest;
import io.swagger.model.PendingUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Api(value = "activate", description = "the activate API")
public interface ActivateApi {

    @ApiOperation(value = "create pending email", notes = "create pending email", response = PendingUserResponse.class, tags={  })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = PendingUserResponse.class) })
    @RequestMapping(value = "/register",
        method = RequestMethod.POST)
    ResponseEntity<?> registerPost(@ApiParam(value = "") @RequestBody PendingUserRequest pendingUserRequest);

    @ApiOperation(value = "active pending email", notes = "active pending email", response = PendingUserResponse.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PendingUserResponse.class) })
    @RequestMapping(value = "/activate",
            method = RequestMethod.POST)
    ResponseEntity<?> activatesPost(@ApiParam(value = "") @RequestParam(value = "activateKey" ,required=true ) String activateKey);

}

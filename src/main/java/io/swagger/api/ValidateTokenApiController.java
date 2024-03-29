package io.swagger.api;


import io.swagger.annotations.ApiParam;
import io.swagger.model.Token;
import io.swagger.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Controller
public class ValidateTokenApiController implements ValidateTokenApi {

    private static Logger logger = LoggerFactory.getLogger(ValidateTokenApiController.class);

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> validateTokenHead(@ApiParam(value = "User Authentication BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            Token registerToken = tokenService.isTokenValid(authorization);
            if(registerToken == null){
                throw new ApiException("invalid token");
            }
            tokenService.refreshTokenExpireDate(authorization);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("usersPost error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), httpStatus);
        }
    }

}

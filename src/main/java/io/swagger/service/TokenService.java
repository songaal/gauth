package io.swagger.service;

import io.swagger.model.Token;
import io.swagger.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface TokenService {

    /*
     * 로그인 토큰 생성
     */
    Token createToken(User user) throws Exception;

    /*
     * 토큰 아이디로 토큰 정보 조회
     */
    Token findByToken(String tokenId) throws Exception;

    /*
     * 토큰 아이디로 토큰 정보 삭제
     */
    void deleteToekn(String tokenId);

    /*
     * 토큰 전체 조회
     */
    List<Token> selectToken();

    /*
     * 토큰 유효성 검사
     */
    boolean isTokenValidate(String token, String client) throws Exception;

    /*
     * 유저로 토큰 정보 조회
     * 유저아이디, 클라이언트아이디 필수
     */
    Token findByUserToToken(User user) throws Exception;

}

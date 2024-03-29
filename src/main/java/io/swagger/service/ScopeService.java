package io.swagger.service;


import io.swagger.model.Scope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface ScopeService {

    /*
     * scope 조회
     */
    Scope findScope(Scope scope) throws Exception;

    /*
     * 클라이언트의 scope 조회
     */
    List<Scope> selectClientScope(String clientId);

    /*
     * scope 등록
     */
    Scope insertScope(Scope scope) throws Exception;

    /*
     * scope 삭제
     */
    void deleteScope(Scope scope) throws Exception;

    /*
     * 클라이언트 연결 scope 삭제
     */
    void deleteClientByScope(Scope scope) throws Exception;

    /*
     * scope 수정
     */
    Scope updateScope(Scope scope) throws Exception;

    /*
     * 클라이언트의 default scope 조회
     */
    List<Scope> findDefaultByScopes(String clientId);
}

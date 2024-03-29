package io.swagger.service;

import io.swagger.model.AuthenticationRequest;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 유저와 클라이언트 연결을 관리
 *
 * @author 김준우
 * @version 1.0
 * @see
 * @since 2017.09.11
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface UserClientScopeService {


    /*
     * 유저 클라이언트 등록
     */
    List<UserClientScope> insertUserClientScope(UserClientScope userClientScope);

    /*
     * 유저 스코프 등록
     */
    List<UserClientScope> insertUserScope(UserClientScope userClientScope);

    /*
     * 유저 클라이언트 삭제
     */
    void deleteUserClientScope(UserClientScope userClientScope);

    /*
     * 연결 관계 조회
     */
    List<UserClientScope> findRelation(UserClientScope userClientScope);
    /*
     * 유저 삭제
     */
    public void deleteClient(String clientId);

    /*
     * 유저로 관계 조회
     */
    List<UserClientScope> selectUserMappingList(User user);

//    /*
//     * 클라이언트로 관계 조회
//     */
//    List<UserClientScope> selectClientMappingList(Client client);

//    /*
//     * 클라이언트로 전체 삭제
//     */
//    void deleteClient(String clientId);

    /*
     * 클라이언트 등록된 관계 수
     */
    Integer findUserCount(int userCode);

    /*
     * 유저가 클라이언트 등록 여부를 확인
     */
    boolean isUserClientScope(AuthenticationRequest user);

//    /*
//     * 유저와 클라이언트 ScopeId 정보 조회
//     */
//    List<String> findByScopeIdList(UserClientScope userClientScope);

    /*
     * 클라이언트의 스코프 제거
     */
    void deleteClientScope(UserClientScope userClientScope);

    /*
     * 전체 조회시 사용되는 클라이언트 정보 조회
     */
    List<UserClientScope> findByUserSearchList(ArrayList<Integer> searchUser);

    /*
     * 유저 스코프 삭제
     */
    void deleteUserScope(UserClientScope userClientScope);
    /*
     * 유저 코드의 모든 관계 조회
     */
    List<UserClientScope> findUserCodeByRelation(int userCode);
}
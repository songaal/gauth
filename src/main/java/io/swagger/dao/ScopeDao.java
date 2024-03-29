package io.swagger.dao;

import io.swagger.model.Scope;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScopeDao {

    @Autowired
    private SqlSession sqlSession;
//
//    public List<Scope> findClientIdByScopes(Scope scope) {
//        return sqlSession.selectList("scope.findClientIdByScopes", scope);
//    }

    public void insertScope(Scope scope) {
        sqlSession.insert("scope.insertScope", scope);
    }

    public void deleteScope(Scope scope) {
        sqlSession.delete("scope.deleteScope", scope);
    }

    public void deleteClientByScope(Scope scope) {
        sqlSession.delete("scope.deleteClientByScope", scope);
    }

    public Scope findScope(Scope scope) {
        return  sqlSession.selectOne("scope.findScopes", scope);
    }

    public List<Scope> findScopes(Scope scope) {
        return  sqlSession.selectList("scope.findScopes", scope);
    }

    public void updateScope(Scope scope) {
        sqlSession.update("scope.updateScope", scope);
    }

    public List<Scope> findDefaultByScopes(String clientId){
        return sqlSession.selectList("scope.findDefaultByScopes", clientId);
    }
}

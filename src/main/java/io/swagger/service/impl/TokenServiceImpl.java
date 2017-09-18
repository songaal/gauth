package io.swagger.service.impl;

import io.swagger.dao.TokenDao;
import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Token;
import io.swagger.model.User;
import io.swagger.service.TokenService;
import io.swagger.service.UserClientScopeService;
import io.swagger.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.AccessControlException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService{

    private static Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserClientScopeService userClientScopeService;

    @Value("${user.token.timeout}")
    private String tokenTimeout;

    /*
     * 로그인 토큰 생성
     */
    public Token createToken(AuthenticationRequest user) throws Exception {

        // 유저가 클라이언트에 등록 여부를 확인한다.
        if(!userClientScopeService.isUserClientScope(user)){
            logger.debug("user userClientScope empty");
            throw new Exception("user userClientScope empty");
        }

        // 요청시간과 만료시간 생성
        Date requestDate = requestDate();
        int min = tokenTimeout == null ? 60 : Integer.parseInt(tokenTimeout);
        Date expireDate = appendDate(requestDate, min);

        // 토큰 생성
        String userToken = generateToken(user.getUserId(), dateFormat(expireDate));

        // DB 저장
        Token token = new Token();
        token.setUserId(user.getUserId());
        token.setClientId(user.getClientId());
        token.setTokenId(userToken);
        token.setCreateTime(dateFormat(requestDate));
        token.setExpireDate(dateFormat(expireDate));

        tokenDao.insertToekn(token);

        Token registerToken = findByToken(token.getTokenId());

        return registerToken;
    }

    /*
     * 토큰 아이디로 토큰 정보 조회
     */
    public Token findByToken(String tokenId) throws Exception {

        Token registerToken = tokenDao.findByToken(tokenId);
        if(registerToken == null){
            throw new Exception("Token invalid");
        }

        return registerToken;
    }

    /*
     * 토큰 아이디로 토큰 정보 삭제
     */
    public void deleteToekn(String tokenId){
        tokenDao.deleteToken(tokenId);
    }

    /*
     * 토큰 전체 조회
     */
    @Override
    public List<Token> selectToken() {
        return tokenDao.selectTokens();
    }

    /*
     * 토큰 유효성 검사, 발급 클라이언트 도 확인
     */
    @Override
    public Token isTokenValidate(String token, String client) throws Exception, AccessControlException{

        Token registerToken = isTokenValidate(token);

        // 토큰 발급 클라이언트 확인
        String registerClientId = registerToken.getClientId();
        if(!registerClientId.equals(client)){
            throw new AccessControlException("invalid");
        }

        return registerToken;
    }

    /*
     * 토큰 유효성 검사
     */
    @Override
    public Token isTokenValidate(String token) throws Exception, AccessControlException, ParseException{

        // 토큰으로 유저 정보 조회, 없으면 Exception
        isUser(token);

        // DB 저장된 토큰 추가 정보 조회
        Token registerToken = tokenDao.findByToken(token);

        boolean isExpireDate = isExpireDate(registerToken.getExpireDate());
        if(!isExpireDate){
            throw new AccessControlException("invalid");
        }

        return registerToken;
    }

    /*
     * 유저 확인
     */
    private void isUser(String token) throws Exception {
        // 토큰으로 유저 정보 조회
        User registerUser = userService.fienByTokenToUserInfo(token);
        if(registerUser == null){
            logger.debug("Not Found User : {}", token);
            throw new Exception("invalid");
        }
    }

    /*
     * 토큰 만료 여부
     */
    private boolean isExpireDate(String expireDate) throws ParseException {
        // 토큰 만료 시간 확인
        Date expiredDate = new SimpleDateFormat("yyyy-M-d H:m:s").parse(expireDate);

        Date nowDate = Calendar.getInstance().getTime();
        int compare = nowDate.compareTo(expiredDate);

        // 현재보다 미래 시간에 만료 될 경우
        return compare < 0;
    }

    /*
     * 유저로 토큰 정보 조회
     * 유저아이디, 클라이언트아이디 필수
     */
    @Override
    public Token findByUserToToken(AuthenticationRequest authenticationRequest) throws Exception {
        return tokenDao.findByToken(authenticationRequest);
    }

    /*
     * admin check
     */
    @Override
    public void isAdminToken(String authorization) throws AccessControlException {
        // TODO admin 토큰인지 여부 확인
    }

    /*
     * 요청시 날짜와 시간을 리턴
     */
    private Date requestDate(){
        return Calendar.getInstance().getTime();
    }

    /*
     * 기준 시간에서 분 추가
     */
    private Date appendDate(Date date, int timeout){
        Calendar appendCalendar = Calendar.getInstance();
        appendCalendar.setTime(date);
        appendCalendar.add(Calendar.MINUTE, timeout);
        return appendCalendar.getTime();
    }

    /*
     * 날짜 데이터 형식 변환
     */
    private String dateFormat(Date date){
        return new SimpleDateFormat("yyyy-M-d H:m:s").format(date);
    }

    /*
     * 토큰 생성
     */
    private String generateToken(String uid, String date){
        // 유저 아이디와 만료시간으로 암호화된 값 생성
        String tokenSeed = uid + date;
        return DigestUtils.sha256Hex(tokenSeed);
    }

    private boolean isNull(String value){
        return (value == null || "".equals(value));
    }

}

package com.todolist.util.jwt;

import com.todolist.domain.user.model.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct// 빈이 생성된 후, 필요한 초기화 작업을 수행할 때 사용 /  외부 서비스에 연결하거나 초기 설정 값을 읽어오는 등의 작업
    // 또는 빈의 의존성 이 모두 주입된 후에 실행, 의존성 주입에 의존하는 초기화 작업을 안전하게 수행
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }



    //jwt 생성
    public String createToken(String username, UserRole role){
        Date date = new Date();

        return BEARER_PREFIX+
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID) JWT의 subject에 사용자의 식별값 즉, ID를 넣습니다.
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한 JWT에 사용자의 권한 정보를 넣습니다. key-value 형식으로 key 값을 통해 확인할 수 있습니다.
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간 토큰 만료시간을 넣습니다. ms 기준입니다.
                        .setIssuedAt(date) // 발급일 issuedAt에 발급일을 넣습니다.
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘 signWith에 secretKey 값을 담고있는 key와 암호화 알고리즘을 값을 넣어줍니다. 암호화 알고리즘을 사용하여 JWT를 암호화
                        .compact();
    }
    //
    // 생성된 jwt를 쿠키에 저장
    public void addJwtToCookie(String token, HttpServletResponse response){
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

        Cookie cookie = new Cookie(AUTHORIZATION_HEADER,token); // Name-Value
        cookie.setPath("/");

        response.addCookie(cookie); // Response 객체에 Cookie 추가
    }
    // 쿠키에 들어있는 jwt 토큰을 SubString - 순수 토큰 값만 얻기위한 메서드 토큰값 앞에 붙은 접두사 삭제삭제
    public String substringToken(String tokenValue){
        //StringUtils.hasText(tokenValue) 공백인지 null인지 확인가능
        // &&tokenValue.startsWith(BEARER_PREFIX) // 토큰값이  BEARER_PREFIX == Bearer 로 시작하는지 검증
        //StringUtils.hasText를 사용하여 공백, null을 확인하고 startsWith을 사용하여 토큰의 시작값이 Bearer이 맞는지 확인
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)){
            return tokenValue.substring(7);
            //Bearer 이 6자 이고 공백까지 총 7
            //맞다면 순수 JWT를 반환하기 위해 substring을 사용하여 Bearer+"" 을 자르기
        }
        logger.error("Not found token value");
        throw new NullPointerException("Not found token value");
    }

    // jwt 검증
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            //key를 넣고 매개변수를 넣어줘 토큰을 검증함
            //JWT가 위변조되지 않았는지 secretKey(key)값을 넣어 확인
            return true;
        }catch (SecurityException | MalformedJwtException | SignatureException e){
            logger.error("Invalid token, 유효하지 않은 JWT 서명입니다");
        }catch (ExpiredJwtException e){
            logger.error("Unsupported JWT token, 만료된 JWT 토큰입니다");
        }catch (IllegalArgumentException e){
            logger.error("JWT claims is empty, 잘못된 JWT 토큰입니다");
        }
        return false;
    }
    // jwt에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        // 토큰의 페이로드 부분의 토큰에 담긴 정보가 들어있음
        // 여기에 담긴 정보의 한조각을 claim이라 부르고 이는 키-값 쌍으로 이루어짐
        // 토큰에는 여러 클레임을 넣을수 있음
        // 클레임을 가져와서 사용자의 정보를 사용하는 메서드
    }

    public String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();// 여러개 있는 쿠키들을 배열로
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) { // jwt 형식의 쿠키를 순회하면서 찾음
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}

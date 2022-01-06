package com.soul.rat.common.utils;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;


/**
 * @author 朱家兴
 */
public class JwtUtil {


    /**
     * 生成token
     *
     * @param tokenTime tokenTime
     * @param claim     claim
     * @param obj       obj
     * @param subject   subject
     * @return java.lang.String
     * @author zhujx
     * @date 2020/9/15 22:17
     */
    public static String createJwt(Long tokenTime, String claim, Object obj, String subject, String jwtTokenKey) {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body

        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .claim(claim, obj)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, jwtTokenKey);
        if (tokenTime >= 0) {
            long expMillis = nowMillis + tokenTime;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    /**
     * Token的解密
     *
     * @param token       token
     * @param jwtTokenKey jwtTokenKey
     * @param claim       claim
     * @param clazz       clazz
     * @return T
     * @author zhujx
     * @date 2020/9/16 11:38
     */
    public static <T> T parseJwtToken(String token, String jwtTokenKey, String claim, Class<T> clazz) {
        //得到DefaultJwtParser
        try {
            Object object = Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(jwtTokenKey)
                    //设置需要解析的jwt
                    .parseClaimsJws(token).getBody().get(claim);
            return JSONObject.parseObject(JSONObject.toJSONString(object), clazz);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("jwt token timeout");
        }
    }

}

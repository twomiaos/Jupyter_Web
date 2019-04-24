package com.qyk.Jupyter.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    // 待完善写入到配置文件中
    private static final String SECRET = "nL5%73g#";

    public static Map<String, Object> verifyToken(String token) throws Exception {
        Algorithm algorithm;
        Map<String, Claim> map;

        try {
            algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            map = jwt.getClaims();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new Exception("鉴权失败");
        }

        Map<String, Object> resultMap = new HashMap<>(map.size());
        resultMap.put("uid", map.get("uid").asInt());
        resultMap.put("name", map.get("name").asString());
        resultMap.put("roles", map.get("roles").asInt());

        return resultMap;
    }
}

package com.betrybe.agrix.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Classe service do Token.
 */
@Service
public class TokenService {

  @Value("bla")
  private String secret;

  /**
   * Método de geração de um token.
   */
  public String generateToken(Person person) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create()
        .withIssuer("agrix")
        .withSubject(person.getUsername())
        .sign(algorithm);
  }

  /**
   * Método de validação do token.
   */
  public String validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.require(algorithm)
        .withIssuer("agrix")
        .build()
        .verify(token)
        .getSubject();
  }


}

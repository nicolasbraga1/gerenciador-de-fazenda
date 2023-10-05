package com.betrybe.agrix.controllers;

import com.betrybe.agrix.dto.AuthenticationDto;
import com.betrybe.agrix.dto.TokenDto;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import com.betrybe.agrix.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller de autenticação.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final PersonService personService;
  private final TokenService tokenService;

  /**
   * Construtor da classe.
   */
  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager,
      PersonService personService,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.personService = personService;
    this.tokenService = tokenService;
  }

  /**
   * Rota POST /login responsável por realizar o login.
   */
  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody AuthenticationDto authenticationDto) {
    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(authenticationDto.username(),
            authenticationDto.password());
    Authentication authenticate = authenticationManager.authenticate(usernamePassword);
    Person person = (Person) authenticate.getPrincipal();
    String newToken = tokenService.generateToken(person);
    return ResponseEntity.status(HttpStatus.OK).body(new TokenDto(newToken));
  }
}

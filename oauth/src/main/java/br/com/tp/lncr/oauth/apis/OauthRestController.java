package br.com.tp.lncr.oauth.apis;

import br.com.tp.lncr.core.dtos.oauth.OauthCredentialsDTO;
import br.com.tp.lncr.core.dtos.oauth.OauthTokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


public interface OauthRestController {

    ResponseEntity<OauthTokenDTO> token(@RequestHeader("Authorization") String authorizationHeader,
                                        @RequestBody OauthCredentialsDTO body);


}

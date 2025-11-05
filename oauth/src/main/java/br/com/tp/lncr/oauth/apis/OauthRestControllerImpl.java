package br.com.tp.lncr.oauth.apis;

import br.com.tp.lncr.oauth.configs.OauthConfig;
import br.com.tp.lncr.oauth.datasources.integration.OauthCustomerRepositoryImpl;
import br.com.tp.lncr.core.adapters.oauth.OauthControllerImpl;
import br.com.tp.lncr.core.dtos.oauth.OauthCredentialsDTO;
import br.com.tp.lncr.core.dtos.oauth.OauthProfileConfig;
import br.com.tp.lncr.core.dtos.oauth.OauthTokenDTO;
import br.com.tp.lncr.oauth.strategy.OauthProfileStrategyFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class OauthRestControllerImpl implements OauthRestController {

    private final OauthControllerImpl oauthController;
    private final OauthConfig oauthConfig;
    private final OauthCustomerRepositoryImpl oauthCustomerRepository;

    public OauthRestControllerImpl(OauthControllerImpl oauthController, OauthConfig oauthConfig, OauthCustomerRepositoryImpl oauthCustomerRepository) {
        this.oauthController = oauthController;
        this.oauthConfig = oauthConfig;
        this.oauthCustomerRepository = oauthCustomerRepository;
    }

    @Override
    @PostMapping("/token")
    public ResponseEntity<OauthTokenDTO> token(@RequestHeader("Authorization") String authorizationHeader,
                                               @RequestBody OauthCredentialsDTO body) {
        OauthProfileConfig oauthProfileConfig = oauthConfig.getProfileConfig(body.scope());
        OauthTokenDTO oauthTokenDTO = oauthController.createToken(authorizationHeader, body, oauthProfileConfig, new OauthProfileStrategyFactory(oauthCustomerRepository,oauthProfileConfig).getStrategy(body.scope()));
        return ResponseEntity.ok(oauthTokenDTO);
    }
}

package br.com.tp.lncr.oauth.strategy;

import br.com.tp.lncr.core.interfaces.oauth.AbstractOauthProfileStrategy;
import br.com.tp.lncr.core.interfaces.oauth.OauthDatabase;

public class CustomerOauthProfileStrategy extends AbstractOauthProfileStrategy {
    public CustomerOauthProfileStrategy(OauthDatabase oauthDatabase) {
        super(oauthDatabase);
    }
}



package br.com.tp.lncr.oauth.strategy;

import br.com.tp.lncr.core.interfaces.oauth.AbstractOauthProfileStrategy;
import br.com.tp.lncr.core.interfaces.oauth.OauthDatabase;

public class AdminOauthProfileStrategy extends AbstractOauthProfileStrategy {

    public AdminOauthProfileStrategy(OauthDatabase oauthDatabase) {
        super(oauthDatabase);
    }

}


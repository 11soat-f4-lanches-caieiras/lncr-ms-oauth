package br.com.tp.lncr.oauth.strategy;

import br.com.tp.lncr.core.interfaces.oauth.AbstractOauthProfileStrategy;
import br.com.tp.lncr.core.interfaces.oauth.OauthDatabase;

public class KitchenOauthProfileStrategy extends AbstractOauthProfileStrategy {

    public KitchenOauthProfileStrategy(OauthDatabase oauthDatabase) {
        super(oauthDatabase);
    }
}

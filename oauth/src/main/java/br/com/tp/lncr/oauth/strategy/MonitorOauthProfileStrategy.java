package br.com.tp.lncr.oauth.strategy;

import br.com.tp.lncr.core.interfaces.oauth.AbstractOauthProfileStrategy;
import br.com.tp.lncr.core.interfaces.oauth.OauthDatabase;

public class MonitorOauthProfileStrategy extends AbstractOauthProfileStrategy {

    public MonitorOauthProfileStrategy(OauthDatabase oauthDatabase) {
        super(oauthDatabase);
    }
}

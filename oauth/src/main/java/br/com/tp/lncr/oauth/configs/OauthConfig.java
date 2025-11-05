package br.com.tp.lncr.oauth.configs;

import br.com.tp.lncr.oauth.datasources.integration.OauthMapper;
import br.com.tp.lncr.core.adapters.oauth.OauthControllerImpl;
import br.com.tp.lncr.core.dtos.oauth.OauthProfileConfig;
import br.com.tp.lncr.core.dtos.oauth.OauthProfileDTO;
import br.com.tp.lncr.core.exceptions.OauthException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "lncr.oauth")
public class OauthConfig {
    private String oAuthSecretKey;
    private Integer expireIn;
    private Map<String, OauthProfileDTO> profiles;


    public String getoAuthSecretKey() {
        return oAuthSecretKey;
    }

    public void setoAuthSecretKey(String oAuthSecretKey) {
        this.oAuthSecretKey = oAuthSecretKey;
    }

    public Integer getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
    }

    public Map<String, OauthProfileDTO> getProfiles() {
        return profiles;
    }

    public void setProfiles(Map<String, OauthProfileDTO> profiles) {
        this.profiles = profiles;
    }

    public List<String> getAllScopes() {
        return profiles != null ? profiles.values().stream().map(OauthProfileDTO::getScope).toList() : List.of();
    }

    public List<String> getAllGrantTypes() {
        return profiles != null ? profiles.values().stream().map(OauthProfileDTO::getGrantType).toList() : List.of();
    }

    public OauthProfileConfig getProfileConfig(String profileKey) {
        if (profiles != null && profiles.containsKey(profileKey)) {
            return new OauthProfileConfig(expireIn ,profiles.get(profileKey),getoAuthSecretKey());
        }
        throw new OauthException("Scope inválido: " + profileKey, 400);
    }

    @Bean
    public OauthControllerImpl oauthControllerImpl(){
        return new OauthControllerImpl();
    }

    @Bean
    public OauthMapper jpaOauthMapper(){
        return new OauthMapper();
    }

}

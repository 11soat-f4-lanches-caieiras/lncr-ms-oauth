package br.com.tp.lncr.oauth.datasources.integration;

import br.com.tp.lncr.core.dtos.customer.CustomerDTO;
import br.com.tp.lncr.core.dtos.oauth.OauthCredentialsDTO;

public class OauthMapper {

    public OauthMapper() {
    }

    OauthCredentialsDTO customerToDtoToOauthCredentialsDTO(CustomerDTO customerDTO){
        if(customerDTO == null) return null;
        return new OauthCredentialsDTO(
                customerDTO.getDocumentNumber(),
                customerDTO.getEmail(),
            null,
            null,
                customerDTO.getName(),
                customerDTO.getId()
        );
    }
}

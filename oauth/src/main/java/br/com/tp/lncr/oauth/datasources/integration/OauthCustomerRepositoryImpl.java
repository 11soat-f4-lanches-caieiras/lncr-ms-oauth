package br.com.tp.lncr.oauth.datasources.integration;

import br.com.tp.lncr.commons.integrations.customer.CustomerIntegrationImpl;
import br.com.tp.lncr.core.dtos.oauth.OauthCredentialsDTO;
import br.com.tp.lncr.core.interfaces.oauth.OauthDatabase;
import org.springframework.stereotype.Repository;

@Repository
public class OauthCustomerRepositoryImpl implements OauthDatabase {

    private final CustomerIntegrationImpl customerIntegration;
    private final OauthMapper oauthMapper;

    public OauthCustomerRepositoryImpl(CustomerIntegrationImpl customerIntegration, OauthMapper oauthMapper) {
        this.customerIntegration = customerIntegration;
        this.oauthMapper = oauthMapper;
    }

    @Override
    public OauthCredentialsDTO validateCredentials(OauthCredentialsDTO oauthCredentialsDTO) {
        return oauthMapper.customerToDtoToOauthCredentialsDTO(this.customerIntegration.getCustomerDetailsByDocument(oauthCredentialsDTO.client_id()));
    }

}

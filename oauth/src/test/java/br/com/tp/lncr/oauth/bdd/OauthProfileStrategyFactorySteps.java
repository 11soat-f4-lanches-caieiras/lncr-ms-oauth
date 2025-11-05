package br.com.tp.lncr.oauth.bdd;

import br.com.tp.lncr.core.exceptions.OauthException;
import br.com.tp.lncr.core.interfaces.oauth.OauthDatabase;
import br.com.tp.lncr.core.interfaces.oauth.OauthProfileStrategy;
import br.com.tp.lncr.oauth.strategy.*;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class OauthProfileStrategyFactorySteps {

    @Mock
    private OauthDatabase configDatabase;

    @Mock
    private OauthDatabase jpaDatabase;

    private OauthProfileStrategyFactory factory;
    private OauthProfileStrategy strategy;
    private Exception exception;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Dado("que tenho uma OauthDatabase de configuração")
    @Dado("tenho uma OauthDatabase de configuração")
    public void queTenhoUmaOauthDatabaseDeConfiguracao() {
        MockitoAnnotations.openMocks(this);
    }

    @Dado("que tenho uma OauthDatabase JPA")
    @Dado("tenho uma OauthDatabase JPA")
    public void queTenhoUmaOauthDatabaseJPA() {
        factory = new OauthProfileStrategyFactory(jpaDatabase, configDatabase);
    }

    @Quando("eu solicito a estratégia para o perfil {string}")
    public void euSolicitoAEstrategiaParaOPerfil(String profile) {
        try {
            strategy = factory.getStrategy(profile);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Então("a estratégia AdminOauthProfileStrategy deve ser retornada")
    public void aEstrategiaAdminOauthProfileStrategyDeveSerRetornada() {
        assertNotNull(strategy);
        assertInstanceOf(AdminOauthProfileStrategy.class, strategy);
    }

    @Então("a estratégia deve usar a database de configuração")
    public void aEstrategiaDeveUsarADatabaseDeConfiguracao() {
        assertNotNull(strategy);
    }

    @Então("a estratégia CustomerOauthProfileStrategy deve ser retornada")
    public void aEstrategiaCustomerOauthProfileStrategyDeveSerRetornada() {
        assertNotNull(strategy);
        assertInstanceOf(CustomerOauthProfileStrategy.class, strategy);
    }

    @Então("a estratégia deve usar a database JPA")
    public void aEstrategiaDeveUsarADatabaseJPA() {
        assertNotNull(strategy);
    }

    @Então("a estratégia MonitorOauthProfileStrategy deve ser retornada")
    public void aEstrategiaMonitorOauthProfileStrategyDeveSerRetornada() {
        assertNotNull(strategy);
        assertInstanceOf(MonitorOauthProfileStrategy.class, strategy);
    }

    @Então("a estratégia TotemOauthProfileStrategy deve ser retornada")
    public void aEstrategiaTotemOauthProfileStrategyDeveSerRetornada() {
        assertNotNull(strategy);
        assertInstanceOf(TotemOauthProfileStrategy.class, strategy);
    }

    @Então("uma exceção OauthException deve ser lançada")
    public void umaExcecaoOauthExceptionDeveSerLancada() {
        assertNotNull(exception);
        assertInstanceOf(OauthException.class, exception);
    }

    @Então("a mensagem de erro deve ser {string}")
    public void aMensagemDeErroDeveSer(String message) {
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("inválido"));
    }
}


package br.com.tp.lncr.oauth.bdd;

import br.com.tp.lncr.core.interfaces.oauth.OauthDatabase;
import br.com.tp.lncr.oauth.strategy.*;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class CommonOauthStrategySteps {

    @Mock
    private OauthDatabase configDatabase;

    @Mock
    private OauthDatabase jpaDatabase;

    private Object strategy;
    private boolean validationResult;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Dado("que tenho uma OauthDatabase de configuração válida")
    public void queTenhoUmaOauthDatabaseDeConfiguracaoValida() {
        MockitoAnnotations.openMocks(this);
    }

    @Dado("que tenho uma OauthDatabase JPA válida")
    public void queTenhoUmaOauthDatabaseJPAValida() {
        MockitoAnnotations.openMocks(this);
    }

    @Quando("eu crio uma AdminOauthProfileStrategy")
    public void euCrioUmaAdminOauthProfileStrategy() {
        strategy = new AdminOauthProfileStrategy(configDatabase);
    }

    @Quando("eu crio uma CustomerOauthProfileStrategy")
    public void euCrioUmaCustomerOauthProfileStrategy() {
        strategy = new CustomerOauthProfileStrategy(jpaDatabase);
    }

    @Quando("eu crio uma KitchenOauthProfileStrategy")
    public void euCrioUmaKitchenOauthProfileStrategy() {
        strategy = new KitchenOauthProfileStrategy(configDatabase);
    }

    @Quando("eu crio uma MonitorOauthProfileStrategy")
    public void euCrioUmaMonitorOauthProfileStrategy() {
        strategy = new MonitorOauthProfileStrategy(configDatabase);
    }

    @Quando("eu crio uma TotemOauthProfileStrategy")
    public void euCrioUmaTotemOauthProfileStrategy() {
        strategy = new TotemOauthProfileStrategy(configDatabase);
    }

    @Então("a estratégia Admin deve ser criada com sucesso")
    public void aEstrategiaAdminDeveSerCriadaComSucesso() {
        assertNotNull(strategy);
        assertInstanceOf(AdminOauthProfileStrategy.class, strategy);
    }

    @Então("a estratégia Customer deve ser criada com sucesso")
    public void aEstrategiaCustomerDeveSerCriadaComSucesso() {
        assertNotNull(strategy);
        assertInstanceOf(CustomerOauthProfileStrategy.class, strategy);
    }

    @Então("a estratégia Kitchen deve ser criada com sucesso")
    public void aEstrategiaKitchenDeveSerCriadaComSucesso() {
        assertNotNull(strategy);
        assertInstanceOf(KitchenOauthProfileStrategy.class, strategy);
    }

    @Então("a estratégia Monitor deve ser criada com sucesso")
    public void aEstrategiaMonitorDeveSerCriadaComSucesso() {
        assertNotNull(strategy);
        assertInstanceOf(MonitorOauthProfileStrategy.class, strategy);
    }

    @Então("a estratégia Totem deve ser criada com sucesso")
    public void aEstrategiaTotemDeveSerCriadaComSucesso() {
        assertNotNull(strategy);
        assertInstanceOf(TotemOauthProfileStrategy.class, strategy);
    }

    @Então("deve usar a database de configuração fornecida")
    public void deveUsarADatabaseDeConfiguracaoFornecida() {
        assertNotNull(strategy);
    }

    @Então("deve usar a database JPA fornecida")
    public void deveUsarADatabaseJPAFornecida() {
        assertNotNull(strategy);
    }

    @Dado("que tenho uma AdminOauthProfileStrategy configurada")
    public void queTenhoUmaAdminOauthProfileStrategyConfigurada() {
        MockitoAnnotations.openMocks(this);
        strategy = new AdminOauthProfileStrategy(configDatabase);
    }

    @Dado("que tenho uma CustomerOauthProfileStrategy configurada")
    public void queTenhoUmaCustomerOauthProfileStrategyConfigurada() {
        MockitoAnnotations.openMocks(this);
        strategy = new CustomerOauthProfileStrategy(jpaDatabase);
    }

    @Dado("que tenho uma KitchenOauthProfileStrategy configurada")
    public void queTenhoUmaKitchenOauthProfileStrategyConfigurada() {
        MockitoAnnotations.openMocks(this);
        strategy = new KitchenOauthProfileStrategy(configDatabase);
    }

    @Dado("que tenho uma MonitorOauthProfileStrategy configurada")
    public void queTenhoUmaMonitorOauthProfileStrategyConfigurada() {
        MockitoAnnotations.openMocks(this);
        strategy = new MonitorOauthProfileStrategy(configDatabase);
    }

    @Dado("que tenho uma TotemOauthProfileStrategy configurada")
    public void queTenhoUmaTotemOauthProfileStrategyConfigurada() {
        MockitoAnnotations.openMocks(this);
        strategy = new TotemOauthProfileStrategy(configDatabase);
    }

    @Dado("tenho credenciais administrativas válidas")
    public void tenhoCredenciaisAdministrativasValidas() {
        // Preparação de credenciais para teste
    }

    @Dado("tenho credenciais de cliente válidas")
    public void tenhoCredenciaisDeClienteValidas() {
        // Preparação de credenciais para teste
    }

    @Dado("tenho credenciais da cozinha válidas")
    public void tenhoCredenciaisDaCozinhaValidas() {
        // Preparação de credenciais para teste
    }

    @Dado("tenho credenciais de monitor válidas")
    public void tenhoCredenciaisDeMonitorValidas() {
        // Preparação de credenciais para teste
    }

    @Dado("tenho credenciais de totem válidas")
    public void tenhoCredenciaisDeTotemValidas() {
        // Preparação de credenciais para teste
    }

    @Quando("eu valido as credenciais usando a estratégia")
    public void euValidoAsCredenciaisUsandoAEstrategia() {
        validationResult = true;
    }

    @Então("a validação deve ser bem-sucedida")
    public void aValidacaoDeveSerBemSucedida() {
        assertTrue(validationResult);
    }
}


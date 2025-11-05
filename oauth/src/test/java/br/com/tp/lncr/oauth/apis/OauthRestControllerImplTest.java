package br.com.tp.lncr.oauth.apis;

import br.com.tp.lncr.oauth.configs.OauthConfig;
import br.com.tp.lncr.oauth.datasources.integration.OauthCustomerRepositoryImpl;
import br.com.tp.lncr.core.adapters.oauth.OauthControllerImpl;
import br.com.tp.lncr.core.dtos.oauth.OauthCredentialsDTO;
import br.com.tp.lncr.core.dtos.oauth.OauthProfileConfig;
import br.com.tp.lncr.core.dtos.oauth.OauthProfileDTO;
import br.com.tp.lncr.core.dtos.oauth.OauthTokenDTO;
import br.com.tp.lncr.core.exceptions.OauthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OauthRestControllerImplTest {

    @Mock
    private OauthControllerImpl oauthController;

    @Mock
    private OauthConfig oauthConfig;

    @Mock
    private OauthCustomerRepositoryImpl oauthCustomerRepository;

    @InjectMocks
    private OauthRestControllerImpl oauthRestController;

    private OauthCredentialsDTO oauthCredentialsDTO;
    private OauthTokenDTO oauthTokenDTO;
    private OauthProfileConfig oauthProfileConfig;
    private OauthProfileDTO oauthProfileDTO;
    private String authorizationHeader;

    @BeforeEach
    void setUp() {
        authorizationHeader = "Basic dGVzdDp0ZXN0";

        oauthCredentialsDTO = new OauthCredentialsDTO(
                "test-client-id",
                "test-client-secret",
                "client_credentials",
                "admin",
                null,
                null
        );

        oauthTokenDTO = new OauthTokenDTO(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test",
                "Bearer",
                3600
        );

        oauthProfileDTO = new OauthProfileDTO();
        oauthProfileDTO.setClientId("test-client-id");
        oauthProfileDTO.setClientSecret("test-client-secret");
        oauthProfileDTO.setGrantType("client_credentials");
        oauthProfileDTO.setScope("admin");

        oauthProfileConfig = new OauthProfileConfig(3600, oauthProfileDTO, "test-secret-key");
    }

    @Test
    void deveRetornarTokenComSucessoParaPerfilAdmin() {
        when(oauthConfig.getProfileConfig("admin")).thenReturn(oauthProfileConfig);
        when(oauthController.createToken(eq(authorizationHeader), eq(oauthCredentialsDTO),
                eq(oauthProfileConfig), any())).thenReturn(oauthTokenDTO);

        ResponseEntity<OauthTokenDTO> response = oauthRestController.token(authorizationHeader, oauthCredentialsDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Bearer", response.getBody().tokenType());
        assertEquals(3600, response.getBody().expiresIn());
        assertNotNull(response.getBody().accessToken());
        verify(oauthConfig).getProfileConfig("admin");
        verify(oauthController).createToken(eq(authorizationHeader), eq(oauthCredentialsDTO),
                eq(oauthProfileConfig), any());
    }

    @Test
    void deveRetornarTokenComSucessoParaPerfilCustomer() {
        OauthCredentialsDTO customerCredentials = new OauthCredentialsDTO(
                "customer-client-id",
                "customer-client-secret",
                "client_credentials",
                "customer",
                "João Silva",
                1
        );

        OauthProfileDTO customerProfileDTO = new OauthProfileDTO();
        customerProfileDTO.setClientId("customer-client-id");
        customerProfileDTO.setClientSecret("customer-client-secret");
        customerProfileDTO.setGrantType("client_credentials");
        customerProfileDTO.setScope("customer");

        OauthProfileConfig customerProfileConfig = new OauthProfileConfig(3600, customerProfileDTO, "test-secret-key");

        when(oauthConfig.getProfileConfig("customer")).thenReturn(customerProfileConfig);
        when(oauthController.createToken(eq(authorizationHeader), eq(customerCredentials),
                eq(customerProfileConfig), any())).thenReturn(oauthTokenDTO);

        ResponseEntity<OauthTokenDTO> response = oauthRestController.token(authorizationHeader, customerCredentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(oauthTokenDTO, response.getBody());
        verify(oauthConfig).getProfileConfig("customer");
        verify(oauthController).createToken(eq(authorizationHeader), eq(customerCredentials),
                eq(customerProfileConfig), any());
    }

    @Test
    void deveRetornarTokenComSucessoParaPerfilMonitor() {
        OauthCredentialsDTO monitorCredentials = new OauthCredentialsDTO(
                "monitor-client-id",
                "monitor-client-secret",
                "client_credentials",
                "monitor",
                null,
                null
        );

        OauthProfileDTO monitorProfileDTO = new OauthProfileDTO();
        monitorProfileDTO.setClientId("monitor-client-id");
        monitorProfileDTO.setClientSecret("monitor-client-secret");
        monitorProfileDTO.setGrantType("client_credentials");
        monitorProfileDTO.setScope("monitor");

        OauthProfileConfig monitorProfileConfig = new OauthProfileConfig(3600, monitorProfileDTO, "test-secret-key");

        when(oauthConfig.getProfileConfig("monitor")).thenReturn(monitorProfileConfig);
        when(oauthController.createToken(eq(authorizationHeader), eq(monitorCredentials),
                eq(monitorProfileConfig), any())).thenReturn(oauthTokenDTO);

        ResponseEntity<OauthTokenDTO> response = oauthRestController.token(authorizationHeader, monitorCredentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(oauthConfig).getProfileConfig("monitor");
        verify(oauthController).createToken(eq(authorizationHeader), eq(monitorCredentials),
                eq(monitorProfileConfig), any());
    }

    @Test
    void deveRetornarTokenComSucessoParaPerfilTotem() {
        OauthCredentialsDTO totemCredentials = new OauthCredentialsDTO(
                "totem-client-id",
                "totem-client-secret",
                "client_credentials",
                "totem",
                null,
                null
        );

        OauthProfileDTO totemProfileDTO = new OauthProfileDTO();
        totemProfileDTO.setClientId("totem-client-id");
        totemProfileDTO.setClientSecret("totem-client-secret");
        totemProfileDTO.setGrantType("client_credentials");
        totemProfileDTO.setScope("totem");

        OauthProfileConfig totemProfileConfig = new OauthProfileConfig(3600, totemProfileDTO, "test-secret-key");

        when(oauthConfig.getProfileConfig("totem")).thenReturn(totemProfileConfig);
        when(oauthController.createToken(eq(authorizationHeader), eq(totemCredentials),
                eq(totemProfileConfig), any())).thenReturn(oauthTokenDTO);

        ResponseEntity<OauthTokenDTO> response = oauthRestController.token(authorizationHeader, totemCredentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(oauthConfig).getProfileConfig("totem");
        verify(oauthController).createToken(eq(authorizationHeader), eq(totemCredentials),
                eq(totemProfileConfig), any());
    }

    @Test
    void deveLancarExcecaoQuandoPerfilNaoExistir() {
        OauthCredentialsDTO invalidCredentials = new OauthCredentialsDTO(
                "invalid-client-id",
                "invalid-client-secret",
                "client_credentials",
                "invalid",
                null,
                null
        );

        when(oauthConfig.getProfileConfig("invalid")).thenThrow(new OauthException("Perfil inválido", 400));

        assertThrows(OauthException.class, () -> oauthRestController.token(authorizationHeader, invalidCredentials));

        verify(oauthConfig).getProfileConfig("invalid");
        verify(oauthController, never()).createToken(any(), any(), any(), any());
    }

    @Test
    void deveLancarExcecaoQuandoAuthorizationHeaderInvalido() {
        String invalidHeader = "Invalid Header";

        when(oauthConfig.getProfileConfig("admin")).thenReturn(oauthProfileConfig);
        when(oauthController.createToken(eq(invalidHeader), eq(oauthCredentialsDTO),
                eq(oauthProfileConfig), any())).thenThrow(new OauthException("Authorization header inválido", 401));

        assertThrows(OauthException.class, () -> oauthRestController.token(invalidHeader, oauthCredentialsDTO));

        verify(oauthConfig).getProfileConfig("admin");
    }

    @Test
    void deveLancarExcecaoQuandoCredenciaisInvalidas() {
        OauthCredentialsDTO invalidCredentials = new OauthCredentialsDTO(
                "wrong-client-id",
                "wrong-client-secret",
                "client_credentials",
                "admin",
                null,
                null
        );

        when(oauthConfig.getProfileConfig("admin")).thenReturn(oauthProfileConfig);
        when(oauthController.createToken(eq(authorizationHeader), eq(invalidCredentials),
                eq(oauthProfileConfig), any())).thenThrow(new OauthException("Credenciais inválidas", 401));

        assertThrows(OauthException.class, () -> oauthRestController.token(authorizationHeader, invalidCredentials));

        verify(oauthConfig).getProfileConfig("admin");
        verify(oauthController).createToken(eq(authorizationHeader), eq(invalidCredentials),
                eq(oauthProfileConfig), any());
    }

    @Test
    void deveRetornarTokenComExpiracaoCorreta() {
        when(oauthConfig.getProfileConfig("admin")).thenReturn(oauthProfileConfig);
        when(oauthController.createToken(eq(authorizationHeader), eq(oauthCredentialsDTO),
                eq(oauthProfileConfig), any())).thenReturn(oauthTokenDTO);

        ResponseEntity<OauthTokenDTO> response = oauthRestController.token(authorizationHeader, oauthCredentialsDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3600, response.getBody().expiresIn());
    }

    @Test
    void deveRetornarTokenComTipoBearer() {
        when(oauthConfig.getProfileConfig("admin")).thenReturn(oauthProfileConfig);
        when(oauthController.createToken(eq(authorizationHeader), eq(oauthCredentialsDTO),
                eq(oauthProfileConfig), any())).thenReturn(oauthTokenDTO);

        ResponseEntity<OauthTokenDTO> response = oauthRestController.token(authorizationHeader, oauthCredentialsDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Bearer", response.getBody().tokenType());
    }
}

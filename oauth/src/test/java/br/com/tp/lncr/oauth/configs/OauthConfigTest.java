package br.com.tp.lncr.oauth.configs;

import br.com.tp.lncr.core.adapters.oauth.OauthControllerImpl;
import br.com.tp.lncr.core.dtos.oauth.OauthProfileConfig;
import br.com.tp.lncr.core.dtos.oauth.OauthProfileDTO;
import br.com.tp.lncr.core.exceptions.OauthException;
import br.com.tp.lncr.oauth.datasources.integration.OauthMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OauthConfig - Testes Unitários")
class OauthConfigTest {

    private OauthConfig oauthConfig;

    @BeforeEach
    void setUp() {
        oauthConfig = new OauthConfig();
        Map<String, OauthProfileDTO> profiles;

        // Setup default values
        oauthConfig.setoAuthSecretKey("test-secret-key");
        oauthConfig.setExpireIn(3600);

        // Setup profiles
        profiles = new HashMap<>();

        OauthProfileDTO adminProfile = new OauthProfileDTO();
        adminProfile.setClientId("admin-client-id");
        adminProfile.setClientSecret("admin-secret");
        adminProfile.setScope("admin");
        adminProfile.setGrantType("client_credentials");
        profiles.put("admin", adminProfile);

        OauthProfileDTO customerProfile = new OauthProfileDTO();
        customerProfile.setClientId("customer-client-id");
        customerProfile.setClientSecret("customer-secret");
        customerProfile.setScope("customer");
        customerProfile.setGrantType("password");
        profiles.put("customer", customerProfile);

        oauthConfig.setProfiles(profiles);
    }

    @Test
    @DisplayName("Deve retornar a chave secreta OAuth configurada")
    void deveRetornarChaveSecretaOAuth() {
        String secretKey = oauthConfig.getoAuthSecretKey();

        assertNotNull(secretKey);
        assertEquals("test-secret-key", secretKey);
    }

    @Test
    @DisplayName("Deve configurar a chave secreta OAuth")
    void deveConfigurarChaveSecretaOAuth() {
        String newSecretKey = "new-secret-key";

        oauthConfig.setoAuthSecretKey(newSecretKey);

        assertEquals(newSecretKey, oauthConfig.getoAuthSecretKey());
    }

    @Test
    @DisplayName("Deve retornar o tempo de expiração configurado")
    void deveRetornarTempoExpiracao() {
        Integer expireIn = oauthConfig.getExpireIn();

        assertNotNull(expireIn);
        assertEquals(3600, expireIn);
    }

    @Test
    @DisplayName("Deve configurar o tempo de expiração")
    void deveConfigurarTempoExpiracao() {
        Integer newExpireIn = 7200;

        oauthConfig.setExpireIn(newExpireIn);

        assertEquals(newExpireIn, oauthConfig.getExpireIn());
    }

    @Test
    @DisplayName("Deve retornar os perfis configurados")
    void deveRetornarPerfisConfigurados() {
        Map<String, OauthProfileDTO> returnedProfiles = oauthConfig.getProfiles();

        assertNotNull(returnedProfiles);
        assertEquals(2, returnedProfiles.size());
        assertTrue(returnedProfiles.containsKey("admin"));
        assertTrue(returnedProfiles.containsKey("customer"));
    }

    @Test
    @DisplayName("Deve configurar os perfis")
    void deveConfigurarPerfis() {
        Map<String, OauthProfileDTO> newProfiles = new HashMap<>();
        OauthProfileDTO testProfile = new OauthProfileDTO();
        testProfile.setClientId("test-id");
        testProfile.setScope("test");
        newProfiles.put("test", testProfile);

        oauthConfig.setProfiles(newProfiles);

        assertEquals(1, oauthConfig.getProfiles().size());
        assertTrue(oauthConfig.getProfiles().containsKey("test"));
    }

    @Test
    @DisplayName("Deve retornar todos os scopes dos perfis")
    void deveRetornarTodosOsScopes() {
        List<String> scopes = oauthConfig.getAllScopes();

        assertNotNull(scopes);
        assertEquals(2, scopes.size());
        assertTrue(scopes.contains("admin"));
        assertTrue(scopes.contains("customer"));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há perfis configurados")
    void deveRetornarListaVaziaQuandoNaoHaPerfis() {
        oauthConfig.setProfiles(null);

        List<String> scopes = oauthConfig.getAllScopes();

        assertNotNull(scopes);
        assertTrue(scopes.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar todos os grant types dos perfis")
    void deveRetornarTodosOsGrantTypes() {
        List<String> grantTypes = oauthConfig.getAllGrantTypes();

        assertNotNull(grantTypes);
        assertEquals(2, grantTypes.size());
        assertTrue(grantTypes.contains("client_credentials"));
        assertTrue(grantTypes.contains("password"));
    }

    @Test
    @DisplayName("Deve retornar lista vazia de grant types quando não há perfis")
    void deveRetornarListaVaziaGrantTypesQuandoNaoHaPerfis() {
        oauthConfig.setProfiles(null);

        List<String> grantTypes = oauthConfig.getAllGrantTypes();

        assertNotNull(grantTypes);
        assertTrue(grantTypes.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar configuração de perfil válido - admin")
    void deveRetornarConfiguracaoPerfilValidoAdmin() {
        OauthProfileConfig profileConfig = oauthConfig.getProfileConfig("admin");

        assertNotNull(profileConfig);
        assertEquals(3600, profileConfig.getExpireIn());
        assertEquals("test-secret-key", profileConfig.getSecretKey());
        assertNotNull(profileConfig.getOauthProfile());
        assertEquals("admin-client-id", profileConfig.getOauthProfile().getClientId());
        assertEquals("admin", profileConfig.getOauthProfile().getScope());
    }

    @Test
    @DisplayName("Deve retornar configuração de perfil válido - customer")
    void deveRetornarConfiguracaoPerfilValidoCustomer() {
        OauthProfileConfig profileConfig = oauthConfig.getProfileConfig("customer");

        assertNotNull(profileConfig);
        assertEquals(3600, profileConfig.getExpireIn());
        assertEquals("test-secret-key", profileConfig.getSecretKey());
        assertNotNull(profileConfig.getOauthProfile());
        assertEquals("customer-client-id", profileConfig.getOauthProfile().getClientId());
        assertEquals("customer", profileConfig.getOauthProfile().getScope());
    }

    @Test
    @DisplayName("Deve lançar OauthException quando perfil não existe")
    void deveLancarExcecaoQuandoPerfilNaoExiste() {
        OauthException exception = assertThrows(OauthException.class,
            () -> oauthConfig.getProfileConfig("invalid-profile"));

        assertEquals("Scope inválido: invalid-profile", exception.getMessage());
        assertEquals(400, exception.getCode());
    }

    @Test
    @DisplayName("Deve lançar OauthException quando profiles é null")
    void deveLancarExcecaoQuandoProfilesNull() {
        oauthConfig.setProfiles(null);

        OauthException exception = assertThrows(OauthException.class,
            () -> oauthConfig.getProfileConfig("admin"));

        assertEquals("Scope inválido: admin", exception.getMessage());
        assertEquals(400, exception.getCode());
    }

    @Test
    @DisplayName("Deve criar bean OauthControllerImpl")
    void deveCriarBeanOauthControllerImpl() {
        OauthControllerImpl controller = oauthConfig.oauthControllerImpl();

        assertNotNull(controller);
        assertInstanceOf(OauthControllerImpl.class, controller);
    }

    @Test
    @DisplayName("Deve criar bean OauthMapper")
    void deveCriarBeanOauthMapper() {
        OauthMapper mapper = oauthConfig.jpaOauthMapper();

        assertNotNull(mapper);
        assertInstanceOf(OauthMapper.class, mapper);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando profiles é um mapa vazio")
    void deveRetornarListaVaziaQuandoProfilesMapaVazio() {
        oauthConfig.setProfiles(new HashMap<>());

        List<String> scopes = oauthConfig.getAllScopes();
        List<String> grantTypes = oauthConfig.getAllGrantTypes();

        assertNotNull(scopes);
        assertNotNull(grantTypes);
        assertTrue(scopes.isEmpty());
        assertTrue(grantTypes.isEmpty());
    }

    @Test
    @DisplayName("Deve manter integridade dos dados ao modificar chave secreta")
    void deveManterIntegridadeAoModificarChaveSecreta() {
        Integer originalExpireIn = oauthConfig.getExpireIn();

        oauthConfig.setoAuthSecretKey("modified-key");

        assertEquals("modified-key", oauthConfig.getoAuthSecretKey());
        assertEquals(originalExpireIn, oauthConfig.getExpireIn());
        assertNotNull(oauthConfig.getProfiles());
    }

    @Test
    @DisplayName("Deve retornar profile config com valores corretos para múltiplos perfis")
    void deveRetornarProfileConfigComValoresCorretosParaMultiplosPerfis() {
        OauthProfileConfig adminConfig = oauthConfig.getProfileConfig("admin");
        OauthProfileConfig customerConfig = oauthConfig.getProfileConfig("customer");

        assertNotEquals(adminConfig.getOauthProfile().getClientId(),
                       customerConfig.getOauthProfile().getClientId());
        assertNotEquals(adminConfig.getOauthProfile().getScope(),
                       customerConfig.getOauthProfile().getScope());
        assertEquals(adminConfig.getExpireIn(), customerConfig.getExpireIn());
        assertEquals(adminConfig.getSecretKey(), customerConfig.getSecretKey());
    }
}


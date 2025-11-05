package br.com.tp.lncr.oauth.datasources.integration;

import br.com.tp.lncr.commons.integrations.customer.CustomerIntegrationImpl;
import br.com.tp.lncr.core.dtos.customer.CustomerDTO;
import br.com.tp.lncr.core.dtos.oauth.OauthCredentialsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OauthCustomerRepositoryImpl - Testes Unitários")
class OauthCustomerRepositoryImplTest {

    @Mock
    private CustomerIntegrationImpl customerIntegration;

    @Mock
    private OauthMapper oauthMapper;

    @InjectMocks
    private OauthCustomerRepositoryImpl oauthCustomerRepository;

    private OauthCredentialsDTO inputCredentials;
    private CustomerDTO customerDTO;
    private OauthCredentialsDTO expectedCredentials;

    @BeforeEach
    void setUp() {
        inputCredentials = new OauthCredentialsDTO(
                "client-id",
                "client-secret",
                "password",
                "customer",
                "João Silva",
                1
        );

        customerDTO = new CustomerDTO(
                1,
                "12345678900",
                "João Silva",
                "joao.silva@email.com"
        );

        expectedCredentials = new OauthCredentialsDTO(
                "12345678900",
                "joao.silva@email.com",
                null,
                null,
                "João Silva",
                1
        );
    }

    @Test
    @DisplayName("Deve validar credenciais com sucesso quando customer existe")
    void deveValidarCredenciaisComSucessoQuandoCustomerExiste() {
        when(customerIntegration.getCustomerDetails(1)).thenReturn(customerDTO);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO)).thenReturn(expectedCredentials);

        OauthCredentialsDTO result = oauthCustomerRepository.validateCredentials(inputCredentials);

        assertNotNull(result);
        assertEquals("12345678900", result.client_id());
        assertEquals("joao.silva@email.com", result.client_secret());
        assertEquals("João Silva", result.name());
        assertEquals(1, result.customerId());
        assertNull(result.grant_type());
        assertNull(result.scope());

        verify(customerIntegration, times(1)).getCustomerDetails(1);
        verify(oauthMapper, times(1)).customerToDtoToOauthCredentialsDTO(customerDTO);
    }

    @Test
    @DisplayName("Deve retornar null quando customer não é encontrado")
    void deveRetornarNullQuandoCustomerNaoEncontrado() {
        when(customerIntegration.getCustomerDetails(999)).thenReturn(null);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(null)).thenReturn(null);

        OauthCredentialsDTO inputWithInvalidId = new OauthCredentialsDTO(
                "client-id",
                "client-secret",
                "password",
                "customer",
                null,
                999
        );

        OauthCredentialsDTO result = oauthCustomerRepository.validateCredentials(inputWithInvalidId);

        assertNull(result);
        verify(customerIntegration, times(1)).getCustomerDetails(999);
        verify(oauthMapper, times(1)).customerToDtoToOauthCredentialsDTO(null);
    }

    @Test
    @DisplayName("Deve validar credenciais para diferentes customer IDs")
    void deveValidarCredenciaisParaDiferentesCustomerIds() {
        CustomerDTO customer2 = new CustomerDTO(
                2,
                "98765432100",
                "Maria Santos",
                "maria.santos@email.com"
        );

        OauthCredentialsDTO credentials2 = new OauthCredentialsDTO(
                "98765432100",
                "maria.santos@email.com",
                null,
                null,
                "Maria Santos",
                2
        );

        when(customerIntegration.getCustomerDetails(2)).thenReturn(customer2);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(customer2)).thenReturn(credentials2);

        OauthCredentialsDTO inputCredentials2 = new OauthCredentialsDTO(
                "client-id",
                "client-secret",
                "password",
                "customer",
                "Maria Santos",
                2
        );

        OauthCredentialsDTO result = oauthCustomerRepository.validateCredentials(inputCredentials2);

        assertNotNull(result);
        assertEquals(2, result.customerId());
        assertEquals("98765432100", result.client_id());
        assertEquals("Maria Santos", result.name());

        verify(customerIntegration, times(1)).getCustomerDetails(2);
        verify(oauthMapper, times(1)).customerToDtoToOauthCredentialsDTO(customer2);
    }

    @Test
    @DisplayName("Deve chamar customerIntegration com o customerId correto")
    void deveChamarCustomerIntegrationComCustomerIdCorreto() {
        when(customerIntegration.getCustomerDetails(1)).thenReturn(customerDTO);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO)).thenReturn(expectedCredentials);

        oauthCustomerRepository.validateCredentials(inputCredentials);

        verify(customerIntegration).getCustomerDetails(1);
        verifyNoMoreInteractions(customerIntegration);
    }

    @Test
    @DisplayName("Deve chamar oauthMapper com o CustomerDTO correto")
    void deveChamarOauthMapperComCustomerDTOCorreto() {
        when(customerIntegration.getCustomerDetails(1)).thenReturn(customerDTO);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO)).thenReturn(expectedCredentials);

        oauthCustomerRepository.validateCredentials(inputCredentials);

        verify(oauthMapper).customerToDtoToOauthCredentialsDTO(customerDTO);
        verifyNoMoreInteractions(oauthMapper);
    }

    @Test
    @DisplayName("Deve validar credenciais com customerId zero")
    void deveValidarCredenciaisComCustomerIdZero() {
        CustomerDTO customer0 = new CustomerDTO(0, "000", "Test User", "test@email.com");
        OauthCredentialsDTO credentials0 = new OauthCredentialsDTO("000", "test@email.com", null, null, "Test User", 0);

        when(customerIntegration.getCustomerDetails(0)).thenReturn(customer0);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(customer0)).thenReturn(credentials0);

        OauthCredentialsDTO inputWithZeroId = new OauthCredentialsDTO(
                "client-id",
                "client-secret",
                "password",
                "customer",
                "Test User",
                0
        );

        OauthCredentialsDTO result = oauthCustomerRepository.validateCredentials(inputWithZeroId);

        assertNotNull(result);
        assertEquals(0, result.customerId());
        verify(customerIntegration).getCustomerDetails(0);
    }

    @Test
    @DisplayName("Deve validar credenciais quando CustomerDTO tem campos null")
    void deveValidarCredenciaisQuandoCustomerDTOTemCamposNull() {
        CustomerDTO customerWithNulls = new CustomerDTO(1, null, null, null);
        OauthCredentialsDTO credentialsWithNulls = new OauthCredentialsDTO(null, null, null, null, null, 1);

        when(customerIntegration.getCustomerDetails(1)).thenReturn(customerWithNulls);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(customerWithNulls)).thenReturn(credentialsWithNulls);

        OauthCredentialsDTO result = oauthCustomerRepository.validateCredentials(inputCredentials);

        assertNotNull(result);
        assertNull(result.client_id());
        assertNull(result.client_secret());
        assertNull(result.name());
        assertEquals(1, result.customerId());

        verify(customerIntegration).getCustomerDetails(1);
        verify(oauthMapper).customerToDtoToOauthCredentialsDTO(customerWithNulls);
    }

    @Test
    @DisplayName("Deve processar múltiplas validações de forma independente")
    void deveProcessarMultiplasValidacoesDeFormaIndependente() {
        CustomerDTO customer1 = new CustomerDTO(1, "111", "User 1", "user1@email.com");
        CustomerDTO customer2 = new CustomerDTO(2, "222", "User 2", "user2@email.com");

        OauthCredentialsDTO cred1 = new OauthCredentialsDTO("111", "user1@email.com", null, null, "User 1", 1);
        OauthCredentialsDTO cred2 = new OauthCredentialsDTO("222", "user2@email.com", null, null, "User 2", 2);

        when(customerIntegration.getCustomerDetails(1)).thenReturn(customer1);
        when(customerIntegration.getCustomerDetails(2)).thenReturn(customer2);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(customer1)).thenReturn(cred1);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(customer2)).thenReturn(cred2);

        OauthCredentialsDTO input1 = new OauthCredentialsDTO("x", "y", "z", "a", "User 1", 1);
        OauthCredentialsDTO input2 = new OauthCredentialsDTO("x", "y", "z", "a", "User 2", 2);

        OauthCredentialsDTO result1 = oauthCustomerRepository.validateCredentials(input1);
        OauthCredentialsDTO result2 = oauthCustomerRepository.validateCredentials(input2);

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotEquals(result1.customerId(), result2.customerId());
        assertEquals(1, result1.customerId());
        assertEquals(2, result2.customerId());

        verify(customerIntegration).getCustomerDetails(1);
        verify(customerIntegration).getCustomerDetails(2);
        verify(oauthMapper).customerToDtoToOauthCredentialsDTO(customer1);
        verify(oauthMapper).customerToDtoToOauthCredentialsDTO(customer2);
    }

    @Test
    @DisplayName("Deve manter a ordem de chamadas dos métodos")
    void deveManterOrdemDeChamadasDosMetodos() {
        when(customerIntegration.getCustomerDetails(1)).thenReturn(customerDTO);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO)).thenReturn(expectedCredentials);

        oauthCustomerRepository.validateCredentials(inputCredentials);

        var inOrder = inOrder(customerIntegration, oauthMapper);
        inOrder.verify(customerIntegration).getCustomerDetails(1);
        inOrder.verify(oauthMapper).customerToDtoToOauthCredentialsDTO(customerDTO);
    }

    @Test
    @DisplayName("Deve validar credenciais ignorando outros campos do input")
    void deveValidarCredenciaisIgnorandoOutrosCamposDoInput() {
        // Os campos client_id, client_secret, grant_type e scope do input não devem afetar o resultado
        OauthCredentialsDTO differentInput = new OauthCredentialsDTO(
                "different-client-id",
                "different-secret",
                "different-grant",
                "different-scope",
                "Different Name",
                1  // Apenas o customerId é usado
        );

        when(customerIntegration.getCustomerDetails(1)).thenReturn(customerDTO);
        when(oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO)).thenReturn(expectedCredentials);

        OauthCredentialsDTO result = oauthCustomerRepository.validateCredentials(differentInput);

        assertNotNull(result);
        assertEquals(expectedCredentials, result);
        verify(customerIntegration).getCustomerDetails(1);
    }
}



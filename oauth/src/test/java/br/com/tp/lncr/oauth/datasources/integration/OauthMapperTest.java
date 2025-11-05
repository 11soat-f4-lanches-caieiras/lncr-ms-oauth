package br.com.tp.lncr.oauth.datasources.integration;

import br.com.tp.lncr.core.dtos.customer.CustomerDTO;
import br.com.tp.lncr.core.dtos.oauth.OauthCredentialsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OauthMapper - Testes Unitários")
class OauthMapperTest {

    private OauthMapper oauthMapper;

    @BeforeEach
    void setUp() {
        oauthMapper = new OauthMapper();
    }

    @Test
    @DisplayName("Deve converter CustomerDTO para OauthCredentialsDTO com sucesso")
    void deveConverterCustomerDTOParaOauthCredentialsDTO() {
        CustomerDTO customerDTO = new CustomerDTO(
                1,
                "12345678900",
                "João Silva",
                "joao.silva@email.com"
        );

        OauthCredentialsDTO result = oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO);

        assertNotNull(result);
        assertEquals("12345678900", result.client_id());
        assertEquals("joao.silva@email.com", result.client_secret());
        assertNull(result.grant_type());
        assertNull(result.scope());
        assertEquals("João Silva", result.name());
        assertEquals(1, result.customerId());
    }

    @Test
    @DisplayName("Deve retornar null quando CustomerDTO é null")
    void deveRetornarNullQuandoCustomerDTONull() {
        OauthCredentialsDTO result = oauthMapper.customerToDtoToOauthCredentialsDTO(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Deve mapear CustomerDTO com valores vazios")
    void deveMapeiaCustomerDTOComValoresVazios() {
        CustomerDTO customerDTO = new CustomerDTO(
                null,
                "",
                "",
                ""
        );

        OauthCredentialsDTO result = oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO);

        assertNotNull(result);
        assertEquals("", result.client_id());
        assertEquals("", result.client_secret());
        assertNull(result.grant_type());
        assertNull(result.scope());
        assertEquals("", result.name());
        assertNull(result.customerId());
    }

    @Test
    @DisplayName("Deve mapear CustomerDTO com ID zero")
    void deveMapeiaCustomerDTOComIdZero() {
        CustomerDTO customerDTO = new CustomerDTO(
                0,
                "98765432100",
                "Maria Santos",
                "maria.santos@email.com"
        );

        OauthCredentialsDTO result = oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO);

        assertNotNull(result);
        assertEquals(0, result.customerId());
        assertEquals("98765432100", result.client_id());
        assertEquals("maria.santos@email.com", result.client_secret());
        assertEquals("Maria Santos", result.name());
    }

    @Test
    @DisplayName("Deve mapear CustomerDTO com ID negativo")
    void deveMapeiaCustomerDTOComIdNegativo() {
        CustomerDTO customerDTO = new CustomerDTO(
                -1,
                "11122233344",
                "Pedro Oliveira",
                "pedro.oliveira@email.com"
        );

        OauthCredentialsDTO result = oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO);

        assertNotNull(result);
        assertEquals(-1, result.customerId());
    }

    @Test
    @DisplayName("Deve mapear CustomerDTO com campos null")
    void deveMapeiaCustomerDTOComCamposNull() {
        CustomerDTO customerDTO = new CustomerDTO(
                2,
                null,
                null,
                null
        );

        OauthCredentialsDTO result = oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO);

        assertNotNull(result);
        assertNull(result.client_id());
        assertNull(result.client_secret());
        assertNull(result.name());
        assertEquals(2, result.customerId());
        assertNull(result.grant_type());
        assertNull(result.scope());
    }

    @Test
    @DisplayName("Deve mapear CustomerDTO com caracteres especiais")
    void deveMapeiaCustomerDTOComCaracteresEspeciais() {
        CustomerDTO customerDTO = new CustomerDTO(
                3,
                "123.456.789-00",
                "José da Silva Júnior",
                "jose+tag@email.com.br"
        );

        OauthCredentialsDTO result = oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO);

        assertNotNull(result);
        assertEquals("123.456.789-00", result.client_id());
        assertEquals("jose+tag@email.com.br", result.client_secret());
        assertEquals("José da Silva Júnior", result.name());
        assertEquals(3, result.customerId());
    }

    @Test
    @DisplayName("Deve garantir que grant_type e scope sejam sempre null no mapeamento")
    void deveGarantirGrantTypeEScopeNullNoMapeamento() {
        CustomerDTO customerDTO = new CustomerDTO(
                100,
                "document",
                "name",
                "email"
        );

        OauthCredentialsDTO result = oauthMapper.customerToDtoToOauthCredentialsDTO(customerDTO);

        assertNotNull(result);
        assertNull(result.grant_type(), "grant_type deve ser null");
        assertNull(result.scope(), "scope deve ser null");
    }

    @Test
    @DisplayName("Deve mapear múltiplos CustomerDTO de forma independente")
    void deveMapeiaMultiplosCustomerDTODeFormaIndependente() {
        CustomerDTO customer1 = new CustomerDTO(1, "111", "Customer 1", "email1@test.com");
        CustomerDTO customer2 = new CustomerDTO(2, "222", "Customer 2", "email2@test.com");

        OauthCredentialsDTO result1 = oauthMapper.customerToDtoToOauthCredentialsDTO(customer1);
        OauthCredentialsDTO result2 = oauthMapper.customerToDtoToOauthCredentialsDTO(customer2);

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotEquals(result1.customerId(), result2.customerId());
        assertNotEquals(result1.client_id(), result2.client_id());
        assertNotEquals(result1.name(), result2.name());
        assertNotEquals(result1.client_secret(), result2.client_secret());
    }

    @Test
    @DisplayName("Deve criar instância do mapper sem erros")
    void deveCriarInstanciaDoMapperSemErros() {
        OauthMapper mapper = new OauthMapper();

        assertNotNull(mapper);
    }
}
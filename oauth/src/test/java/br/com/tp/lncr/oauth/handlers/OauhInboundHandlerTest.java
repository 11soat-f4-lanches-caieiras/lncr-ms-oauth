package br.com.tp.lncr.oauth.handlers;

import br.com.tp.lncr.core.exceptions.OauthException;
import br.com.tp.lncr.commons.model.ResponseMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OauhInboundHandler - Testes Unitários")
class OauhInboundHandlerTest {

    private OauhInboundHandler handler;

    @BeforeEach
    void setUp() {
        handler = new OauhInboundHandler();
    }

    @Test
    @DisplayName("Deve tratar OauthException com código 400 - Bad Request")
    void deveTratarOauthExceptionCom400() {
        OauthException exception = new OauthException("Credenciais inválidas", 400);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(ResponseMetadata.class, response.getBody());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertEquals("Credenciais inválidas", metadata.getMessage());
        assertNotNull(metadata.getTraceId());
        assertNotNull(metadata.getTimestamp());
    }

    @Test
    @DisplayName("Deve tratar OauthException com código 401 - Unauthorized")
    void deveTratarOauthExceptionCom401() {
        OauthException exception = new OauthException("Token inválido ou expirado", 401);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertEquals("Token inválido ou expirado", metadata.getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com código 403 - Forbidden")
    void deveTratarOauthExceptionCom403() {
        OauthException exception = new OauthException("Acesso negado", 403);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNotNull(metadata);
        assertEquals("Acesso negado", metadata.getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com código 404 - Not Found")
    void deveTratarOauthExceptionCom404() {
        OauthException exception = new OauthException("Recurso não encontrado", 404);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNotNull(metadata);
        assertEquals("Recurso não encontrado", metadata.getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com código 409 - Conflict")
    void deveTratarOauthExceptionCom409() {
        OauthException exception = new OauthException("Conflito de dados", 409);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNotNull(metadata);
        assertEquals("Conflito de dados", metadata.getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com código 500 - Internal Server Error")
    void deveTratarOauthExceptionCom500() {
        OauthException exception = new OauthException("Erro interno do servidor", 500);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNotNull(metadata);
        assertEquals("Erro interno do servidor", metadata.getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com código 503 - Service Unavailable")
    void deveTratarOauthExceptionCom503() {
        OauthException exception = new OauthException("Serviço indisponível", 503);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNotNull(metadata);
        assertEquals("Serviço indisponível", metadata.getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com código 504 - Gateway Timeout")
    void deveTratarOauthExceptionCom504() {
        OauthException exception = new OauthException("Tempo esgotado", 504);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.GATEWAY_TIMEOUT, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNotNull(metadata);
        assertEquals("Tempo esgotado", metadata.getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com código desconhecido - Default Internal Server Error")
    void deveTratarOauthExceptionComCodigoDesconhecido() {
        OauthException exception = new OauthException("Erro não mapeado", 999);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNotNull(metadata);
        assertEquals("Erro não mapeado", metadata.getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com mensagem vazia")
    void deveTratarOauthExceptionComMensagemVazia() {
        OauthException exception = new OauthException("", 400);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNotNull(metadata);
        assertEquals("", metadata.getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com mensagem null")
    void deveTratarOauthExceptionComMensagemNull() {
        OauthException exception = new OauthException(null, 400);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNull(Objects.requireNonNull(metadata).getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com mensagem longa")
    void deveTratarOauthExceptionComMensagemLonga() {
        String longMessage = "Esta é uma mensagem de erro muito longa ".repeat(10);
        OauthException exception = new OauthException(longMessage, 400);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNotNull(metadata);
        assertEquals(longMessage, metadata.getMessage());
    }

    @Test
    @DisplayName("Deve tratar OauthException com caracteres especiais na mensagem")
    void deveTratarOauthExceptionComCaracteresEspeciais() {
        String specialMessage = "Erro: Client_ID 'test@123' com scope 'admin:write' não autorizado!";
        OauthException exception = new OauthException(specialMessage, 403);

        ResponseEntity<Object> response = handler.handleOauthException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertNotNull(metadata);
        assertEquals(specialMessage, metadata.getMessage());
    }

    @Test
    @DisplayName("Deve gerar traceId único para cada exceção")
    void deveGerarTraceIdUnicoParaCadaExcecao() {
        OauthException exception1 = new OauthException("Erro 1", 400);
        OauthException exception2 = new OauthException("Erro 2", 400);

        ResponseEntity<Object> response1 = handler.handleOauthException(exception1);
        ResponseEntity<Object> response2 = handler.handleOauthException(exception2);

        ResponseMetadata metadata1 = (ResponseMetadata) response1.getBody();
        ResponseMetadata metadata2 = (ResponseMetadata) response2.getBody();

        assertNotNull(metadata1);
        assertNotNull(metadata2);
        assertNotEquals(metadata1.getTraceId(), metadata2.getTraceId());
    }

    @Test
    @DisplayName("Deve manter o código de erro correto ao tratar múltiplas exceções")
    void deveManterCodigoErroCorretoAoTratarMultiplasExcecoes() {
        OauthException exception400 = new OauthException("Bad Request", 400);
        OauthException exception401 = new OauthException("Unauthorized", 401);
        OauthException exception500 = new OauthException("Server Error", 500);

        ResponseEntity<Object> response400 = handler.handleOauthException(exception400);
        ResponseEntity<Object> response401 = handler.handleOauthException(exception401);
        ResponseEntity<Object> response500 = handler.handleOauthException(exception500);

        assertEquals(HttpStatus.BAD_REQUEST, response400.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED, response401.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response500.getStatusCode());
    }

    @Test
    @DisplayName("Deve validar que o ResponseMetadata contém todos os campos obrigatórios")
    void deveValidarQueResponseMetadataContemTodosCamposObrigatorios() {
        OauthException exception = new OauthException("Scope inválido: test-scope", 400);

        ResponseEntity<Object> response = handler.handleOauthException(exception);
        ResponseMetadata metadata = (ResponseMetadata) response.getBody();

        assertNotNull(metadata);
        assertNotNull(metadata.getTraceId(), "TraceId não deve ser null");
        assertNotNull(metadata.getTimestamp(), "Timestamp não deve ser null");
        assertNotNull(metadata.getMessage(), "Message não deve ser null");
        assertFalse(metadata.getTraceId().isEmpty(), "TraceId não deve ser vazio");
        assertFalse(metadata.getTimestamp().isEmpty(), "Timestamp não deve ser vazio");
    }

    @Test
    @DisplayName("Deve criar instância do handler sem erros")
    void deveCriarInstanciaDoHandlerSemErros() {
        OauhInboundHandler newHandler = new OauhInboundHandler();

        assertNotNull(newHandler);
    }

    @Test
    @DisplayName("Deve processar exceções OAuth específicas do domínio")
    void deveProcessarExcecoesOAuthEspecificasDoDominio() {
        // Cenários típicos de OAuth
        OauthException invalidClient = new OauthException("invalid_client: Client authentication failed", 401);
        OauthException invalidGrant = new OauthException("invalid_grant: Invalid user credentials", 400);
        OauthException unsupportedGrantType = new OauthException("unsupported_grant_type", 400);
        OauthException invalidScope = new OauthException("invalid_scope: Requested scope is invalid", 400);

        ResponseEntity<Object> response1 = handler.handleOauthException(invalidClient);
        ResponseEntity<Object> response2 = handler.handleOauthException(invalidGrant);
        ResponseEntity<Object> response3 = handler.handleOauthException(unsupportedGrantType);
        ResponseEntity<Object> response4 = handler.handleOauthException(invalidScope);

        assertEquals(HttpStatus.UNAUTHORIZED, response1.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response4.getStatusCode());

        ResponseMetadata metadata1 = (ResponseMetadata) response1.getBody();
        assertNotNull(metadata1);
        assertTrue(metadata1.getMessage().contains("invalid_client"));
    }

    @Test
    @DisplayName("Deve manter consistência ao processar mesma exceção múltiplas vezes")
    void deveManterConsistenciaAoProcessarMesmaExcecaoMultiplasVezes() {
        OauthException exception = new OauthException("Token expirado", 401);

        ResponseEntity<Object> response1 = handler.handleOauthException(exception);
        ResponseEntity<Object> response2 = handler.handleOauthException(exception);

        assertEquals(response1.getStatusCode(), response2.getStatusCode());

        ResponseMetadata metadata1 = (ResponseMetadata) response1.getBody();
        ResponseMetadata metadata2 = (ResponseMetadata) response2.getBody();

        assertNotNull(metadata1);
        assertNotNull(metadata2);
        assertEquals(metadata1.getMessage(), metadata2.getMessage());
        // TraceIds devem ser diferentes mesmo para mesma exceção
        assertNotEquals(metadata1.getTraceId(), metadata2.getTraceId());
    }
}


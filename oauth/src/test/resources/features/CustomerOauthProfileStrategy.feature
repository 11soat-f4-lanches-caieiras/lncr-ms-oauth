#language: pt

Funcionalidade: Estratégia de Perfil Customer OAuth
  Como um sistema de autenticação
  Eu preciso processar autenticação de perfil Customer
  Para validar credenciais de clientes

  Cenário: Criar estratégia Customer com database JPA
    Dado que tenho uma OauthDatabase JPA válida
    Quando eu crio uma CustomerOauthProfileStrategy
    Então a estratégia Customer deve ser criada com sucesso
    E deve usar a database JPA fornecida

  Cenário: Validar credenciais de cliente
    Dado que tenho uma CustomerOauthProfileStrategy configurada
    E tenho credenciais de cliente válidas
    Quando eu valido as credenciais usando a estratégia
    Então a validação deve ser bem-sucedida


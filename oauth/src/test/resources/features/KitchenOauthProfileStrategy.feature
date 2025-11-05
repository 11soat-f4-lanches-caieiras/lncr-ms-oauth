#language: pt

Funcionalidade: Estratégia de Perfil Kitchen OAuth
  Como um sistema de autenticação
  Eu preciso processar autenticação de perfil Kitchen
  Para validar credenciais da cozinha

  Cenário: Criar estratégia Kitchen com database de configuração
    Dado que tenho uma OauthDatabase de configuração válida
    Quando eu crio uma KitchenOauthProfileStrategy
    Então a estratégia Kitchen deve ser criada com sucesso
    E deve usar a database de configuração fornecida

  Cenário: Validar credenciais da cozinha
    Dado que tenho uma KitchenOauthProfileStrategy configurada
    E tenho credenciais da cozinha válidas
    Quando eu valido as credenciais usando a estratégia
    Então a validação deve ser bem-sucedida


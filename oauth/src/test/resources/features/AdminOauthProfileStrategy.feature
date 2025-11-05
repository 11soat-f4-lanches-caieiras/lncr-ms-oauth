#language: pt

Funcionalidade: Estratégia de Perfil Admin OAuth
  Como um sistema de autenticação
  Eu preciso processar autenticação de perfil Admin
  Para validar credenciais administrativas

  Cenário: Criar estratégia Admin com database de configuração
    Dado que tenho uma OauthDatabase de configuração válida
    Quando eu crio uma AdminOauthProfileStrategy
    Então a estratégia Admin deve ser criada com sucesso
    E deve usar a database de configuração fornecida

  Cenário: Validar credenciais de admin
    Dado que tenho uma AdminOauthProfileStrategy configurada
    E tenho credenciais administrativas válidas
    Quando eu valido as credenciais usando a estratégia
    Então a validação deve ser bem-sucedida


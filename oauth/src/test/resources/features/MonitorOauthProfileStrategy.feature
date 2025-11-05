#language: pt

Funcionalidade: Estratégia de Perfil Monitor OAuth
  Como um sistema de autenticação
  Eu preciso processar autenticação de perfil Monitor
  Para validar credenciais de monitores

  Cenário: Criar estratégia Monitor com database de configuração
    Dado que tenho uma OauthDatabase de configuração válida
    Quando eu crio uma MonitorOauthProfileStrategy
    Então a estratégia Monitor deve ser criada com sucesso
    E deve usar a database de configuração fornecida

  Cenário: Validar credenciais de monitor
    Dado que tenho uma MonitorOauthProfileStrategy configurada
    E tenho credenciais de monitor válidas
    Quando eu valido as credenciais usando a estratégia
    Então a validação deve ser bem-sucedida


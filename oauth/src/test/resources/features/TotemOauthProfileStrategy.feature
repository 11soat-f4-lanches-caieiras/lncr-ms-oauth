#language: pt

Funcionalidade: Estratégia de Perfil Totem OAuth
  Como um sistema de autenticação
  Eu preciso processar autenticação de perfil Totem
  Para validar credenciais de totens de autoatendimento

  Cenário: Criar estratégia Totem com database de configuração
    Dado que tenho uma OauthDatabase de configuração válida
    Quando eu crio uma TotemOauthProfileStrategy
    Então a estratégia Totem deve ser criada com sucesso
    E deve usar a database de configuração fornecida

  Cenário: Validar credenciais de totem
    Dado que tenho uma TotemOauthProfileStrategy configurada
    E tenho credenciais de totem válidas
    Quando eu valido as credenciais usando a estratégia
    Então a validação deve ser bem-sucedida


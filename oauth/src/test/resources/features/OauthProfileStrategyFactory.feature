#language: pt

Funcionalidade: Fábrica de Estratégias de Perfil OAuth
  Como um sistema de autenticação
  Eu preciso criar estratégias de perfil OAuth adequadas
  Para processar diferentes tipos de autenticação

  Cenário: Criar estratégia para perfil ADMIN
    Dado que tenho uma OauthDatabase de configuração
    E tenho uma OauthDatabase JPA
    Quando eu solicito a estratégia para o perfil "ADMIN"
    Então a estratégia AdminOauthProfileStrategy deve ser retornada
    E a estratégia deve usar a database de configuração

  Cenário: Criar estratégia para perfil CUSTOMER
    Dado que tenho uma OauthDatabase de configuração
    E tenho uma OauthDatabase JPA
    Quando eu solicito a estratégia para o perfil "CUSTOMER"
    Então a estratégia CustomerOauthProfileStrategy deve ser retornada
    E a estratégia deve usar a database JPA

  Cenário: Criar estratégia para perfil MONITOR
    Dado que tenho uma OauthDatabase de configuração
    E tenho uma OauthDatabase JPA
    Quando eu solicito a estratégia para o perfil "MONITOR"
    Então a estratégia MonitorOauthProfileStrategy deve ser retornada
    E a estratégia deve usar a database de configuração

  Cenário: Criar estratégia para perfil TOTEM
    Dado que tenho uma OauthDatabase de configuração
    E tenho uma OauthDatabase JPA
    Quando eu solicito a estratégia para o perfil "TOTEM"
    Então a estratégia TotemOauthProfileStrategy deve ser retornada
    E a estratégia deve usar a database de configuração

  Cenário: Rejeitar perfil inválido
    Dado que tenho uma OauthDatabase de configuração
    E tenho uma OauthDatabase JPA
    Quando eu solicito a estratégia para o perfil "INVALIDO"
    Então uma exceção OauthException deve ser lançada
    E a mensagem de erro deve ser "Perfil inválido"


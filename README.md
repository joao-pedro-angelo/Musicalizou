# Musicalizou

> Musicalizou é uma API REST que tem como principal objetivo promover a prática no desenvolvimento de aplicações com Java e Spring.


---
## Funcionalidades Principais

- **Explorar músicas**;
- **Reviews das músicas**;
- **Gerenciar artistas**;
- **Login e controle de acesso**;
- **Histórico da base de dados (migrations)**.


---
## Tecnologias Utilizadas

- **Spring Boot**; 
- **Spring Security**;
- **Spring Data JPA**;
- **Spring Tests**;
- **Lombok**;
- **Validações**;
- **JWT (JSON Web Tokens)**;
- **Hashing de senha com BCrypt**;
- **Migrations com Flyway**;
- **Swagger para documentação**;
- **Database exclusiva para testes - H2**;
- **Database exclusiva para produção - Mysql**;


---
## Diretórios importantes

- [Perfil de produção - application.properties](src/main/resources/application.properties)
- [Perfil de testes - application-test.properties](src/test/resources/application-test.properties)
- [Controllers](src/main/java/com/music/review/app/controllers)
- [Services](src/main/java/com/music/review/app/services)
- [Domain](src/main/java/com/music/review/app/domain)
- [Infra](src/main/java/com/music/review/app/infra)
- [Testes](src/test/java/com/music/review/app/controllers)
- [Migrations de produção - Mysql](src/main/resources/db/migration/mysql)
- [Migrations de testes - H2](src/main/resources/db/migration/h2)

---
## Como Executar

1. **Configuração do Ambiente:**
    - Certifique-se de ter o Java JDK, o MySQL e o Maven instalados em sua máquina.
    - Caso não possua algum desses requisitos instalados, basta digitar `java --version`, `mvn --version` ou `mysql --version` e verificar o comando de instalação no Linux.
    - Clone o repositório do Musicalizou para o seu ambiente local.

2. **Configuração do Banco de Dados:**
    - Configure as credenciais do banco de dados no arquivo `application.properties`.

3. **Execução da API:**
    - Abra o projeto em sua IDE preferida.
    - Execute o aplicativo Spring Boot.

4. **Acesso à API:**
    - URL de acesso: http://localhost:8080/swagger-ui/index.html
    ![telaInicial](images/telaInicialSwagger.png)

5. **Realizar Login:**
    - email: basicUser@gmail.com
    - password: 123456
    ![loginExample](images/loginExample.png)

7. **Copiar Token para autorização:**
   - O Token é o que foi retornado
    no Login.
    ![tokenExample](images/tokenExample.png)

9. **Requisições liberadas:**
    - Depois de inserir o token no campo de autorização,
    todas as requisições estarão liberadas.

---
## Melhorias para o futuro

- **Desenvolvimento do Front-end**: Embora a API REST do Musicalizou forneça acesso aos dados, uma interface de usuário (UI) pode ser desenvolvida e integrada para facilitar a interação com os recursos da API.

- **Arquitetura**: Refatorar o projeto para utilizar a arquitetura de microsserviços.

---
## Contribuição

Se você deseja contribuir com o desenvolvimento do Musicalizou, siga estas etapas:

1. Faça um fork do repositório e clone o fork para o seu ambiente local.
2. Crie uma branch para sua nova funcionalidade ou correção de bug: `git checkout -b nome-da-sua-branch`.
3. Faça suas alterações e adicione testes, se aplicável.
4. Commit suas alterações: `git commit -m 'Descrição das alterações'`.
5. Push para a branch: `git push origin nome-da-sua-branch`.
6. Abra um pull request no repositório original.

Sinta-se à vontade para contribuir com esta aplicação!


---
## Licença

O Musicalizou é licenciado sob a [MIT License](LICENSE).

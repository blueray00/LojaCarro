# Relatório — Atividade sobre Testes de Integração com Spring Boot

# Introdução

Durante a realização desta atividade foi desenvolvido um conjunto de testes de integração em uma aplicação Spring Boot voltada para o gerenciamento de carros. O principal objetivo da atividade foi compreender como os testes de integração funcionam na prática, além de validar a comunicação entre diferentes componentes do sistema, garantindo que a aplicação funcionasse corretamente de forma completa e integrada.

Inicialmente, foi necessário verificar se o ambiente estava corretamente configurado. Para isso, foram realizados testes para confirmar o funcionamento do Maven, da aplicação Spring Boot e do banco de dados utilizado durante os testes. Também foi feita a validação dos testes unitários já existentes no projeto, garantindo que a aplicação estivesse estável antes da implementação dos novos testes de integração.

---

# O que são Testes de Integração

Os testes de integração possuem um papel muito importante no desenvolvimento de software, pois são responsáveis por verificar se diferentes partes do sistema funcionam corretamente em conjunto. Diferente dos testes unitários, que avaliam métodos isolados utilizando mocks e simulações, os testes de integração analisam o comportamento real da aplicação, envolvendo comunicação entre controllers, services, repositories e banco de dados.

Esse tipo de teste é fundamental para identificar falhas que normalmente não aparecem em testes unitários, como erros de persistência, problemas de configuração, falhas de comunicação entre camadas e inconsistências no acesso aos dados.

---

# Componentes Integrados no Projeto

Durante o desenvolvimento da atividade, os principais componentes integrados foram a camada de controller, responsável pelos endpoints da aplicação, a camada de service, onde ficam as regras de negócio, o repository, responsável pela comunicação com o banco de dados, além da integração com o banco H2 utilizado nos testes automatizados e com o MySQL utilizado na aplicação principal.

---

# Diferença entre Teste Unitário e Teste de Integração

Os testes unitários verificam partes isoladas da aplicação, normalmente um único método ou classe. Eles utilizam mocks e não dependem de banco de dados real, sendo mais rápidos e focados em validar pequenas regras de negócio específicas.

Já os testes de integração avaliam o funcionamento conjunto dos componentes da aplicação. Nesse tipo de teste são utilizadas conexões reais com banco de dados, integração entre camadas e validação completa dos fluxos do sistema, tornando os testes mais próximos do comportamento real da aplicação.

---

# Desenvolvimento da Atividade

Foram implementados diversos testes para validar tanto cenários de sucesso quanto cenários de erro. Entre os testes de sucesso desenvolvidos estão o salvamento de carros no banco de dados, atualização de registros existentes, busca por ID, listagem de todos os carros e exclusão de registros.

Também foi implementado um teste utilizando a anotação `@Sql`, responsável por inserir automaticamente dados no banco antes da execução do teste, permitindo validar o comportamento do endpoint de listagem utilizando dados previamente cadastrados.

Além dos cenários positivos, também foram criados testes de erro para verificar se o sistema reagia corretamente diante de entradas inválidas. Entre eles estão o teste para salvar carro sem modelo, salvar carro com nome muito grande e salvar carro com ano inválido. Esses testes foram importantes para validar as regras de negócio e as validações implementadas na aplicação.

---

# Configuração dos Bancos de Dados

Durante a atividade também foi realizada a separação dos ambientes de execução da aplicação e dos testes. Para isso, o banco H2 foi utilizado especificamente para os testes automatizados por meio do profile `test`, configurado utilizando a anotação `@ActiveProfiles("test")`.

Essa abordagem trouxe vantagens como maior velocidade na execução dos testes, independência do banco real e maior segurança durante o desenvolvimento.

Já a aplicação principal foi configurada para utilizar o MySQL, simulando um ambiente mais próximo de um cenário real de produção.

---

# Cobertura de Testes com JaCoCo

Outro ponto importante desenvolvido na atividade foi a implementação da cobertura de testes utilizando JaCoCo. Com isso, foi possível gerar relatórios mostrando quais partes do sistema estavam sendo executadas durante os testes, permitindo visualizar o percentual de cobertura da aplicação e identificar possíveis trechos ainda não testados.

---

# Integração Contínua com GitHub Actions

Também foi configurada uma pipeline de integração contínua utilizando GitHub Actions. Essa automação permitiu que os testes fossem executados automaticamente a cada push realizado no repositório, garantindo maior confiabilidade no desenvolvimento e ajudando na identificação rápida de possíveis falhas no sistema.

---

# Backup do Banco de Dados

Além disso, foi criado um banco MySQL chamado `lojacarro`, utilizado para armazenar os dados reais da aplicação. Após a criação e configuração do banco, foi realizado um backup utilizando o DBeaver, gerando um arquivo SQL contendo a estrutura da tabela e os dados necessários para restauração do banco futuramente.

Esse arquivo foi incluído no projeto para facilitar a execução e a reprodução do ambiente da aplicação.

---

# Resultados Obtidos

Ao final da atividade, todos os testes implementados foram executados com sucesso. A aplicação conseguiu validar corretamente os cenários esperados, tanto positivos quanto negativos, demonstrando que a integração entre as camadas do sistema estava funcionando adequadamente.

---

# Conclusão

A realização desta atividade foi importante para compreender de forma prática como os testes de integração funcionam em aplicações Spring Boot, além de mostrar a importância da automação de testes, da integração contínua e da validação do sistema como um todo.

Também foi possível perceber como ferramentas como H2, MySQL, MockMvc, JaCoCo e GitHub Actions ajudam a aumentar a qualidade, estabilidade e confiabilidade de aplicações modernas.
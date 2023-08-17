# API DE CONTA BANCÁRIA

O sisterma clonado originalmente foi atualizado com DTOs, mappers e novos métodos.

Antes era preciso passar o valor que o usuário possuía em sua conta para realizar os cálculos - agora o sistema se encarrega sozinho disso bastando passar o nome do usuário, o valor e qual operação ele deseja fazer (saque ou depósito).

## Bugs encontrados até o momento (17/08/2023)
- O sistema não está atualizando os valores de saque e depósito pelo PUT
- Ao criar uma conta é obrigatório passar se está fazendo saque ou depósito (o que não faz sentido)
- Uma variável estava fora das boas práticas

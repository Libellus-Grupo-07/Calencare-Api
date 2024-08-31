    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('19.518.895/0001-89', 'Acme Corporation', 'acme@email.com', '(11) 2500-0101', '2023-01-01', 60);
    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('11.383.787/0001-34', 'Luma Corporation', 'luma@email.com', '(11) 2500-0102', '2023-01-01', 60);
    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('13.412.628/0001-46', 'rachel Corporation', 'rachel@email.com', '(11) 2500-0103', '2023-01-01', 60);
    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('22.176.577/0001-91', 'Torres Corporation', 'torres@email.com', '(11) 2500-0104', '2023-01-01', 60);
    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('13.814.292/0001-48', 'Kevin Corporation', 'kevin@email.com', '(11) 2500-0105', '2023-01-01', 60);
    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('13.263.818/0001-49', 'Alou Corporation', 'alou@email.com', '(11) 2500-0106', '2023-01-01', 60);
    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('10.353.616/0001-08', 'Circa Corporation', 'circa@email.com', '(11) 2500-0107', '2023-01-01', 60);
    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('76.537.870/0001-66', 'Xanel Corporation', 'xanel@email.com', '(11) 2500-0108', '2023-01-01', 60);
    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('13.847.803/0001-28', 'ndqoiusbnaqosd Corporation', 'ndqoiusbnaqosd@email.com', '(11) 2500-0101', '2023-01-01', 60);
    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('47.222.174/0001-62', 'mlki Corporation', 'mlki@email.com', '(11) 2500-0109', '2023-01-01', 60);
    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('25.941.261/0001-37', 'saqfrewdfr Corporation', 'saqfrewdfr@email.com', '(11) 2500-0110', '2023-01-01', 60);

    INSERT INTO horario_funcionamento(dia_semana, cod_dia_semana, inicio, fim, status, empresa_id)
    VALUES
        ('SEGUNDA-FEIRA',1, '08:00:00', '18:00:00', 1,1),
        ('TERÇA-FEIRA',2, '08:00:00', '18:00:00', 1,1),
        ('QUARTA-FEIRA',3, '08:00:00', '18:00:00', 1,1),
        ('QUINTA-FEIRA',4, '08:00:00', '18:00:00', 1,1),
        ('SEXTA-FEIRA',5, '08:00:00', '18:00:00', 1,1),
        ('SÁBADO',6, '08:00:00', '12:00:00', 1,1),
        ('DOMINGO',7, NULL, NULL, 0,1);

    /*INSERT INTO cliente(nome, sobrenome, telefone, email, senha, dt_criacao)
    VALUES
        ('John', 'Doe', '(11) 99809-5638', 'johndoe@example.com', 'password123', CURRENT_TIMESTAMP),
        ('Jane', 'Doe', '(11) 90182-7890', 'janedoe@example.com', 'password123', CURRENT_TIMESTAMP),
        ('Marcelo', 'Carvalho', '(11) 99456-3433', 'marcelo@example.com', 'password123', CURRENT_TIMESTAMP);*/

    INSERT INTO categoria_servico(nome, descricao)
    VALUES
    ('Cabelo', 'Corte de cabelo'),
    ('Unha', 'Alongamento das unhas'),
    ('Podologia', 'Corte das unhas do pé e mão com uma podóloga');

    INSERT INTO servico(nome, categoria_id)
    VALUES
        ('Corte de cabelo', 1),
        ('Alongamento de unhas', 2),
        ('Corte unha pé', 2),
        ('Corte unha mão', 2),
        ('Podologia', 3);

    INSERT INTO servico_preco(descricao, preco, duracao, comissao, bit_status, empresa_id, servico_id)
    VALUES
        ('Cabelo', 70.00, 90, 00.50, 1, 1, 1),
        ('Unhas', 33.00, 60, 00.50, 1, 1, 2),
        ('podologia', 50.00, 60, 00.60, 1, 1, 5);

    INSERT INTO funcionario(nome, telefone, email, senha, perfil_acesso, bit_status, empresa_id)
    VALUES
    ('Keven Histolino', '(11) 99436-6790', 'kevenhisttt@gmail.com', 'password123','admin', 1, 1),
    ('Keven Histolino SPTECH', '(11) 94869-9486', 'keven.histolino@sptech.school', 'password123','admin', 1, 1),
    ('Jessica Martins', '(11) 99018-6678', 'jessica.martins@sptech.school','password123','admin', 1, 1),
    ('Luma', '(11) 99446-4611', 'lumasouza012002@gmail.com','password123','admin', 1, 1),
    ('Raquel', '(11) 99446-4611', 'raquel.silva@sptech.school','password123','admin', 1, 1),
    ('Torres', '(11) 99446-4611', 'gustavo.souza@sptech.school','password123','admin', 1, 1),
    ('Vânia', '(11) 9668-4565', 'Vania@example.com', 'password123','normal', 1, 1);

    INSERT INTO servico_por_funcionario(dt_add, funcionario_id, servico_preco_id, bit_status)
    VALUES
        (CURRENT_TIMESTAMP, 1, 1,1),
        (CURRENT_TIMESTAMP, 1, 2,1),
        (CURRENT_TIMESTAMP, 2, 3,1),
        (CURRENT_TIMESTAMP, 3, 2,1),
        (CURRENT_TIMESTAMP, 4, 1,1),
        (CURRENT_TIMESTAMP, 5, 1,1);

    -- Categoria 1: Materiais Descartáveis
    INSERT INTO categoria_despesa (nome, descricao)
    VALUES ('Materiais Descartáveis', 'Luvas, máscaras, toucas, seringas, agulhas, etc.');

    -- Categoria 2: Produtos de Beleza
    INSERT INTO categoria_despesa (nome, descricao)
    VALUES ('Produtos de Beleza', 'Cremes, loções, óleos, maquiagens, etc.');

    -- Categoria 3: Equipamentos Médicos
    INSERT INTO categoria_despesa (nome, descricao)
    VALUES ('Equipamentos Médicos', 'Lasers, aparelhos de ultrassom, mesas de massagem, etc.');

    -- Categoria 4: Despesas com Pessoal
    INSERT INTO categoria_despesa (nome, descricao)
    VALUES ('Despesas com Pessoal', 'Salários, encargos sociais, treinamentos, etc.');

    -- Categoria 5: Aluguel e Manutenção
    INSERT INTO categoria_despesa (nome, descricao)
    VALUES ('Aluguel e Manutenção', 'Aluguel do espaço, manutenção de equipamentos, etc.');

    -- Categoria 6: Marketing e Publicidade
    INSERT INTO categoria_despesa (nome, descricao)
    VALUES ('Marketing e Publicidade', 'Campanhas de marketing, anúncios, etc.');

    -- Categoria 7: Utilitários
    INSERT INTO categoria_despesa (nome, descricao)
    VALUES ('Utilitários', 'Água, luz, telefone, internet, etc.');

    -- Categoria 8: Impostos e Taxas
    INSERT INTO categoria_despesa (nome, descricao)
    VALUES ('Impostos e Taxas', 'Imposto de Renda, ISS, etc.');

    -- Categoria 9: Outras Despesas
    INSERT INTO categoria_despesa (nome, descricao)
    VALUES ('Outras Despesas', 'Despesas diversas não categorizadas acima.');

    --
    -- INSERT INTO servico_por_funcionario(dt_add, funcionario_id, servico_preco_id)
    -- VALUES
    -- (CURRENT_TIMESTAMP, 1, 1),
    -- (CURRENT_TIMESTAMP, 1, 2),
    -- (CURRENT_TIMESTAMP, 2, 3),
    -- (CURRENT_TIMESTAMP, 3, 2),
    -- (CURRENT_TIMESTAMP, 4, 1),
    -- (CURRENT_TIMESTAMP, 5, 1);
    --
    INSERT INTO cliente(nome, sobrenome, telefone, email, dt_criacao)
    VALUES
        ('João', 'Silva', '(11) 99801-5638', 'joao@example.com', CURRENT_TIMESTAMP),
        ('Maria', 'Santos', '(11) 90182-7891', 'maria@example.com', CURRENT_TIMESTAMP),
        ('Pedro', 'Oliveira', '(11) 99456-3434', 'pedro@example.com', CURRENT_TIMESTAMP),
        ('Ana', 'Lima', '(11) 99456-3435', 'ana@example.com', CURRENT_TIMESTAMP),
        ('Lucas', 'Pereira', '(11) 99456-3436', 'lucas@example.com', CURRENT_TIMESTAMP),
        ('Paula', 'Costa', '(11) 99456-3437', 'paula@example.com', CURRENT_TIMESTAMP),
        ('Gabriel', 'Rodrigues', '(11) 99456-3438', 'gabriel@example.com', CURRENT_TIMESTAMP),
        ('Julia', 'Mendes', '(11) 99456-3439', 'julia@example.com', CURRENT_TIMESTAMP),
        ('Roberto', 'Barbosa', '(11) 99456-3440', 'roberto@example.com', CURRENT_TIMESTAMP),
        ('Tereza', 'Ribeiro', '(11) 99456-3441', 'tereza@example.com', CURRENT_TIMESTAMP),
        ('Ricardo', 'Almeida', '(11) 99456-3442', 'ricardo@example.com', CURRENT_TIMESTAMP),
        ('Mariana', 'Teixeira', '(11) 99456-3443', 'mariana@example.com', CURRENT_TIMESTAMP);

    -- Insira agendamentos com diferentes status, datas e associações a clientes, funcionários e serviços
    INSERT INTO agendamento(dt_hora, bit_status, cliente_id, funcionario_id, servico_preco_id)
    VALUES
        ('2024-07-01 09:00:00', 2, 1, 1, 1), -- Pendente
        ('2024-07-02 10:00:00', 3, 2, 1, 2), -- Cancelado
        ('2024-07-03 11:00:00', 5, 3, 2, 3), -- Finalizado
        ('2024-07-04 12:00:00', 2, 4, 2, 1), -- Pendente
        ('2024-07-05 13:00:00', 3, 5, 3, 2), -- Cancelado
        ('2024-07-06 14:00:00', 5, 1, 1, 3), -- Finalizado
        ('2024-07-07 15:00:00', 2, 2, 2, 1), -- Pendente
        ('2024-07-08 16:00:00', 3, 3, 3, 2), -- Cancelado
        ('2024-07-09 17:00:00', 5, 4, 1, 3), -- Finalizado
        ('2024-07-10 18:00:00', 2, 5, 2, 1), -- Pendente
        ('2024-07-11 19:00:00', 3, 1, 3, 2), -- Cancelado
        ('2024-07-12 20:00:00', 5, 2, 1, 3), -- Finalizado
        ('2024-07-13 21:00:00', 2, 3, 2, 1), -- Pendente
        ('2024-07-14 22:00:00', 3, 4, 3, 2), -- Cancelado
        ('2024-07-15 23:00:00', 5, 5, 1, 3); -- Finalizado

    INSERT INTO agendamento(dt_hora, bit_status, cliente_id, funcionario_id, servico_preco_id)
    VALUES

        ('2024-06-13 09:00:00', 2, 1, 1, 1), -- Pendente
        ('2024-06-13 10:00:00', 3, 2, 1, 2), -- Cancelado
        ('2024-06-13 11:00:00', 5, 3, 2, 3), -- Finalizado
        ('2024-06-13 12:00:00', 2, 4, 2, 1), -- Pendente
        ('2024-06-14 13:00:00', 3, 5, 3, 2), -- Cancelado
        ('2024-06-14 14:00:00', 5, 1, 1, 3), -- Finalizado
        ('2024-06-13 15:00:00', 2, 2, 2, 1), -- Pendente
        ('2024-06-13 16:00:00', 3, 3, 3, 2), -- Cancelado
        ('2024-06-13 17:00:00', 5, 4, 1, 3), -- Finalizado
        ('2024-06-13 18:00:00', 2, 5, 2, 1), -- Pendente
        ('2024-06-13 19:00:00', 3, 1, 3, 2), -- Cancelado
        ('2024-06-13 20:00:00', 5, 2, 1, 3), -- Finalizado
        ('2024-06-13 21:00:00', 2, 3, 2, 1), -- Pendente
        ('2024-06-13 22:00:00', 3, 4, 3, 2), -- Cancelado
        ('2024-06-13 23:00:00', 5, 5, 1, 3), -- Finalizado


        ('2024-07-08 09:00:00', 1, 1, 1, 1),
        ('2024-07-08 09:00:00', 1, 2, 2, 2),
        ('2024-07-08 09:00:00', 1, 3, 4, 3),
        ('2024-07-08 10:00:00', 1, 4, 2, 3),
        ('2024-07-08 10:00:00', 1, 5, 3, 2),
        ('2024-07-08 11:00:00', 1, 6, 3, 1),
        ('2024-07-08 11:00:00', 1, 7, 4, 2),
        ('2024-07-08 12:00:00', 1, 8, 1, 3),
        ('2024-07-08 12:00:00', 1, 9, 5, 2);

    INSERT INTO agendamento(dt_hora, bit_status, cliente_id, funcionario_id, servico_preco_id)
    VALUES

        ('2024-06-14 22:00:00', 1, 1, 1, 1),
        ('2024-06-14 22:00:00', 1, 2, 2, 2),
        ('2024-06-12 22:00:00', 1, 3, 4, 3);

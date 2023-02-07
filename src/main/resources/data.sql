INSERT INTO ACCOUNT_ENTITY (id, balance, currency, create_date) VALUES
  (111, 100.10, 'USD', '1999-01-22 00:00:00'),
  (222, 200, 'USD', '2020-01-22 00:00:00'),
  (333, 20, 'USD', '2003-01-22 00:00:00'),
  (444, 321.1, 'USD', '1999-01-22 00:00:00'),
  (555, 123, 'EUR', '2000-01-22 00:00:00'),
  (666, 666, 'EUR', '1966-06-06 06:06:06');

INSERT INTO STATEMENT_ENTITY (id, credit_id, debit_id, amount, currency, date) VALUES
  (11, 111, 222, 0.10, 'USD', '2023-01-22 00:00:00'),
  (22, 111, 222, 1, 'USD', '2023-01-22 01:00:00'),
  (33, 222, 111, 10.00, 'USD', '2023-01-23 00:00:00'),
  (44, 222, 111, 23.00, 'USD', '2023-01-23 10:00:00');
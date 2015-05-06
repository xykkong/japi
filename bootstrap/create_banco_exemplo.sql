-- Execute este script no banco de dados japi_exemplos (crie ele, se não existir)

-- Schema: teste_bd

-- DROP SCHEMA teste_bd;

CREATE SCHEMA teste_bd
  AUTHORIZATION postgres;
 
CREATE TABLE teste_bd.contratos(
  id_contrato SERIAL PRIMARY KEY,
  descricao CHARACTER VARYING(255),
  status BOOLEAN,
  data_termino TIMESTAMP WITHOUT TIME ZONE,
  valor_inicial NUMERIC(9,2),
  id_contratante INTEGER
);

CREATE TABLE teste_bd.empresas(
  id_empresa SERIAL PRIMARY KEY,
  nome CHARACTER(100),
  descricao CHARACTER VARYING(255),
  representante_legal CHARACTER(100),
  cnpj CHARACTER(18)
);
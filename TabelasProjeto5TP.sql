-- Criar schema
CREATE SCHEMA projetoTP5;
GO

-- Tabela Categorias
CREATE TABLE projetoTP5.Categorias (
    idCategoria INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);
GO

-- Tabela Produtos
CREATE TABLE projetoTP5.Produtos (
    idProduto INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL,
    idCategoria INT NOT NULL,
    FOREIGN KEY (idCategoria) REFERENCES projetoTP5.Categorias(idCategoria)
);
GO
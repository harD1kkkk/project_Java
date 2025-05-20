USE java;
DROP TABLE IF EXISTS Orders, Products, Category, Users, Roles;

CREATE TABLE Roles (
                       id   INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE Users (
                       id      INT AUTO_INCREMENT PRIMARY KEY,
                       name    VARCHAR(255) NOT NULL,
                       email   VARCHAR(255) NOT NULL UNIQUE,
                       role_id INT NOT NULL,
                       FOREIGN KEY (role_id) REFERENCES Roles(id)
);

CREATE TABLE Category (
                          id   INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE Products (
                          id          INT AUTO_INCREMENT PRIMARY KEY,
                          name        VARCHAR(255) NOT NULL,
                          price       DOUBLE NOT NULL,
                          category_id INT NOT NULL,
                          FOREIGN KEY (category_id) REFERENCES Category(id)
);

CREATE TABLE Orders (
                        id         INT AUTO_INCREMENT PRIMARY KEY,
                        created_at DATETIME NOT NULL,
                        user_id    INT NOT NULL,
                        product_id INT NOT NULL,
                        quantity   INT NOT NULL,
                        FOREIGN KEY (user_id)    REFERENCES Users(id),
                        FOREIGN KEY (product_id) REFERENCES Products(id)
);

INSERT INTO Roles (name) VALUES ('Admin'), ('User');
# INSERT INTO Category (name) VALUES ('Electronics'), ('Books');
# INSERT INTO Products (name,price,category_id) VALUES
#                                                   ('Laptop', 1200.0, 1),
#                                                   ('Spring in Action', 45.0, 2);
#
# INSERT INTO Users (name,email,role_id) VALUES
#                                            ('John','john@gmail.com',1),
#                                            ('Jane','jane@gmail.com',2);

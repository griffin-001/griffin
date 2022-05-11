CREATE TABLE if not exists MAP (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    dependency_id INT NOT NULL
);

CREATE TABLE if not exists Project(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    status INT NOT NULL
);

CREATE TABLE if not exists Dependency(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    category Int NOT NULL,
    status INT NOT NULL
);
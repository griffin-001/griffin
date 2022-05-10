CREATE TABLE if not exists MAP (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    dependency_id INT NOT NULL
);
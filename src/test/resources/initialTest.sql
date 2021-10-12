CREATE TABLE IF NOT EXISTS tags(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS certificates(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(200) NOT NULL,
    price DOUBLE NOT NULL,
    duration INT NOT NULL,
    create_date TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS certificates_tags(
    certificate_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (certificate_id) REFERENCES certificates(id) ON DELETE CASCADE
);
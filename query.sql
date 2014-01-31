CREATE DATABASE `new_schema` /*!40100 DEFAULT CHARACTER SET utf8 */;
CREATE TABLE `book` (
  `idbook` int(11) NOT NULL AUTO_INCREMENT,
  `gen` varchar(45) DEFAULT NULL,
  `isbn` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `urlString` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idbook`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
INSERT INTO `new_schema`.`book`
(`idbook`,
`gen`,
`isbn`,
`title`,
`urlString`)
VALUES
(1,
"SF",
9873456733456,
"title",
"http://localhost:8080/RESTfull/rest/books/1");

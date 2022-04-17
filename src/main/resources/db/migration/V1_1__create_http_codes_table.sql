
--@author limit (Yurii Chukhrai)

DROP TABLE IF EXISTS http_code;
CREATE TABLE http_code (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code SMALLINT NOT NULL,
  category VARCHAR(20) NOT NULL,
  reason_phrase VARCHAR(50) NOT NULL,
  definition VARCHAR(1000)
);

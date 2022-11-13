docker run --name lab-7-ds -e MYSQL_ROOT_PASSWORD=root -p3306:3306 -d mysql
docker run -d --name lab-8-rabbitmq -p5672:5672 rabbitmq:3
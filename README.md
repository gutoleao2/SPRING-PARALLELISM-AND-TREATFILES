# SPRING-PARALLELISM-AND-TREATFILES

### Spring Boot upload, create and manipulate files and directory's. And, work with parallel stream to have a good performance

### Build API

```
* Run Spring Boot application mvn spring-boot:run
* Ou - No diretório do projeto, Execute o comando : mvn clean package -DskipTests (Um .jar será gerado na pasta ./target)
```

### Test Spring Boot

* POST - Upload and send > http://localhost:8080/federal-revenue/upload/update-accounts.
  * Body -> ./big-account-list.csv
* GET - work directory List files >  http://localhost:8080/federal-revenue/files.

```
# É possível realizar testes dos endpoints chamando via POSTMAN.
# IMPORTE A COLLECTION :

{"info":{"_postman_id":"07f7968b-9db8-44e4-9425-f2a5a984ce8c","name":"UploadAndUpdateFiles","schema":"https://schema.getpostman.com/json/collection/v2.1.0/collection.json"}
    ,"item":[
        {"name":"upload-and-update","request":{"method":"POST","header":[]
            ,"body":{"mode":"formdata","formdata":[{"key":"file","type":"file","src":"/C:/Users/.../filestotest/arquivo.csv"}]}
            ,"url":{"raw":"http://localhost:8080/federal-revenue/upload/update-accounts","protocol":"http","host":["localhost"],"port":"8080","path":["federal-revenue","upload","update-accounts"]}}
            ,"response":[]}
        ,{"name":"list-files","request":{"method":"GET","header":[],"url":{"raw":"http://localhost:8080/federal-revenue/files","protocol":"http","host":["localhost"],"port":"8080","path":["federal-revenue","files"]}}
        ,"response":[]}
    ]
}
```

### SWAGGER

Acesse :  http://localhost:8080/swagger-ui.html

```
* Ele foi adicionado ao projeto com intuito de trazer uma brevíssima documentação e testar a aplicação
* Acesse o link acima e execute os teste com auxilio da interface

PORT=8080
```


# 啟動說明

### Docker 啟動 postgres Database

```shell
docker pull postgres
docker run -d --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=123456 postgres

docker exec -it postgres /bin/bash ## 進入 postgres container

psql -U postgres ## 使用 postgres 登入

create database demo;  ## 新建 database
\c demo;  ## 切換 database

##### 建 table & 寫入初始資料(如下)

```
```sql
----- 建表 SQL
create table systemUser
(
    _id UUID DEFAULT gen_random_uuid() NOT NULL,
    account  VARCHAR(128)              NOT NULL,
    password VARCHAR(128),
    name     VARCHAR(128),
    PRIMARY KEY (_id),
    UNIQUE (account)
);



create table goods
(
    _id UUID              DEFAULT gen_random_uuid() NOT NULL,
    name        VARCHAR(128)                        NOT NULL,
    cr_user     UUID                                NOT NULL,
    cr_datetime TIMESTAMP DEFAULT CURRENT_DATE,
    up_user     UUID                                NOT NULL,
    up_datetime TIMESTAMP DEFAULT CURRENT_DATE,
    PRIMARY KEY (_id),
    FOREIGN KEY (cr_user) REFERENCES systemUser (_id),
    FOREIGN KEY (up_user) REFERENCES systemUser (_id)
);

--- initial data
INSERT INTO systemuser (_id, account, password, name)
VALUES ('42001b99-2b9d-488b-8cf4-598d689dd549', 'david', '$2a$10$ZzNg0UQqrZU8eGfxMzmcqOccDJP.XoC78ppDd0CN9KxonyeFod2TO',
        'david');
INSERT INTO systemuser (_id, account, password, name)
VALUES ('dca3c9a8-5040-4f62-bf8f-80b810209803', 'jack', '$2a$10$lJMkJNkX9NKwPGOVHVAGgOmD1VOt9X2hOYYXk0lnWxyGoVUXY0C/2',
        'jack');
INSERT INTO systemuser (_id, account, password, name)
VALUES ('b17726db-a995-48ec-a134-54db0924e1fa', 'mike', '$2a$10$O0DjbfjDNhSkTrFR2kWk7OtQ/Eeu/.G8SL8T0fWWlKsxS6xQPok5u',
        'mike');



INSERT INTO goods (_id, name, cr_user, cr_datetime, up_user, up_datetime)
VALUES ('910c8eb0-f45b-4926-93eb-ca3e823c4888', '鳳梨', 'b17726db-a995-48ec-a134-54db0924e1fa',
        '2024-03-10 13:30:52.843000', 'b17726db-a995-48ec-a134-54db0924e1fa', '2024-03-10 13:33:15.136000');
INSERT INTO goods (_id, name, cr_user, cr_datetime, up_user, up_datetime)
VALUES ('6feb2de9-cc3c-4dca-b276-00e2e1306d40', '橘子', 'dca3c9a8-5040-4f62-bf8f-80b810209803',
        '2024-03-10 13:33:59.334000', 'dca3c9a8-5040-4f62-bf8f-80b810209803', '2024-03-10 13:33:59.334000');
INSERT INTO goods (_id, name, cr_user, cr_datetime, up_user, up_datetime)
VALUES ('b099110e-1eda-416a-9c9d-17f5c8897f63', '葡萄', 'dca3c9a8-5040-4f62-bf8f-80b810209803',
        '2024-03-10 13:33:55.419000', 'b17726db-a995-48ec-a134-54db0924e1fa', '2024-03-10 13:35:22.804000');

```
```shell
--- 專案打包 jar
### 切到專案目錄下
mvn clean install -DskipTests
cd target
java -jar demo-0.0.1-SNAPSHOT.jar
```
### API 文件路徑
```text
Swagger UI API Path
http://localhost:8888/swagger-ui/index.html#
```
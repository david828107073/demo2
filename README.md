# 啟動說明


### Docker 啟動 postgres Database

```shell
docker pull postgres
docker run -d --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=123456 postgres

```
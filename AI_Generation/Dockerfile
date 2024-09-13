# 使用官方的Python镜像
FROM python:3.10

# 设置工作目录
WORKDIR /app

# 将当前目录下的所有文件复制到容器的工作目录
COPY . /app

# 安装项目的Python依赖
RUN pip3 install --no-cache-dir -r requirements.txt

# 将 .env 文件复制到 Docker 容器中 /app/src/model 目录
COPY src/model/.env /app/src/model/.env


# 暴露端口（如果有需要）
EXPOSE 5000

# 使用 ENTRYPOINT，允许传入命令行参数
ENTRYPOINT ["python3"]

## 使用 CMD，允许传入命令行参数
#CMD ["python3"]
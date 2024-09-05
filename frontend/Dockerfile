# Этап 1: Сборка фронтенд-приложения
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Этап 2: Создание финального образа
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html

# Этап 1: Сборка фронтенд-приложения
FROM node:18 AS build
WORKDIR /app

# Копируем package.json и package-lock.json для установки зависимостей
COPY package*.json ./

# Устанавливаем зависимости
RUN npm ci

# Копируем остальные файлы проекта
COPY . .

# Собираем проект
RUN npm run build

# Этап 2: Создание финального образа
FROM nginx:alpine

# Копируем сгенерированные файлы сборки из предыдущего этапа
COPY --from=build /app/dist /usr/share/nginx/html

# Копируем файл конфигурации nginx, если необходимо
# COPY nginx.conf /etc/nginx/nginx.conf

# Запускаем Nginx
CMD ["nginx", "-g", "daemon off;"]

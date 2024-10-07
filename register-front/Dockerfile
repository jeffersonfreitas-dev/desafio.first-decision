FROM node:20-alpine AS builder
WORKDIR /app
COPY . .
RUN npm config set strict-ssl false && \
    npm install && \
    npm run build


FROM nginx:alpine
COPY --from=builder /app/dist/register-front/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
COPY mime.types /etc/nginx/mime.types
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]



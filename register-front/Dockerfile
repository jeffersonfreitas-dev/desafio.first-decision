FROM node:20-alpine AS builder
WORKDIR /app
COPY . .
RUN npm install && \
    npm install @angular/cli && \
    npm run build


FROM nginx:alpine
COPY --from=builder /app/dist/register-front/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
COPY mime.types /etc/nginx/mime.types
EXPOSE 4200
CMD ["nginx", "-g", "daemon off;"]



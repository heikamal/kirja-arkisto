FROM node:21-alpine3.18 as build

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN npm install -g @angular/cli

RUN npm install

# Build the Angular app for production
RUN npm run build

# Use a smaller, production-ready image as the final image
FROM nginx:latest

# Copy the Nginx config file
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the production-ready Angular app to the Nginx webserver's root directory
COPY --from=build /usr/src/app/dist/app/browser /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
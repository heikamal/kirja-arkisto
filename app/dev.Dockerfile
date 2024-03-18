FROM node:21-alpine3.19

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN npm install -g @angular/cli

RUN npm install

EXPOSE 4200 49153

CMD ["ng", "serve", "--host", "0.0.0.0", "--poll", "500"]
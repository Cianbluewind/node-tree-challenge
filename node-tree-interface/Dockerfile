FROM node:latest as build

WORKDIR /app
ENV PATH /app/node_modules/ .bin:$PATH
COPY package.json /app/package.json
COPY package-lock.json /app/package-lock.json
RUN npm install -g @angular/cli
RUN npm install --force
COPY . .
RUN ng build --configuration production --output-path=/dist

FROM nginx:latest
COPY --from=build /dist /usr/share/nginx/html
CMD ["/bin/sh",  "-c",  "envsubst < /usr/share/nginx/html/assets/env.template.js > /usr/share/nginx/html/assets/env.js && exec nginx -g 'daemon off;'"]

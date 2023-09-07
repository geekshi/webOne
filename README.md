# A web project demonstrating the frontend and backend technology stack
Frontend technology stack includes HTML, CSS, JavaScript, Vue.js. Backend technology stack includes SpringBoot, Mybatis, MySQL, RabbitMQ, Redis. The frontend and backend communication uses two modes: PULL and PUSH, the PULL mode is based on REST, and the PUSH mode is based on WebSocket.

## Overview
Vue.js calls the API and the API first checks the data from the cache. If the data is not in the cache, it then queries the database and updates the cache.

When the producer publishes the new data and sends to the MQ, the consumer receives the data and pushes the data to Vue.js, and updates the data to the database simultaneously.
![Overview](/doc/overview.png)

## Vue page

![image](/doc/page.GIF)

import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
const dataContainer = document.getElementById("data-container");
const stompClient = new Client({
    webSocketFactory: () => new SockJS('http://localhost:9090/websocket'),
    debug: (str) => console.log(str),
    reconnectDelay: 5000,
    connectionTimeout: 10000
});
stompClient.onConnect = () => {
    console.log("Connected to websocket");
    stompClient.subscribe("/topic/sensor-data", (data) => {
        const keyValue = data.body.split(":");
        const sensorId = keyValue[0];
        const dataVal = keyValue[1];
        const createHeaders = document.createElement("h1");
        createHeaders.textContent = `The id is : ${sensorId} \n The value is : ${dataVal}`;
        if (dataContainer) {
            dataContainer.appendChild(createHeaders);
        }
    });
};
stompClient.activate();

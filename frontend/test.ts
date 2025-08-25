import SockJS from 'sockjs-client';

import { Client } from '@stomp/stompjs';


const stompClient = new Client({
    webSocketFactory: () => new SockJS('http://localhost:9090/websocket'),
    debug: (str) => console.log(str),
    reconnectDelay: 5000,
});


stompClient.onConnect = () => {
    console.log('Connected to WebSocket');

    // Subscribe to a topic
    stompClient.subscribe('/topic/sensor-data', (message) => {
        console.log('Received:', message.body);
    });

    // Send a message to the backend
    stompClient.publish({
        destination: '/app/sendMessage',
        body: JSON.stringify({ text: 'Hello from frontend!' }),
    });
};

// Activate connection
stompClient.activate();

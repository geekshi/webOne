<template>
    <div>
        <h2>PUSH MODE</h2>
        <p>Name: HSBC</p>
        <p>Code: 004</p>
        <p v-if="upOrDown == 'up'">Price: <span style="color: green;">{{price}}</span></p>
        <p v-else-if="upOrDown == 'down'">Price: <span style="color: red;">{{price}}</span></p>
        <p v-else>Price: {{price}}</p>
    </div>
</template>

<script>
    export default {
        name:'StockPrice',
        data() {
            return {
                price: '',
                upOrDown: ''
            }
        },
        mounted() {
            if('WebSocket' in window) {
                this.websocket = new WebSocket('ws://localhost:8081/push/websocket/100')
                this.initWebSocket()
            } else {
                alert('Current browser does not support')
            }
        },
        beforeDestroy() {
            this.onbeforeunload()
        },
        methods: {
            initWebSocket () {
                this.websocket.onerror = this.setErrorMessage
                this.websocket.onopen = this.setOnopenMessage
                this.websocket.onmessage = this.setOnmessageMessage
                this.websocket.onclose = this.setOncloseMessage
                window.onbeforeunload = this.onbeforeunload
            },
            setErrorMessage () {
                console.log('WebSocket connection error, status code: ' + this.websocket.readyState)
            },
            setOnopenMessage () {
                console.log('WebSocket connection succeeded, status code: ' + this.websocket.readyState)
            },
            setOnmessageMessage (event) {
                if(this.price < event.data) {
                    this.upOrDown = 'up'
                }else if(this.price > event.data) {
                    this.upOrDown = 'down'
                }
                this.price = event.data
                console.log('server return: ' + event.data)
            },
            setOncloseMessage () {
                console.log('WebSocket connection closed, status code: ' + this.websocket.readyState)
            },
            onbeforeunload () {
                this.closeWebSocket()
            },
            closeWebSocket () {
                this.websocket.close()
            }
        }
    }
</script>
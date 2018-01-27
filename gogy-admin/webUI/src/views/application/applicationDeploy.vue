<template>
    <div>
        <div>{{ message }}</div>
        <Button v-on:click="send" type="primary">send Message</Button>
    </div>

</template>

<script>
    export default {
        data() {
            return {
                message: "test",
                invokeIdCnt: 1
            }
        },
        stompClient: {
            monitorIntervalTime: 100,
            stompReconnect: false,
            timeoutCallback: (orgCmd) => {
                console.log("orgCmd ==> "+orgCmd);
            }
        },
        created () {
            this.connectSrv();
        },
        methods: {
            onConnected(frame){
                console.log(frame);
                this.message = "connected";
                this.$stompClient.subscribe('/topic/resend', this.responseCallback, this.onFailed);
            },
            onFailed(frame){
                this.message = "failed";
                console.log(frame);
            },
            connectSrv(){
                var headers = {
                    "login": 'guest',
                    "passcode": 'guest',
                    // additional header

                };
                this.connetWM("http://192.168.101.14:8880/api/gogy", headers, this.onConnected, this.onFailed);
            },
            getInvokeId(){
                let hex = (this.invokeIdCnt++ ).toString(16);
                var zero = '0000';
                var tmp  = 4-hex.length;
                return zero.substr(0,tmp) + hex;
            },
            send(){
                let destination = '/api/websocket/test'
                let invokeId = this.getInvokeId();

                let body = "head - " + invokeId + " yuanyi";
                console.log("start sending message");
                this.sendWM(destination, body, invokeId, this.responseCallback, 3000);
            },
            responseCallback(frame){
                console.log("responseCallback msg=>");
                console.log(frame);
                this.message = JSON.parse(frame.body).message;

                //必须获取到invokeId 移除监视，否则有内存泄漏风险
                let invokeId = this.message.substr(14, 4);
                console.log(invokeId);
                this.removeStompMonitor(invokeId);
            },
            disconnect() {
                this.message = "disconnect";
                this.disconnetWM();
            }
        }
    };
</script>
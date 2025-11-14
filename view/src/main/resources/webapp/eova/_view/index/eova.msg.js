const {createApp, ref, reactive, watch, computed, onMounted, nextTick} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

let eventSource;

window.onload = function () {
    startSSE()
}

function startSSE() {

    // 初始化EventSource
    eventSource = new EventSource('/sse');


    eventSource.addEventListener('error', function (event) {
        console.log('关闭:', event);
        // 这里不关闭时，浏览器可能会重新连接SSE
        eventSource.close();
    });

    eventSource.addEventListener('msg', function (event) {
        console.log('收到消息:', event);
        var o = JSON.parse(event.data);
        // addText(kv.name, kv.msg, kv.sendALL);
        addMessage(o)
    });

    console.log("eova msg sse init...")
}

function stopSSE() {
    if (eventSource) {
        eventSource.close();
        addMessage("手动关闭连接");
    }
}


function addMessage(o) {
    // const messages = document.getElementById('messages');
    // messages.innerHTML += `<p>${new Date().toLocaleTimeString()}: ${text}</p>`;
    // messages.scrollTop = messages.scrollHeight; // 自动滚动到底部

    me.layer.notify(o.title, o.info, o.type)
}
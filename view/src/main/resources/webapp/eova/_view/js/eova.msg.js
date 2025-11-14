const {createApp, ref, reactive, watch, computed, onMounted, nextTick} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

let eventSource;

window.onload = function () {
    startSSE()
}

function startSSE() {

    eventSource = new EventSource('/sse');
    let reconn = 0;
    eventSource.addEventListener('error', function (event) {
        if (eventSource.readyState === EventSource.CLOSED) {
            console.log('SSE服务连接已关闭，可能是网络异常或服务端关闭');
            // 可以选择延迟重连
            console.log('3秒后将尝试重连SSE服务');
            setTimeout(startSSE, 3000);
        } else if (eventSource.readyState === EventSource.CONNECTING) {
            console.log('SSE正在连接或重试连接...');
            reconn++;
            if (reconn > 5) {
                console.log('SSE重连次数过多, 请确认Nginx SSE配置是否正常...');
                eventSource.close();
            }
        } else if (eventSource.readyState === EventSource.OPEN) {
            console.log('SSE服务连接已创建');
        } else {
            console.log('未知的服务连接状态:', error);
        }
    });
    eventSource.addEventListener('msg', function (event) {
        console.log('收到消息:', event);
        var o = JSON.parse(event.data);
        // addText(kv.name, kv.msg, kv.sendALL);
        addMessage(o)
    });
}

function stopSSE() {
    if (eventSource) {
        eventSource.close();
        addMessage("手动关闭连接");
    }
}

function addMessage(o) {
    me.layer.notify(o.title || '系统消息', o.info, o.type)
}
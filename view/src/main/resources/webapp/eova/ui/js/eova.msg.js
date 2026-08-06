const {createApp, ref, reactive, watch, computed, onMounted, nextTick} = Vue; // 使用全局 Vue 对象
const {me} = EovaUI
const {x} = EovaTools

let eventSource = null;
let reconnectTimer = null;
let manualClose = false;
let retryCount = 0;
const MAX_RETRY_BEFORE_BACKOFF = 5;

function clearReconnectTimer() {
    if (reconnectTimer) {
        clearTimeout(reconnectTimer);
        reconnectTimer = null;
    }
}

function scheduleReconnect() {
    clearReconnectTimer();
    // 指数退避，最长 30s
    const delay = Math.min(30000, 2000 * Math.pow(1.5, Math.min(retryCount, 8)));
    console.log(`SSE 将在 ${Math.round(delay / 1000)}s 后重连 (第 ${retryCount} 次)`);
    reconnectTimer = setTimeout(startSSE, delay);
}

function startSSE() {
    manualClose = false;
    clearReconnectTimer();

    // 先关掉旧实例，避免多 EventSource 互踢
    if (eventSource) {
        try {
            eventSource.close();
        } catch (e) {
        }
        eventSource = null;
    }

    eventSource = new EventSource('/sse');

    eventSource.addEventListener('open', function () {
        retryCount = 0;
        console.log('SSE 连接成功');
    });

    eventSource.addEventListener('error', function () {
        if (manualClose) {
            return;
        }

        // 主动 close，禁用浏览器自带自动重连，只走我们自己的单一重连路径
        // （否则会与 scheduleReconnect / 新 EventSource 叠成两次 /sse）
        const es = eventSource;
        if (es) {
            es.close();
        }
        eventSource = null;

        retryCount++;
        if (retryCount > MAX_RETRY_BEFORE_BACKOFF) {
            console.log('SSE 重连次数较多，请确认 Nginx 已关闭 proxy_buffering 且 proxy_read_timeout 足够大');
        }
        scheduleReconnect();
    });

    eventSource.addEventListener('msg', function (event) {
        try {
            var o = JSON.parse(event.data);
            addMessage(o);
        } catch (e) {
            console.warn('SSE 消息解析失败:', e, event.data);
        }
    });
}

function stopSSE() {
    manualClose = true;
    clearReconnectTimer();
    retryCount = 0;
    if (eventSource) {
        eventSource.close();
        eventSource = null;
    }
}

function addMessage(o) {
    me.layer.notify(o.title || '系统消息', o.info, o.type)
}

// module 脚本可能晚于 window.load，需兼容
function initSSE() {
    startSSE();
}
if (document.readyState === 'complete') {
    initSSE();
} else {
    window.addEventListener('load', initSSE);
}

// 页面隐藏时不断开（消息仍可收）；卸载时清理，避免 bfcache/重复进入叠连接
window.addEventListener('beforeunload', function () {
    stopSSE();
});

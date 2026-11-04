/**
 * 复制文本到剪贴板（优先 Clipboard API，失败回退 execCommand）
 *
 * @param {string} text 文本内容
 * @param {string} [okMsg='已复制'] 成功提示；传空字符串则不弹提示
 * @returns {Promise<boolean>}
 */
function copy(text, okMsg) {
    if (okMsg === undefined) {
        okMsg = '已复制'
    }
    text = text == null ? '' : String(text)

    function tip(ok) {
        if (ok && okMsg) {
            alert(okMsg)
        }
        return !!ok
    }

    function fallback() {
        var ta = document.createElement('textarea')
        ta.value = text
        ta.setAttribute('readonly', '')
        ta.style.cssText = 'position:fixed;left:-9999px;top:0'
        document.body.appendChild(ta)
        ta.focus()
        ta.select()
        var ok = false
        try {
            ok = document.execCommand('copy')
        } catch (e) {
            ok = false
        }
        document.body.removeChild(ta)
        return tip(ok)
    }

    if (navigator.clipboard && typeof navigator.clipboard.writeText === 'function' && window.isSecureContext) {
        return navigator.clipboard.writeText(text).then(function () {
            return tip(true)
        }).catch(function () {
            return fallback()
        })
    }
    return Promise.resolve(fallback())
}

/**
 * 按元素 ID 复制文本（优先 innerText，其次 value）
 *
 * @param {string} elId
 * @param {string} [okMsg='已复制']
 * @returns {Promise<boolean>}
 */
function copyById(elId, okMsg) {
    var el = document.getElementById(elId)
    if (!el) {
        return Promise.resolve(false)
    }
    var text = el.innerText != null && el.innerText !== '' ? el.innerText : (el.value || '')
    return copy(text, okMsg)
}

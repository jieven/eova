/**
 * “虫洞”是连接Web宇宙区域间的时空通道。
 * @type {{app: {}, vue: {}, page: {}}}
 * @app Vue App 模版作用域
 * @vue Vue 全局作用域
 * @page Web 页面, JS全局作用域
 */
window.uzoo = {
    page: {},
    vue: {},
    app: {}
}

/**
 * EovaMeta 前端公共工具
 */
window.EovaMeta = {
    /**
     * 懒加载单例：factory 只执行一次，并发调用共用同一个 Promise。
     * factory 应返回（或 resolve）带 show(...args) 的对象；之后每次调用走 show。
     * @param {() => object|Promise<object>} factory
     * @returns {(...args: any[]) => Promise<any>}
     */
    single(factory) {
        let api = null
        let pending = null
        return async (...args) => {
            if (!api) {
                if (!pending) {
                    pending = Promise.resolve()
                        .then(() => factory())
                        .then((a) => {
                            api = a
                            pending = null
                            return api
                        })
                }
                await pending
            }
            if (api && typeof api.show === 'function') {
                return api.show(...args)
            }
            return api
        }
    },

    /**
     * 获取/创建旁挂挂载点（body 下 div），供 app.mount(dom(id))
     * @param {string} id
     * @returns {HTMLElement}
     */
    dom(id) {
        if (!id) throw new Error('EovaMeta.dom(id) 需要 id')
        let el = document.getElementById(id)
        if (!el) {
            el = document.createElement('div')
            el.id = id
            document.body.appendChild(el)
        }
        return el
    },

    /**
     * 按后缀加载资源
     * - .css：按需注入 stylesheet（同 href 只一次）
     * - 其它（如 .vue）：fetch 文本，供 createApp({ template }) 使用
     * @param {string} url
     * @returns {Promise<string|void>}
     */
    async include(url) {
        if (!url) return

        const path = url.split('?')[0]
        const ext = path.includes('.') ? path.split('.').pop().toLowerCase() : ''

        if (ext === 'css') {
            if (document.querySelector(`link[href="${url}"]`)) return
            const link = document.createElement('link')
            link.rel = 'stylesheet'
            link.href = url
            document.head.appendChild(link)
            return
        }

        const res = await fetch(url)
        if (!res.ok) {
            throw new Error(`加载失败: ${url} (${res.status})`)
        }
        return res.text()
    },
}

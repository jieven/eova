const {createApp, ref, nextTick} = Vue
const {me} = EovaUI
const {include, dom, single} = EovaMeta

// 自定义资源
const ID = 'eova-menu-auth'
const TP = '/eova/_view/menu/auth/app.vue'
const CSS = '/eova/_view/menu/auth/app.css'

// 按类型分组
function groupByCat(array) {
    return array.reduce((acc, item) => {
        if (!acc[item.cat]) {
            acc[item.cat] = []
        }
        acc[item.cat].push(item)
        return acc
    }, {})
}

async function mount() {
    include(CSS)

    const visible = ref(false)
    const menuId = ref(0)
    const menu = ref(null)
    const btnCats = ref({})
    const roles = ref([])
    const auths = ref([])

    const applyChecked = () => {
        nextTick(() => {
            ;(auths.value || []).forEach((auth) => {
                const checkbox = document.getElementById(`CK_${auth.bid}_${auth.rid}`)
                if (checkbox) checkbox.checked = true
            })
        })
    }

    const loadData = (id) => {
        menuId.value = id
        menu.value = null
        btnCats.value = {}
        roles.value = []
        auths.value = []

        return axios
            .post('/menu/authData', {id})
            .then((res) => {
                const ret = res.data
                if (ret.state === 'ok') {
                    menu.value = ret.menu
                    btnCats.value = groupByCat(ret.btns || [])
                    roles.value = ret.roles || []
                    auths.value = ret.auths || []
                    applyChecked()
                } else {
                    me.layer.no(ret.msg)
                }
            })
            .catch((error) => {
                me.layer.msg('客户端请求异常: ' + error.message)
            })
    }

    const onAuth = (event, bid, rid) => {
        const status = event.target.checked
        axios
            .post('/auth/update', {
                is_check: status,
                bid,
                rid,
            })
            .then((res) => {
                const ret = res.data
                if (ret.state === 'ok') {
                    me.layer.msg('授权成功')
                } else {
                    me.layer.no(ret.msg)
                }
            })
            .catch((error) => {
                me.layer.msg('客户端请求异常: ' + error.message)
            })
    }

    const app = createApp({
        setup() {
            return {
                visible,
                menu,
                btnCats,
                roles,
                onAuth,
            }
        },
        template: await include(TP),
    })
    app.use(EovaUI)
    app.mount(dom(ID))

    return {
        show(id) {
            visible.value = true
            return loadData(id)
        },
    }
}

export const open = single(mount)

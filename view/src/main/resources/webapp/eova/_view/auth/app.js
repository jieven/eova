const {createApp, ref, reactive, computed, watch} = Vue
const {me} = EovaUI
const {include, dom, single} = EovaMeta

const ID = 'eova-field-auth'
const TP = '/eova/_view/auth/app.vue'

const urls = {
    save: '/auth/fieldSave',
}

const TYPE_ITEMS = [
    {val: 1, txt: '角色白名单'},
    {val: 2, txt: '角色黑名单'},
    {val: 3, txt: '用户白名单'},
    {val: 4, txt: '部门白名单'},
]

const TYPE_TIPS = {
    1: '未授权角色将屏蔽所选字段',
    2: '仅勾选的角色不可见所选字段',
    3: '未授权用户将屏蔽所选字段',
    4: '未授权部门将屏蔽所选字段',
}

const ORG_TREE_CONF = {
    id: 'id',
    pid: 'parent_id',
    title: 'name',
    spread: 'open',
    root: '0',
    icon: 'icon',
}

const AUTH_PLACEHOLDER = {
    1: '请选择角色（可多选）',
    2: '请选择角色（可多选）',
    3: '请选择用户（可多选）',
    4: '请选择部门（可多选）',
}

const AUTH_TARGET_TIP = {
    1: '请至少选择一个角色',
    2: '请至少选择一个角色',
    3: '请至少选择一个用户',
    4: '请至少选择一个部门',
}

const emptyForm = () => ({
    type: 1,
    field: '',
    auth: '',
    authOrg: [],
})

const resolveAuth = (form, isOrg) => {
    if (isOrg) {
        const arr = Array.isArray(form.authOrg) ? form.authOrg : []
        return arr.join(',')
    }
    return form.auth || ''
}

async function mount() {
    const visible = ref(false)
    const objectCode = ref('')
    const form = reactive(emptyForm())
    const saving = ref(false)
    const typeItems = TYPE_ITEMS

    const isUserAuth = computed(() => Number(form.type) === 3)
    const isOrgAuth = computed(() => Number(form.type) === 4)
    const treeConf = ORG_TREE_CONF
    const authPlaceholder = computed(() => AUTH_PLACEHOLDER[Number(form.type)] || '请选择')
    const currentTypeTip = computed(() => TYPE_TIPS[Number(form.type)] || '')
    const fieldOption = computed(() => (objectCode.value ? `eova_field;${objectCode.value}` : ''))

    const resetForm = () => {
        form.type = 1
        form.field = ''
        form.auth = ''
        form.authOrg = []
    }

    const onTypeChange = () => {
        form.auth = ''
        form.authOrg = []
    }

    const saveRule = () => {
        if (!objectCode.value) {
            me.layer.msg('请先选择元对象')
            return
        }
        if (!form.field) {
            me.layer.msg('请至少选择一个字段')
            return
        }
        const auth = resolveAuth(form, isOrgAuth.value)
        if (!auth) {
            me.layer.msg(AUTH_TARGET_TIP[Number(form.type)] || '请选择授权目标')
            return
        }
        saving.value = true
        axios
            .post(urls.save, {
                object: objectCode.value,
                field: form.field,
                type: Number(form.type),
                auth,
            }, {
                headers: {'Content-Type': 'application/json'},
            })
            .then((res) => {
                const ret = res.data
                if (ret.state !== 'ok') {
                    me.layer.no(ret.msg || '保存失败')
                    return
                }
                me.layer.ok(ret.msg || '保存成功')
                resetForm()
                visible.value = false
            })
            .catch((e) => {
                me.layer.msg('保存异常: ' + (e.message || e))
            })
            .finally(() => {
                saving.value = false
            })
    }

    watch(objectCode, () => {
        form.field = ''
        form.auth = ''
        form.authOrg = []
        form.type = 1
    })

    const app = createApp({
        setup() {
            return {
                visible,
                objectCode,
                form,
                saving,
                typeItems,
                isUserAuth,
                isOrgAuth,
                treeConf,
                authPlaceholder,
                currentTypeTip,
                fieldOption,
                onTypeChange,
                saveRule,
            }
        },
        template: await include(TP),
    })
    app.use(EovaUI)
    app.mount(dom(ID))

    return {
        show(object) {
            resetForm()
            visible.value = true
            objectCode.value = object || ''
        },
    }
}

export const open = single(mount)

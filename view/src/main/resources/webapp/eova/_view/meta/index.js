const {createApp, ref, reactive, computed, onMounted} = Vue
const {me} = EovaUI

/** 页面分组顺序（与后端 MetaUtil.META_TABLES 对齐） */
const TABLE_META = [
    {table: 'eova_object', label: '元对象', ds: 'eova'},
    {table: 'eova_field', label: '元字段', ds: 'eova'},
    {table: 'eova_field_diy', label: '元字段个性化', ds: 'eova'},
    {table: 'eova_option', label: '表达式', ds: 'eova'},
    {table: 'eova_menu', label: '菜单', ds: 'eova'},
    {table: 'eova_button', label: '按钮', ds: 'eova'},
    {table: 'dicts', label: '业务字典', ds: 'main'},
]

/** 行真实表名（默认与分组一致） */
const rowTable = (g, row) => row.table || g.table

/** 空分组骨架（接口无数据的表也占位，便于统计卡片稳定） */
const emptyGroups = () =>
    TABLE_META.map((t) => ({table: t.table, label: t.label, rows: []}))

const app = createApp({
    setup() {
        const groups = ref(emptyGroups())
        const versions = ref([])
        const selectedVer = ref('')
        const filterTable = ref('')
        const openMap = reactive({})
        const checked = reactive({})
        const loading = ref(false)

        const keyOf = (table, id) => `${table}#${id}`
        const rowKey = (g, row) => keyOf(rowTable(g, row), row.id)

        const currentRange = computed(() => {
            if (!selectedVer.value) return {start: '', end: ''}
            const v = versions.value.find((it) => it.ver === selectedVer.value)
            return v ? {start: v.start, end: v.end} : {start: '', end: ''}
        })

        const isHistory = computed(() => !!selectedVer.value)

        const pendingTotal = computed(() =>
            groups.value.reduce((n, g) => n + g.rows.length, 0)
        )

        const visibleGroups = computed(() => {
            if (!filterTable.value) return groups.value.filter((g) => g.rows.length)
            return groups.value.filter((g) => g.table === filterTable.value && g.rows.length)
        })

        const visiblePendingTotal = computed(() =>
            visibleGroups.value.reduce((n, g) => n + g.rows.length, 0)
        )

        const checkedTotal = computed(() =>
            Object.keys(checked).filter((k) => checked[k]).length
        )

        const applyGroups = (list) => {
            const map = {}
            ;(list || []).forEach((g) => {
                map[g.table] = g
            })
            groups.value = TABLE_META.map((t) => {
                const g = map[t.table]
                return {
                    table: t.table,
                    label: (g && g.label) || t.label,
                    rows: (g && g.rows) || [],
                }
            })
            Object.keys(checked).forEach((k) => delete checked[k])
            groups.value.forEach((g) => {
                if (openMap[g.table] === undefined) openMap[g.table] = true
                g.rows.forEach((r) => {
                    checked[rowKey(g, r)] = true
                })
            })
        }

        const loadVersions = () => {
            return axios
                .get('/meta/versions', {params: {limit: 5}})
                .then((res) => {
                    const ret = res.data
                    if (ret.state !== 'ok') {
                        me.layer.no(ret.msg || '加载历史版本失败')
                        return
                    }
                    versions.value = ret.versions || []
                })
                .catch((e) => {
                    me.layer.msg('加载历史版本异常: ' + (e.message || e))
                })
        }

        const buildUpdatedParams = () => {
            if (!selectedVer.value) return {}
            const {start, end} = currentRange.value
            if (!start || !end) return {}
            return {start, end}
        }

        const loadPending = (showOk) => {
            loading.value = true
            return axios
                .get('/meta/updated', {params: buildUpdatedParams()})
                .then((res) => {
                    const ret = res.data
                    if (ret.state !== 'ok') {
                        me.layer.no(ret.msg || '加载元数据失败')
                        return
                    }
                    applyGroups(ret.groups)
                    if (showOk) me.layer.ok('已刷新')
                })
                .catch((e) => {
                    me.layer.msg('请求异常: ' + (e.message || e))
                })
                .finally(() => {
                    loading.value = false
                })
        }

        const reload = () => loadPending(true)

        /** yyyyMMddHHmmss → MM-dd HH:mm */
        const formatVer = (ver) => {
            if (!ver || ver.length < 14) return ver || ''
            return `${ver.slice(4, 6)}-${ver.slice(6, 8)} ${ver.slice(8, 10)}:${ver.slice(10, 12)}`
        }

        /** 时间窗短展示：取 end 的月日时分 */
        const formatRange = (v) => {
            const end = (v && v.end) || ''
            if (end.length >= 16) return `至 ${end.slice(5, 16)}`
            return end
        }

        const selectVer = (ver) => {
            if (selectedVer.value === ver) return
            selectedVer.value = ver || ''
            filterTable.value = ''
            loadPending(false)
        }

        const toggleGroup = (table) => {
            openMap[table] = !openMap[table]
        }

        const isGroupAllChecked = (g) =>
            g.rows.length > 0 && g.rows.every((r) => checked[rowKey(g, r)])

        const setGroupIndeterminate = (el, g) => {
            if (!el) return
            const n = g.rows.filter((r) => checked[rowKey(g, r)]).length
            el.indeterminate = n > 0 && n < g.rows.length
        }

        const toggleGroupCheck = (g) => {
            const all = isGroupAllChecked(g)
            g.rows.forEach((r) => {
                checked[rowKey(g, r)] = !all
            })
        }

        const checkAll = (val) => {
            visibleGroups.value.forEach((g) => {
                g.rows.forEach((r) => {
                    checked[rowKey(g, r)] = val
                })
            })
        }

        const collectChecked = () => {
            const list = []
            groups.value.forEach((g) => {
                g.rows.forEach((r) => {
                    if (checked[rowKey(g, r)]) {
                        list.push({table_name: rowTable(g, r), row_id: r.id, name: r.name})
                    }
                })
            })
            return list
        }

        const exportPending = () => {
            const items = collectChecked()
            if (!items.length) {
                me.layer.wa(isHistory.value ? '请先勾选要生成的数据' : '请先勾选待导出数据')
                return
            }
            const tip = isHistory.value
                ? `确认重新生成 SQL？共 ${items.length} 项（不写入版本）`
                : `确认导出元数据？共 ${items.length} 项`
            me.layer.confirm(tip, () => {
                const payload = {
                    items: items.map((it) => ({
                        table_name: it.table_name,
                        row_id: it.row_id,
                    })),
                    skip_version: isHistory.value,
                }
                if (isHistory.value) {
                    payload.start = currentRange.value.start
                    payload.end = currentRange.value.end
                }
                const w = window.open('about:blank', '_blank')
                axios
                    .post('/meta/export', payload, {
                        headers: {'Content-Type': 'application/json'},
                        responseType: 'text',
                    })
                    .then((res) => {
                        if (w) {
                            w.document.open()
                            w.document.write(res.data)
                            w.document.close()
                        }
                        if (!isHistory.value) {
                            loadVersions().then(() => loadPending(false))
                        }
                    })
                    .catch((e) => {
                        if (w) w.close()
                        me.layer.msg('导出失败: ' + (e.message || e))
                    })
            })
        }

        onMounted(() => {
            loadVersions().then(() => loadPending(false))
        })

        return {
            groups,
            versions,
            selectedVer,
            currentRange,
            isHistory,
            filterTable,
            openMap,
            checked,
            loading,
            pendingTotal,
            visibleGroups,
            visiblePendingTotal,
            checkedTotal,
            keyOf,
            rowKey,
            reload,
            formatVer,
            formatRange,
            selectVer,
            toggleGroup,
            isGroupAllChecked,
            setGroupIndeterminate,
            toggleGroupCheck,
            checkAll,
            exportPending,
        }
    },
})

app.use(EovaUI)
app.mount('#app')

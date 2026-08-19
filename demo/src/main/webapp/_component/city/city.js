/**
 * City — 省市多选
 *
 * 用法：
 *   <ev-city v-model="data.city_ids"></ev-city>
 *
 * 交互：框内回显已选城市，点击「选择」弹出省市选择界面，确定后写回。
 *
 * v-model 值格式（String / JSON）：
 *   [{"id":37,"name":"合肥","provinceId":3,"provinceName":"安徽"}, ...]
 *
 * 兼容回填：
 *   - JSON 对象数组（含 id）
 *   - JSON 数字数组：[37, 38]
 *   - 逗号分隔 id：37,38
 */
const {defineComponent, watch, ref, computed, reactive, onMounted, nextTick} = Vue;

const TEMP_GROUP_KEY = 'eova_city_select';
const AREA_URL = '/_component/city/area.json';

function loadTempGroupsFromCache() {
    try {
        const raw = localStorage.getItem(TEMP_GROUP_KEY);
        const list = raw ? JSON.parse(raw) : [];
        return Array.isArray(list) ? list : [];
    } catch {
        return [];
    }
}

function saveTempGroupsToCache(list) {
    localStorage.setItem(TEMP_GROUP_KEY, JSON.stringify(list));
}

/** 从 modelValue 解析出城市 id 列表 */
function parseModelValue(val) {
    if (val == null || val === '') return [];
    if (Array.isArray(val)) {
        return val
            .map((x) => (typeof x === 'object' && x != null ? Number(x.id) : Number(x)))
            .filter((id) => !Number.isNaN(id));
    }
    if (typeof val === 'number') return Number.isNaN(val) ? [] : [val];
    if (typeof val !== 'string') return [];

    const s = val.trim();
    if (!s) return [];

    try {
        const parsed = JSON.parse(s);
        return parseModelValue(parsed);
    } catch {
        return s
            .split(/[,，\s]+/)
            .map((x) => Number(x.trim()))
            .filter((id) => !Number.isNaN(id));
    }
}

/** 从 modelValue 尽量取出名称（未加载 area 时用于回显） */
function parseNameHints(val) {
    const map = {};
    let list = val;
    if (typeof val === 'string') {
        try {
            list = JSON.parse(val.trim());
        } catch {
            return map;
        }
    }
    if (!Array.isArray(list)) return map;
    list.forEach((x) => {
        if (x && typeof x === 'object' && x.id != null && x.name) {
            map[Number(x.id)] = x.name;
        }
    });
    return map;
}

function idsEqual(a, b) {
    if (a.length !== b.length) return false;
    const sa = [...a].sort((x, y) => x - y);
    const sb = [...b].sort((x, y) => x - y);
    return sa.every((id, i) => id === sb[i]);
}

export default defineComponent({
    name: 'City',
    props: {
        modelValue: {
            type: [String, Array],
            default: '',
        },
    },
    emits: ['update:modelValue'],
    setup(props, ctx) {
        const visible = ref(false);
        const loading = ref(true);
        const loadError = ref('');
        const provinces = ref([]);
        const citiesByProvince = reactive({});
        const cityMap = reactive({});
        const nameHints = ref({});
        const activeProvinceId = ref(null);
        const selectedIds = ref([]);
        const draftIds = ref([]);
        const tempGroups = ref(loadTempGroupsFromCache());
        const groupName = ref('');
        const activeGroupId = ref('');
        const toastText = ref('');
        let toastTimer = null;
        let syncingFromProps = false;
        let dataLoaded = false;

        const draftSet = computed(() => new Set(draftIds.value));

        const activeProvince = computed(() =>
            provinces.value.find((p) => p.id === activeProvinceId.value)
        );

        const activeCities = computed(
            () => citiesByProvince[activeProvinceId.value] || []
        );

        const selectedCountInActive = computed(
            () => activeCities.value.filter((c) => draftSet.value.has(c.id)).length
        );

        function resolveList(ids) {
            return ids
                .map((id) => {
                    if (cityMap[id]) return cityMap[id];
                    const name = nameHints.value[id];
                    if (name) {
                        return {id, name, provinceId: 0, provinceName: ''};
                    }
                    return {id, name: String(id), provinceId: 0, provinceName: ''};
                })
                .sort((a, b) => (a.provinceId || 0) - (b.provinceId || 0) || a.id - b.id);
        }

        const selectedList = computed(() => resolveList(selectedIds.value));
        const draftList = computed(() => resolveList(draftIds.value));

        const canSaveGroup = computed(
            () => !!groupName.value.trim() && draftList.value.length > 0
        );

        function emitValue(ids) {
            if (syncingFromProps) return;
            const payload = resolveList(ids).map((c) => ({
                id: c.id,
                name: cityMap[c.id]?.name || c.name,
                provinceId: cityMap[c.id]?.provinceId ?? c.provinceId,
                provinceName: cityMap[c.id]?.provinceName || c.provinceName,
            }));
            ctx.emit('update:modelValue', JSON.stringify(payload));
        }

        function applyIdsFromProps(val) {
            nameHints.value = {...nameHints.value, ...parseNameHints(val)};
            const ids = parseModelValue(val);
            if (idsEqual(ids, selectedIds.value)) return;
            syncingFromProps = true;
            selectedIds.value = [...new Set(ids)];
            nextTick(() => {
                syncingFromProps = false;
            });
        }

        function showToast(msg) {
            toastText.value = msg;
            clearTimeout(toastTimer);
            toastTimer = setTimeout(() => {
                toastText.value = '';
            }, 1800);
        }

        function provinceHasSelected(provinceId) {
            const cities = citiesByProvince[provinceId] || [];
            return cities.some((c) => draftSet.value.has(c.id));
        }

        function setDraft(ids) {
            draftIds.value = [...new Set(ids)];
            activeGroupId.value = '';
        }

        function toggleCity(city) {
            const set = new Set(draftIds.value);
            if (set.has(city.id)) set.delete(city.id);
            else set.add(city.id);
            setDraft([...set]);
        }

        function selectAllVisible() {
            const set = new Set(draftIds.value);
            activeCities.value.forEach((c) => set.add(c.id));
            setDraft([...set]);
        }

        function clearVisible() {
            const set = new Set(draftIds.value);
            activeCities.value.forEach((c) => set.delete(c.id));
            setDraft([...set]);
        }

        function removeDraftCity(id) {
            setDraft(draftIds.value.filter((x) => x !== id));
        }

        function clearDraftAll() {
            draftIds.value = [];
            activeGroupId.value = '';
        }

        function removeSelectedCity(id, e) {
            e?.stopPropagation?.();
            const ids = selectedIds.value.filter((x) => x !== id);
            selectedIds.value = ids;
            emitValue(ids);
        }

        function persistGroups() {
            saveTempGroupsToCache(tempGroups.value);
        }

        function saveTempGroup() {
            const name = groupName.value.trim();
            if (!name) {
                showToast('请填写大区名');
                return;
            }
            if (!draftIds.value.length) {
                showToast('请先勾选城市');
                return;
            }
            const cityIds = [...draftIds.value];
            const exist = tempGroups.value.find((g) => g.name === name);
            if (exist) {
                exist.cityIds = cityIds;
                exist.updatedAt = Date.now();
                activeGroupId.value = exist.id;
                showToast(`已更新自定义组「${name}」`);
            } else {
                const item = {
                    id: String(Date.now()),
                    name,
                    cityIds,
                    createdAt: Date.now(),
                };
                tempGroups.value = [item, ...tempGroups.value];
                activeGroupId.value = item.id;
                showToast(`已保存自定义组「${name}」`);
            }
            persistGroups();
            groupName.value = '';
        }

        function applyTempGroup(group) {
            const validIds = (group.cityIds || []).filter((id) => cityMap[id]);
            draftIds.value = [...new Set(validIds)];
            activeGroupId.value = group.id;
            if (validIds.length && cityMap[validIds[0]]) {
                activeProvinceId.value = cityMap[validIds[0]].provinceId;
            }
            showToast(`已应用「${group.name}」（${validIds.length} 个市）`);
        }

        function removeTempGroup(id) {
            tempGroups.value = tempGroups.value.filter((g) => g.id !== id);
            if (activeGroupId.value === id) activeGroupId.value = '';
            persistGroups();
            showToast('已删除自定义组');
        }

        async function loadData() {
            if (dataLoaded) return;
            loading.value = true;
            loadError.value = '';
            try {
                const res = await fetch(AREA_URL);
                if (!res.ok) throw new Error('HTTP ' + res.status);
                const data = await res.json();
                const records = data.RECORDS || [];

                const provinceList = records
                    .filter((x) => x.lv === 1)
                    .sort((a, b) => a.id - b.id);

                const provinceNameMap = {};
                provinceList.forEach((p) => {
                    provinceNameMap[p.id] = p.name;
                    citiesByProvince[p.id] = [];
                });

                records
                    .filter((x) => x.lv === 2)
                    .sort((a, b) => a.id - b.id)
                    .forEach((c) => {
                        const item = {
                            id: c.id,
                            name: c.name,
                            provinceId: c.pid,
                            provinceName: provinceNameMap[c.pid] || '',
                        };
                        cityMap[c.id] = item;
                        if (citiesByProvince[c.pid]) {
                            citiesByProvince[c.pid].push(item);
                        }
                    });

                provinces.value = provinceList;
                if (activeProvinceId.value == null) {
                    activeProvinceId.value = provinceList[0]?.id ?? null;
                }
                dataLoaded = true;
                applyIdsFromProps(props.modelValue);
            } catch (e) {
                loadError.value =
                    '加载省市数据失败' + (e?.message ? '：' + e.message : '');
            } finally {
                loading.value = false;
            }
        }

        async function openPicker() {
            await loadData();
            if (loadError.value && !dataLoaded) return;
            draftIds.value = [...selectedIds.value];
            activeGroupId.value = '';
            groupName.value = '';
            if (draftIds.value.length && cityMap[draftIds.value[0]]) {
                activeProvinceId.value = cityMap[draftIds.value[0]].provinceId;
            }
            visible.value = true;
        }

        function closePicker() {
            visible.value = false;
        }

        function confirmPicker() {
            selectedIds.value = [...draftIds.value];
            emitValue(selectedIds.value);
            visible.value = false;
        }

        function onMaskClick() {
            closePicker();
        }

        watch(
            () => props.modelValue,
            (val) => {
                applyIdsFromProps(val);
            },
            {immediate: true}
        );

        onMounted(() => {
            // 预加载，便于框内正确显示名称
            loadData();
        });

        return {
            visible,
            loading,
            loadError,
            provinces,
            activeProvinceId,
            activeProvince,
            activeCities,
            draftSet,
            selectedCountInActive,
            selectedList,
            draftList,
            canSaveGroup,
            tempGroups,
            groupName,
            activeGroupId,
            toastText,
            provinceHasSelected,
            toggleCity,
            selectAllVisible,
            clearVisible,
            removeDraftCity,
            clearDraftAll,
            removeSelectedCity,
            saveTempGroup,
            applyTempGroup,
            removeTempGroup,
            openPicker,
            closePicker,
            confirmPicker,
            onMaskClick,
        };
    },
    template: `
      <div class="city-widget">
        <link rel="stylesheet" href="/_component/city/city.css">
        <div class="city-field" @click="openPicker">
          <div class="city-field-inner">
            <template v-if="selectedList.length">
              <span
                v-for="item in selectedList"
                :key="item.id"
                class="city-field-tag"
                @click.stop
              >
                {{ item.name }}
                <button type="button" title="移除" @click="removeSelectedCity(item.id, $event)">×</button>
              </span>
            </template>
            <span v-else class="city-field-placeholder">请选择城市</span>
          </div>
          <div class="city-field-actions" @click.stop>
            <button type="button" class="city-field-btn" @click="openPicker">选择</button>
          </div>
        </div>

        <!-- 不用 teleport：Eova 异步组件 + Teleport 会触发 Vue locateNonHydratedAsyncRoot 报错 -->
        <div v-if="visible" class="city-modal-mask" @click.self="onMaskClick">
          <div class="city-modal" role="dialog" aria-modal="true">
            <div class="city-modal-head">
              <h3>选择城市</h3>
              <button type="button" class="city-modal-close" title="关闭" @click="closePicker">×</button>
            </div>

            <div class="city-modal-body">
              <div v-if="loading" class="city-picker-loading">正在加载省市区数据…</div>
              <div v-else-if="loadError" class="city-picker-loading">{{ loadError }}</div>
              <div v-else class="city-picker">
                <div class="picker-main">
                  <div class="province-tabs">
                    <button
                      v-for="p in provinces"
                      :key="p.id"
                      type="button"
                      class="province-tab"
                      :class="{
                        'is-active': activeProvinceId === p.id,
                        'has-selected': provinceHasSelected(p.id),
                      }"
                      @click="activeProvinceId = p.id"
                    >
                      {{ p.name }}
                    </button>
                  </div>

                  <div class="city-panel">
                    <div class="city-panel-head">
                      <div class="city-panel-title">
                        {{ activeProvince?.name || '—' }}
                        <span class="city-count">
                          （已选 {{ selectedCountInActive }} / {{ activeCities.length }}）
                        </span>
                      </div>
                      <div class="city-panel-actions">
                        <button type="button" class="btn" @click="selectAllVisible">全选当前</button>
                        <button type="button" class="btn" @click="clearVisible">清空当前</button>
                      </div>
                    </div>

                    <div v-if="activeCities.length" class="city-grid">
                      <label
                        v-for="city in activeCities"
                        :key="city.id"
                        class="city-item"
                        :class="{ 'is-checked': draftSet.has(city.id) }"
                      >
                        <input
                          type="checkbox"
                          :checked="draftSet.has(city.id)"
                          @change="toggleCity(city)"
                        />
                        <span :title="city.name">{{ city.name }}</span>
                      </label>
                    </div>
                    <div v-else class="empty-tip">该省暂无城市数据</div>
                  </div>
                </div>

                <aside class="picker-side">
                  <div class="side-section">
                    <div class="group-save">
                      <input
                        v-model.trim="groupName"
                        class="search"
                        type="text"
                        maxlength="20"
                        placeholder="填写大区名，如：华中"
                        @keyup.enter="canSaveGroup && saveTempGroup()"
                      />
                      <button
                        type="button"
                        class="btn btn-primary"
                        :disabled="!canSaveGroup"
                        @click="saveTempGroup"
                      >
                        保存
                      </button>
                    </div>
                    <div class="group-list">
                      <div
                        v-for="g in tempGroups"
                        :key="g.id"
                        class="group-item"
                        :class="{ 'is-active': activeGroupId === g.id }"
                        @click="applyTempGroup(g)"
                      >
                        <div class="group-item-main">
                          <div class="group-item-name">{{ g.name }}</div>
                          <div class="group-item-meta">{{ g.cityIds.length }}市</div>
                        </div>
                        <button
                          type="button"
                          class="group-item-del"
                          title="删除自定义组"
                          @click.stop="removeTempGroup(g.id)"
                        >
                          ×
                        </button>
                      </div>
                      <div v-if="!tempGroups.length" class="side-empty">
                        填写大区名并勾选城市后可保存为组
                      </div>
                    </div>
                  </div>

                  <div class="side-section">
                    <div class="side-head">
                      <h3>已选 {{ draftList.length }} 个市</h3>
                      <button
                        v-if="draftList.length"
                        type="button"
                        class="btn btn-text"
                        @click="clearDraftAll"
                      >
                        清空全部
                      </button>
                    </div>
                    <div class="side-body">
                      <template v-if="draftList.length">
                        <span v-for="item in draftList" :key="item.id" class="tag">
                          {{ item.name }}
                          <button type="button" title="移除" @click="removeDraftCity(item.id)">×</button>
                        </span>
                      </template>
                      <div v-else class="side-empty">勾选左侧城市后显示在这里</div>
                    </div>
                  </div>
                </aside>
              </div>
            </div>

            <div class="city-modal-foot">
              <button type="button" class="btn" @click="closePicker">取消</button>
              <button type="button" class="btn btn-primary" @click="confirmPicker">确定</button>
            </div>
          </div>
          <div v-if="toastText" class="city-picker-toast">{{ toastText }}</div>
        </div>
      </div>
    `,
});

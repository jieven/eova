/**
 * Eova 设计模式（与业务模版逻辑独立）
 * 入口：admin 工具条 onclick="onDesign()"
 * 规范：ai/EovaUI.md —— 控件复用 EovaUI，布局按面板场景自定
 *
 * 进入设计模式后：
 * - 查询字段右上角出现关闭按钮，点击即移除该字段 DOM
 * - 表格数据列头右上角出现关闭按钮，点击即移除整列 DOM
 * 元字段配置 API 接入后，在 removeQueryField / removeTableColumn 中提交变更。
 */
window.__eovaDesign = {
    on: false,
    panel: null,
};

/** 进入设计模式（再次点击则退出；面板暂时不显示，避免遮挡） */
const onDesign = () => {
    if (window.__eovaDesign.on) {
        exitDesign();
        return;
    }
    window.__eovaDesign.on = true;
    // showDesignPanel();
    enableQueryFieldDesign();
    enableTableColumnDesign();
    me.layer.msg('进入设计模式');
};

/** 退出设计模式（已移除的字段保持移除，仅卸下关闭按钮） */
const exitDesign = () => {
    if (!window.__eovaDesign.on) {
        return;
    }
    window.__eovaDesign.on = false;
    disableQueryFieldDesign();
    disableTableColumnDesign();
    // hideDesignPanel();
    me.layer.msg('已退出设计模式');
};

const queryForm = () => document.querySelector('form.eova-form');

const enableQueryFieldDesign = () => {
    const form = queryForm();
    if (!form) {
        return;
    }
    form.classList.add('eova-design');
    form.querySelectorAll('.eova-form-field').forEach((field) => {
        if (field.querySelector('.eova-design-remove')) {
            return;
        }
        field.appendChild(createRenameButton());
        field.appendChild(createRemoveButton('移除字段'));
    });
    form.addEventListener('click', onQueryFieldClick);
};

const disableQueryFieldDesign = () => {
    const form = queryForm();
    if (!form) {
        return;
    }
    form.classList.remove('eova-design');
    form.removeEventListener('click', onQueryFieldClick);
    form.querySelectorAll('.eova-design-rename, .eova-design-remove').forEach((btn) => btn.remove());
};

const onQueryFieldClick = (e) => {
    const renameBtn = e.target.closest('.eova-design-rename');
    if (renameBtn) {
        e.preventDefault();
        e.stopPropagation();
        const field = renameBtn.closest('.eova-form-field');
        if (field) {
            renameQueryField(field);
        }
        return;
    }
    const btn = e.target.closest('.eova-design-remove');
    if (!btn) {
        return;
    }
    e.preventDefault();
    e.stopPropagation();
    const field = btn.closest('.eova-form-field');
    if (field) {
        removeQueryField(field);
    }
};

/**
 * 实时移除查询字段。
 * label.title 格式：中文名#字段编码#控件类型
 * @param {HTMLElement} field .eova-form-field
 */
const objectCode = () => (window.uzoo && uzoo.page && uzoo.page.object_code) || '';

const notifyRemove = (ok, code, fieldName) => {
    const text = `字段禁用成功：${fieldName}`;
    me.layer.notify(ok ? '操作成功' : '操作失败', text, ok ? 'ok' : 'no');
};

/**
 * @returns {Promise<boolean>}
 */
const requestRemoveField = (fieldCode, mode) => {
    const code = objectCode();
    return axios.get('/api/meta/removeField', {
        params: {mode, object: code, field: fieldCode}
    }).then((res) => {
        const ok = res.data && res.data.state === 'ok';
        notifyRemove(ok, code, fieldCode);
        return ok;
    }).catch(() => {
        notifyRemove(false, code, fieldCode);
        return false;
    });
};

const removeQueryField = (field) => {
    const label = field.querySelector('.eova-form-label');
    const title = label ? (label.getAttribute('title') || '') : '';
    const fieldCode = (title.split('#')[1] || '').trim();
    let mode = field.getAttribute('mode') || '';
    if (!mode) {
        const form = field.closest('form');
        const dataMode = form ? (form.getAttribute('data-mode') || '') : '';
        mode = dataMode.replace(/^mode:/, '');
    }
    requestRemoveField(fieldCode, mode).then((ok) => {
        if (ok) {
            field.remove();
        }
    });
};

const tableViews = () => document.querySelectorAll('.eova-table-view');

/** 数据列才可删，序号/固定占位列跳过 */
const isDataColumn = (th) => {
    if (th.classList.contains('eova-table-fixed_left')) {
        return false;
    }
    return th.innerText.trim().length > 0;
};

const enableTableColumnDesign = () => {
    tableViews().forEach((view) => {
        view.classList.add('eova-design');
        view.querySelectorAll('.eova-table-header th').forEach((th) => {
            if (!isDataColumn(th) || th.querySelector('.eova-design-remove')) {
                return;
            }
            th.appendChild(createRenameButton());
            th.appendChild(createRemoveButton('移除列'));
        });
        view.addEventListener('click', onTableColumnClick);
    });
};

const disableTableColumnDesign = () => {
    tableViews().forEach((view) => {
        view.classList.remove('eova-design');
        view.removeEventListener('click', onTableColumnClick);
        view.querySelectorAll('.eova-table-header .eova-design-rename, .eova-table-header .eova-design-remove').forEach((btn) => btn.remove());
    });
};

const onTableColumnClick = (e) => {
    const renameBtn = e.target.closest('.eova-design-rename');
    if (renameBtn) {
        e.preventDefault();
        e.stopPropagation();
        const th = renameBtn.closest('th');
        if (th) {
            renameTableColumn(th);
        }
        return;
    }
    const btn = e.target.closest('.eova-design-remove');
    if (!btn) {
        return;
    }
    const th = btn.closest('th');
    if (!th) {
        return;
    }
    e.preventDefault();
    e.stopPropagation();
    removeTableColumn(th);
};

/**
 * 实时移除表格列（表头、列宽、表体单元格）。
 * 列字段名在表头 col[title] 上，元字段 API 接入后据此提交。
 * @param {HTMLElement} th
 */
const removeTableColumn = (th) => {
    const index = [...th.parentElement.children].indexOf(th);
    const box = th.closest('.eova-table-box');
    const headerCols = box ? box.querySelectorAll('.eova-table-header colgroup col') : [];
    const fieldCode = (headerCols[index] && headerCols[index].getAttribute('title')) || '';
    requestRemoveField(fieldCode, 'table').then((ok) => {
        if (!ok || !box || index < 0) {
            return;
        }
        th.remove();
        box.querySelectorAll('colgroup').forEach((group) => {
            const col = group.children[index];
            if (col) {
                col.remove();
            }
        });
        box.querySelectorAll('tbody tr').forEach((tr) => {
            const cell = tr.children[index];
            if (cell) {
                cell.remove();
            }
        });
    });
};

const requestRenameField = (fieldCode, cn) => {
    const code = objectCode();
    return axios.get('/api/meta/renameField', {
        params: {object: code, field: fieldCode, cn}
    }).then((res) => {
        const ok = res.data && res.data.state === 'ok';
        me.layer.notify(ok ? '操作成功' : '操作失败', ok ? `中文名已更新：${cn}` : '中文名更新失败', ok ? 'ok' : 'no');
        return ok;
    }).catch(() => {
        me.layer.notify('操作失败', '中文名更新失败', 'no');
        return false;
    });
};

/** 表单标签与表头共用 cn，改一处两边一起换字 */
const syncCn = (fieldCode, cn) => {
    document.querySelectorAll('.eova-form-label').forEach((label) => {
        const parts = (label.getAttribute('title') || '').split('#');
        if (parts[1] !== fieldCode) {
            return;
        }
        parts[0] = cn;
        label.setAttribute('title', parts.join('#'));
        const text = [...label.childNodes].find((node) => node.nodeType === 3 && node.textContent.trim());
        if (text) {
            text.textContent = cn + ' ';
        }
    });
    document.querySelectorAll('.eova-table-header th').forEach((th) => {
        if (columnFieldCode(th) !== fieldCode) {
            return;
        }
        const span = th.querySelector('.eova-table_cell > span');
        const text = span && [...span.childNodes].find((node) => node.nodeType === 3);
        if (text) {
            text.textContent = cn;
        }
    });
};

const columnFieldCode = (th) => {
    const index = [...th.parentElement.children].indexOf(th);
    const box = th.closest('.eova-table-box');
    const cols = box ? box.querySelectorAll('.eova-table-header colgroup col') : [];
    return (cols[index] && cols[index].getAttribute('title')) || '';
};

const openRenameInput = (textNode, fieldCode) => {
    const parent = textNode && textNode.parentNode;
    if (!textNode || !fieldCode || !parent || parent.querySelector('.eova-design-rename-input')) {
        return;
    }
    const gap = parent.classList.contains('eova-form-label') ? ' ' : '';
    const old = textNode.textContent.trim();
    const input = document.createElement('input');
    input.type = 'text';
    input.className = 'eova-design-rename-input';
    input.value = old;
    parent.replaceChild(input, textNode);
    input.focus();
    input.select();

    let done = false;
    const finish = (save) => {
        if (done) {
            return;
        }
        done = true;
        const cn = input.value.trim();
        const keep = save && cn && cn !== old;
        const node = document.createTextNode((keep ? cn : old) + gap);
        if (input.parentNode) {
            input.parentNode.replaceChild(node, input);
        }
        if (!keep) {
            return;
        }
        requestRenameField(fieldCode, cn).then((ok) => {
            if (ok) {
                syncCn(fieldCode, cn);
            } else {
                node.textContent = old + gap;
            }
        });
    };
    input.addEventListener('keydown', (e) => {
        e.stopPropagation();
        if (e.key === 'Enter') {
            e.preventDefault();
            input.blur();
        } else if (e.key === 'Escape') {
            e.preventDefault();
            input.value = old;
            finish(false);
        }
    });
    input.addEventListener('blur', () => finish(true));
    input.addEventListener('click', (e) => e.stopPropagation());
};

const renameQueryField = (field) => {
    const label = field.querySelector('.eova-form-label');
    if (!label) {
        return;
    }
    const fieldCode = ((label.getAttribute('title') || '').split('#')[1] || '').trim();
    const text = [...label.childNodes].find((node) => node.nodeType === 3 && node.textContent.trim());
    openRenameInput(text, fieldCode);
};

const renameTableColumn = (th) => {
    const span = th.querySelector('.eova-table_cell > span');
    const text = span && [...span.childNodes].find((node) => node.nodeType === 3);
    openRenameInput(text, columnFieldCode(th));
};

const createRenameButton = () => {
    const btn = document.createElement('button');
    btn.type = 'button';
    btn.className = 'eova-design-rename';
    btn.title = '修改中文名';
    btn.innerHTML = '<i class="eova-icon-edit"></i>';
    return btn;
};

const createRemoveButton = (title) => {
    const btn = document.createElement('button');
    btn.type = 'button';
    btn.className = 'eova-design-remove';
    btn.title = title;
    btn.innerHTML = '<i class="eova-icon-close"></i>';
    return btn;
};

const showDesignPanel = () => {
    if (window.__eovaDesign.panel) {
        return;
    }
    const panel = document.createElement('div');
    panel.className = 'eova-panel eova-design-panel';
    panel.innerHTML = `
      <div class="eova-design-panel__hd">设计模式</div>
      <div class="eova-design-panel__body">
        <div class="eova-design-panel__tip">点击查询字段或表格列头右上角关闭即可移除</div>
        <button type="button" class="eova-btn_error" data-act="exit">退出设计模式</button>
      </div>
    `;
    panel.addEventListener('click', (e) => {
        const btn = e.target.closest('[data-act="exit"]');
        if (btn) {
            exitDesign();
        }
    });
    document.body.appendChild(panel);
    window.__eovaDesign.panel = panel;
};

const hideDesignPanel = () => {
    const panel = window.__eovaDesign.panel;
    if (panel && panel.parentNode) {
        panel.parentNode.removeChild(panel);
    }
    window.__eovaDesign.panel = null;
};

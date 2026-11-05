<ev-dialog
    title="字段权限"
    width="760px"
    height="500px"
    layer-container="body"
    v-model:visible="visible"
>
<div style="padding: 10px;">
  <form method="post" class="eova-form" @submit.prevent>
    <div class="eova-form-field">
      <label class="eova-form-label required">元对象</label>
      <ev-find
          name="object"
          option="eova_object"
          v-model="objectCode"
          placeholder="请选择元对象"
      ></ev-find>
    </div>

    <div class="eova-form-field">
      <label class="eova-form-label required">元字段</label>
      <ev-select
          :key="'field-' + (objectCode || '')"
          name="field"
          :option="fieldOption"
          :multiple="true"
          :disabled="!objectCode"
          v-model="form.field"
          placeholder="请先选择元对象"
      ></ev-select>
    </div>

    <div class="eova-form-field">
      <label class="eova-form-label required">授权类型</label>
      <ev-radio
          name="type"
          :items="typeItems"
          v-model="form.type"
          @change="onTypeChange"
      ></ev-radio>
    </div>

    <div class="eova-form-field">
      <label class="eova-form-label required">授权目标</label>
      <ev-find
          v-if="isUserAuth"
          ref="refAuthFind"
          name="auth"
          option="eova_user"
          :multiple="true"
          :disabled="!objectCode"
          v-model="form.auth"
          placeholder="请选择用户（可多选）"
      ></ev-find>
      <ev-select
          v-else-if="isOrgAuth"
          name="auth"
          type="tree"
          object="eova_org"
          :tree-conf="treeConf"
          :multiple="true"
          :disabled="!objectCode"
          v-model="form.authOrg"
          placeholder="请选择部门（可多选）"
      ></ev-select>
      <ev-select
          v-else
          :key="'role-' + (objectCode || '')"
          name="auth"
          option="eova_role"
          :multiple="true"
          :disabled="!objectCode"
          v-model="form.auth"
          :placeholder="authPlaceholder"
      ></ev-select>
    </div>

    <div class="eova-notes">💡 {{ currentTypeTip }}</div>
  </form>
  <div class="eova-btn_dialog__right">
    <button class="eova-btn" :disabled="saving" @click="saveRule">确认</button>
    <button class="eova-btn eova-btn_base" @click="visible = false">取消</button>
  </div>
</div>
</ev-dialog>
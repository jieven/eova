<ev-dialog
  title="功能授权给角色"
  width="1200px"
  height="700px"
  layer-container="body"
  v-model:visible="visible"
>
  <div style="padding: 10px;">
    <h3>{{ menu?.name }}</h3>
    <template v-for="(btns, index) in btnCats" :key="index">
      <table class="eova-table" ev-size="s30">
        <colgroup>
          <col width="140">
          <col v-for="btn in btns" :key="btn.id">
        </colgroup>
        <thead>
        <tr>
          <th>角色</th>
          <th v-for="btn in btns" :key="btn.id" style="text-align: center">{{ btn.name }}</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="role in roles" :key="role.id">
          <td>{{ role.name }}</td>
          <td v-for="b in btns" :key="b.id" style="text-align: center">
            <label>
              <input type="checkbox"
                     @change="onAuth($event, b.id, role.id)"
                     :id="'CK_' + b.id + '_' + role.id">
            </label>
          </td>
        </tr>
        </tbody>
      </table>
      <hr>
    </template>
    <div class="eova-notes"> 💡 授权后相关角色需要重新登录更新权限</div>
  </div>
</ev-dialog>

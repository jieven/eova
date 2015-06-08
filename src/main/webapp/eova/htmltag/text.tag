<div class="eova-text" id="${id!}" name="${(isQuery!)=='true' ? QUERY+name:name}" value="${value!}" 
data-options="
required : ${isNoN!false}, placeholder : ${format(placeholder!)} 
${!isEmpty(options!) ? ', ' + options : '' } 
${!isEmpty(validate!) ? ', ' + validate : '' }"
></div>
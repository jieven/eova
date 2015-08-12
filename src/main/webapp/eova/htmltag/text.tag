<div enable="false" class="eova-text" id="${id!}" name="${(isQuery!)=='true' ? QUERY+name:name}" value="${value!}"
data-options="
placeholder : ${format(placeholder!)} 
${!isEmpty(options!) ? ', ' + options : '' } 
"
></div>
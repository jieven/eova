<textarea class="eova-texts" 
id="${id!}" 
name="${(isQuery!)=='true' ? QUERY+name:name}" ${isTrue(isNoN!) ? 'required' : ''}
placeholder='${placeholder!}'  
${htmlattrs!} 
style="${style!}">${value!}</textarea>
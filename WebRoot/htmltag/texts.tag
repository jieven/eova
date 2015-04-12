<textarea 
id="${id!}" 
name="${(isQuery!)=='true' ? QUERY+name:name}" 
${htmlattrs!} 
style="${style!}">${value!}</textarea>
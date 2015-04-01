<textarea 
id="${id!}" 
name="${(isQuery!)=='true' ? QUERY+name:name}" 
${htmlattrs!} 
style="
width: ${isEmpty(width) ? 471:width}px;
height: ${isEmpty(height) ? 80:height}px;
">${value!}</textarea>
const EOVA_THEME = 'default'// dark

// 动态切换主题
document.addEventListener('DOMContentLoaded', function () {
    // 动态加载主题样式
    EovaTools.x.dom.loadCSS(`/_eova/theme/eova.theme.${EOVA_THEME}.css`);
    document.body.classList.add(`eova-theme_${EOVA_THEME}`);
});
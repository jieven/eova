package key;

/**
 * 常用快捷键分享（Eclipse keymap for all IntelliJ-based IDEs）
 */
public class KeyTest {
    public static void main(String[] args) {
        // 格式化代码 Ctrl + Shift + F(ormat)

        // 在文件中查找（改键 加入快捷键）
        // Ctrl+Alt+F(ind)

        // 在文件中替换（改键 加入快捷键）
        // Ctrl+Alt+H

        // Ctrl + / 注释行
        // aaa;

        // Ctrl + Alt + ↑/↓ 复制行
        System.out.println("复制行");
        System.out.println("复制行1");

        // Ctrl + D 删除行
        System.out.println();
        System.out.println();

        // 选中内容转大写/小写 Ctrl+shift+X/Y
        int testFun = 100;

        // 光标选中"111" 然后 Shift + alt + L 进行变量提取
        // 操作前
        System.out.println(111);
        // 操作后
        int aa = 111;
        System.out.println(aa);

        // 其他右键菜单 快捷操作

        // 重构>重命名
        // 选中变量/方法名/类名

        // 级联 操作
        System.out.println(aa);
        // 按住Ctrl 鼠标单击变量名/方法名, 跳转到变量声明位置.

    }

    // 重构 > 更改签名 (添加参数, 减少参数)
    public void aaa(int a) {
    }
}
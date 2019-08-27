package cn.lyb.zc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class Menu {
    private Integer id;

    private Integer pid;

    private String name;

    private String url;

    private String icon;

    private List<Menu> children = new LinkedList<>();

    // 控制节点展开还是折叠，设置为true是让整个树形菜单默认展开
    private Boolean open = true;

    public Menu(Integer id, Integer pid, String name, String url, String icon) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.url = url;
        this.icon = icon;
    }
}
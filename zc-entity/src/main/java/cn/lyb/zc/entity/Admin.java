package cn.lyb.zc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Admin {
    private Integer id;

    private String loginAcct;

    private String userPswd;

    private String userName;

    private String email;

    private String createTime;
}
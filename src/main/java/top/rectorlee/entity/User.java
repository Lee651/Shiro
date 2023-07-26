package top.rectorlee.entity;

import lombok.*;

/**
 * @description:
 * @author: Lee
 * @date: 2023-07-25 19:13:02
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;

    private String userName;

    private String password;
}

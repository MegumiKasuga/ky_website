package org.fairingstudio.kuayue_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModParamInput {

    //MC版本号
    private String MCVersion;
    //运行环境
    private String env;
}

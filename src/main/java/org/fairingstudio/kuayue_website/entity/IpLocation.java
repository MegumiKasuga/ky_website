package org.fairingstudio.kuayue_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IpLocation implements Serializable {

    private String ip;

    private String country;

    private String province;

    private String city;

    private String isp;
}

package org.fairingstudio.kuayue_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SchematicController {

    @RequestMapping("/schematic")
    public String gallery() {
        return "schematic";
    }
}

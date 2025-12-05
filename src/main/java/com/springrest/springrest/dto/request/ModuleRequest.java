package com.springrest.springrest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRequest {
    private String moduleTitle;
    private int moduleOrder;
}


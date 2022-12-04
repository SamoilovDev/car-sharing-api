package org.allJavaFiles.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Car {
    private final int id;

    private final String name;

    private final int companyID;
}

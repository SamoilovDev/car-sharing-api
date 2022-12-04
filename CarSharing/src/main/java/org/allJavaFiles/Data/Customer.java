package org.allJavaFiles.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Customer {
    int id;

    String name;

    Integer rentedCarID;
}

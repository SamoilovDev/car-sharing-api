package org.all.files.data;

import lombok.Builder;

@Builder
public record Customer(int id, String name, Integer rentedCarID) {
}

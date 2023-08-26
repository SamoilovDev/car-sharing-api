package org.all.files.dto;

import lombok.Builder;

@Builder
public record Customer(int id, String name, Integer rentedCarID) {
}

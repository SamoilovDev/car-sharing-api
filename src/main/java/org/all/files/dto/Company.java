package org.all.files.dto;

import lombok.Builder;

@Builder
public record Company(int id, String name) {
}

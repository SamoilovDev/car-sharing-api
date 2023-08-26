package org.all.files.dto;

import lombok.Builder;

@Builder
public record Car(int id, String name, int companyID) {
}

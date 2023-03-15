package org.all.files.data;

import lombok.Builder;

@Builder
public record Car(int id, String name, int companyID) {
}

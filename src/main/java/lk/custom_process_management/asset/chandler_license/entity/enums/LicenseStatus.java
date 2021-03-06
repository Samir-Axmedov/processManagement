package lk.custom_process_management.asset.chandler_license.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LicenseStatus {
    VALID("Valid"),
    PROCEED("Proceeding"),
    INVALID("Invalid");

    private final String licenseStatus;
}

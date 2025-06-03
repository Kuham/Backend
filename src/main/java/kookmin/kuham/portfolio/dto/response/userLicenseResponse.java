package kookmin.kuham.portfolio.dto.response;

import kookmin.kuham.portfolio.schema.License;

import java.util.List;

public record userLicenseResponse(List<License> licenses) {
}

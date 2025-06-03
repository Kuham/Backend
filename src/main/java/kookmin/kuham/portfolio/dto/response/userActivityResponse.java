package kookmin.kuham.portfolio.dto.response;

import kookmin.kuham.portfolio.schema.Activity;

import java.util.List;

public record userActivityResponse(List<Activity> activities) {
}

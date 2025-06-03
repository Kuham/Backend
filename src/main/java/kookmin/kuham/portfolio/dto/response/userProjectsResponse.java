package kookmin.kuham.portfolio.dto.response;

import kookmin.kuham.portfolio.schema.Project;

import java.util.List;

public record userProjectsResponse(List<Project> projects) {}

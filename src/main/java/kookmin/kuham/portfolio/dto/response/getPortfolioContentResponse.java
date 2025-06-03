package kookmin.kuham.portfolio.dto.response;

import kookmin.kuham.portfolio.schema.Portfolio;
import kookmin.kuham.user.schema.User;
import lombok.Getter;

import java.util.List;

@Getter
public class getPortfolioContentResponse {
    private final String name;
    private final String email;
    private final String studentNumber;
    private final String major;
    private final String grade;
    private final String profileUrl;
    private final String introduce;
    private final List<String> links;
    private final List<String> stacks;
    private final List<String> characters;

    public getPortfolioContentResponse(User user, Portfolio portfolio) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.studentNumber = user.getStudentNumber();
        this.major = user.getMajor();
        this.grade = user.getGrade();
        this.profileUrl = user.getProfileUrl();
        this.introduce = portfolio.getIntroduce();
        this.links = portfolio.getLinks();
        this.stacks = portfolio.getStacks();
        this.characters = portfolio.getCharacters();
    }
}

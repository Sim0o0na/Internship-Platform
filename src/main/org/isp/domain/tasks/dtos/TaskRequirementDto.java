package org.isp.domain.tasks.dtos;

public class TaskRequirementDto {
    private String title;
    private int skillPoints;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }
}

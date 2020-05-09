package st.alexandra.facades.dto;

import java.util.List;

public class UserData {
    private Integer level;
    private Double discountPercentage;
    private List<AchievementData> achievements;
    private Long xp;
    private Long maxXP;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public List<AchievementData> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<AchievementData> achievements) {
        this.achievements = achievements;
    }

    public Long getXP() {
        return xp;
    }

    public void setXP(Long xp) {
        this.xp = xp;
    }

    public Long getMaxXP() {
        return maxXP;
    }

    public void setMaxXP(Long maxXP) {
        this.maxXP = maxXP;
    }
}

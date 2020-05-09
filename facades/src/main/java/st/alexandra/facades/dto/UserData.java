package st.alexandra.facades.dto;

import com.es.core.model.user.Achievement;

import java.util.List;

public class UserData {
    private Integer level;
    private Double discountPercentage;
    private List<Achievement> achievements;
    private Long xp;

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

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public Long getXP() {
        return xp;
    }

    public void setXP(Long xp) {
        this.xp = xp;
    }
}

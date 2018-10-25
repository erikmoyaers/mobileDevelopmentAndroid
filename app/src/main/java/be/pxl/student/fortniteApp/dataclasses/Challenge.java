package be.pxl.student.fortniteApp.dataclasses;

import java.util.HashMap;

public class Challenge {

    private String name;
    private String questsTotal; //how much time they need to perform the quest
    private String questReward; //ammount of battlestars earned by completing the test

    public Challenge() {
    }


    public Challenge(HashMap<String,String> challenge) {
        this.name = challenge.get("name");
        this.questsTotal = challenge.get("questsTotal");
        this.questReward = challenge.get("rewardName");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestsTotal() {
        return questsTotal;
    }

    public void setQuestsTotal(String questsTotal) {
        this.questsTotal = questsTotal;
    }

    public String getQuestReward() {
        return questReward;
    }

    public void setQuestReward(String questReward) {
        this.questReward = questReward;
    }
}

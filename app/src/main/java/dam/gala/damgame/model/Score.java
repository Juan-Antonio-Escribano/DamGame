package dam.gala.damgame.model;

public class Score {
    private int rankingId, rankingScore, rankingPosition;
    private String nameUser;

    public Score(int rankingId, int rankingScore, int rankingPosition, String nameUser) {
        this.rankingId = rankingId;
        this.rankingScore = rankingScore;
        this.rankingPosition = rankingPosition;
        this.nameUser = nameUser;
    }

    public int getRankingId() {
        return rankingId;
    }

    public void setRankingId(int rankingId) {
        this.rankingId = rankingId;
    }

    public int getRankingScore() {
        return rankingScore;
    }

    public void setRankingScore(int rankingScore) {
        this.rankingScore = rankingScore;
    }

    public int getRankingPosition() {
        return rankingPosition;
    }

    public void setRankingPosition(int rankingPosition) {
        this.rankingPosition = rankingPosition;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}

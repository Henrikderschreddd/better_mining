package com.better_mining.block;

public class MultiStageBlockLocal {
    private int stage;
    private final int maxStage;
    private final String id;
    public MultiStageBlockLocal(MultiStageBlock block) {
        this.stage = block.getMaxStage();
        this.maxStage = block.getMaxStage();
        this.id = block.getId();
    }

    public int getStage() {
        return stage;
    }
    public void removeStage () {
        if (stage > 0) {
            stage -= 1;
        }
    }
    public void resetStage () {
        stage = maxStage;
    }

    public String getId() {
        return id;
    }
}

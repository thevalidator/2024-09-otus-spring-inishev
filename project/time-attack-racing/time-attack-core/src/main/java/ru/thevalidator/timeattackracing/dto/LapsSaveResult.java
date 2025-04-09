package ru.thevalidator.timeattackracing.dto;

public class LapsSaveResult {

    private int successful;

    private int failed;

    private int total;

    public LapsSaveResult() {
    }

    public LapsSaveResult(int successful, int failed, int total) {
        this.successful = successful;
        this.failed = failed;
        this.total = total;
    }

    public int getSuccessful() {
        return successful;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}

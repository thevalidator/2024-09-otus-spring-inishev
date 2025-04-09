package ru.thevalidator.timeattackracing.dto;

public class Pagination {

    private int offset;

    private int pageSizeLimit;

    private int elements;

    private boolean hasPrevious;

    private boolean hasNext;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSizeLimit() {
        return pageSizeLimit;
    }

    public void setPageSizeLimit(int pageSizeLimit) {
        this.pageSizeLimit = pageSizeLimit;
    }

    public int getElements() {
        return elements;
    }

    public void setElements(int elements) {
        this.elements = elements;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

}

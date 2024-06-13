package su.knst.finwave.api.tools;


import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionsFilter {
    public static final TransactionsFilter EMPTY = new TransactionsFilter();

    protected List<Long> tagsIds;
    protected List<Long> accountIds;
    protected List<Long> currenciesIds;
    protected OffsetDateTime fromTime;
    protected OffsetDateTime toTime;
    protected String description;

    protected TransactionsFilter() {
    }

    public TransactionsFilter(List<Long> tagsIds, List<Long> accountIds, List<Long> currenciesIds, OffsetDateTime fromTime, OffsetDateTime toTime, String description) {
        this.tagsIds = tagsIds;
        this.accountIds = accountIds;
        this.currenciesIds = currenciesIds;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.description = description;
    }

    public List<Object> toOptions() {
        ArrayList<Object> result = new ArrayList<>();

        if (tagsIds != null && !tagsIds.isEmpty()) {
            result.add("tagsIds");
            result.add(tagsIds.toString());
        }

        if (accountIds != null && !accountIds.isEmpty()) {
            result.add("accountsIds");
            result.add(accountIds.toString());
        }

        if (currenciesIds != null && !currenciesIds.isEmpty()) {
            result.add("currenciesIds");
            result.add(currenciesIds.toString());
        }

        if (fromTime != null) {
            result.add("fromTime");
            result.add(fromTime.toString());
        }

        if (toTime != null) {
            result.add("toTime");
            result.add(toTime.toString());
        }

        if (description != null && !description.isBlank()) {
            result.add("description");
            result.add(description);
        }

        return result;
    }

    public List<Long> getTagsIds() {
        return tagsIds;
    }

    public List<Long> getAccountIds() {
        return accountIds;
    }

    public List<Long> getCurrenciesIds() {
        return currenciesIds;
    }

    public OffsetDateTime getFromTime() {
        return fromTime;
    }

    public OffsetDateTime getToTime() {
        return toTime;
    }

    public String getDescription() {
        return description;
    }

    public void setTagsIds(List<Long> tagsIds) {
        this.tagsIds = tagsIds;
    }

    public void setAccountIds(List<Long> accountIds) {
        this.accountIds = accountIds;
    }

    public void setCurrenciesIds(List<Long> currenciesIds) {
        this.currenciesIds = currenciesIds;
    }

    public void setFromTime(OffsetDateTime fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(OffsetDateTime toTime) {
        this.toTime = toTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

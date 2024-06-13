package su.knst.finwave.api.tools;

public class AbstractMetadata {
    protected long type;

    // Accumulation metadata
    protected long internalTransactionId;

    // Internal transfer metadata
    protected long id;
    protected Transaction linkedTransaction;

    public AbstractMetadata(long type) {
        this.type = type;
    }

    public AbstractMetadata(long type, long internalTransactionId, long id, Transaction linkedTransaction) {
        this.type = type;
        this.internalTransactionId = internalTransactionId;
        this.id = id;
        this.linkedTransaction = linkedTransaction;
    }

    public AbstractMetadata(long type, long id, Transaction linkedTransaction) {
        this.type = type;
        this.id = id;
        this.linkedTransaction = linkedTransaction;
    }

    public AbstractMetadata(long type, long internalTransactionId) {
        this.type = type;
        this.internalTransactionId = internalTransactionId;
    }

    public MetadataType getType() {
        return MetadataType.get((short) type);
    }

    public long getInternalTransactionId() {
        return internalTransactionId;
    }

    public long getId() {
        return id;
    }

    public Transaction getLinkedTransaction() {
        return linkedTransaction;
    }
}

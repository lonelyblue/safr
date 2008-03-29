package net.sourceforge.safr.core.integration.support;

public class Crypto {

    public enum Operation {ENCRYPT, DECRYPT}

    private Operation operation;
    
    private Object value;
    
    private Object context;
    
    public Crypto(Operation operation, Object value, Object context) {
        this.operation = operation;
        this.value = value;
        this.context = context;
    }
    
    public Operation getOperation() {
        return operation;
    }
    
    public Object getValue() {
        return value;
    }
    
    public Object getContext() {
        return context;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Crypto)) {
            return false;
        }
        Crypto that = (Crypto)obj;
        return this.operation.equals(that.operation)
            && this.context.equals(that.context)
            && this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + operation.hashCode();
        result = 37 * result + context.hashCode();
        result = 37 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return operation + ": value=" + value + " context=" + context;
    }
    
}

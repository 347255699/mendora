package org.mendora.util.constant;

/**
 * about sql element name;
 */
public enum SqlReferences {
    STATEMENT("statement"), PARAMS("params"), BATCH("batch");
    private String val;
    SqlReferences(String val){
        this.val = val;
    }

    public String val(){
        return this.val;
    }
}

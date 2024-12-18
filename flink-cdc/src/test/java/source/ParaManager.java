package source;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.util.Preconditions;

public class ParaManager {
    private final ParameterTool paramTool;
    private String srSinkTableSuffix;

    public ParaManager(ParameterTool paramTool) {
        this.paramTool = paramTool;
        sinkBuilder();
    }

    public String getSrSinkTableSuffix() {
        return srSinkTableSuffix;
    }

    public void setSrSinkTableSuffix(String srSinkTableSuffix) {
        this.srSinkTableSuffix = srSinkTableSuffix;
    }

    private void sinkBuilder() {
        this.srSinkTableSuffix = paramTool.get("sink.table_suffix", "");
    }

    public void validate() {
        Preconditions.checkNotNull(this.srSinkTableSuffix, "sink.table_suffix must not be null");
    }
}

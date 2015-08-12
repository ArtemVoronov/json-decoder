package mobi.eyeline.jsonb;

/**
 * Node types according JSON rules
 * Created by Artem Voronov on 04.08.2015.
 */
public enum NodeType {

    OBJECT,     // {} or {members}
    PAIR,       // string : value
    PAIR_STRING,
    PAIR_NUMBER,
    PAIR_BOOLEAN,
    PAIR_NULL,
    ARRAY,      // [] or [elements]
    VALUE,      // string, number, object, array, true, false, null,
    VALUE_STRING,
    VALUE_NUMBER,
    VALUE_BOOLEAN,
    VALUE_NULL,
    ROOT        // base node
}

package mobi.eyeline.jsonb;

/**
 * Node types according JSON rules
 * Created by Artem Voronov on 04.08.2015.
 */
public enum NodeType {

    OBJECT,     // {} or {members}
    PAIR,       // string : value
    ARRAY,      // [] or [elements]
    VALUE,      // string, number, object, array, true, false, null,
    ROOT        // base node
}

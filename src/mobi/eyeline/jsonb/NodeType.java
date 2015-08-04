package mobi.eyeline.jsonb;

/**
 * Created by voronov on 04.08.2015.
 */
public enum NodeType {
    // Token types
    OBJECT,     // {} or {members}
//    members,    // pair or pair, members
    PAIR,       // string : value
    ARRAY,      // [] or [elements]
//    elements,   // value or value, elements
    VALUE,      // string, number, object, array, true, false, null,
    ROOT        // base node
}

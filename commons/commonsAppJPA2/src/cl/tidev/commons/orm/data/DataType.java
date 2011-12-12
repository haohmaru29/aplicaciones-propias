package cl.tidev.commons.orm.data;

public enum DataType {
	/** text, varchar, varchar2, char[],  */
    STRING,

    /** Number, numeric, integer, float, double */
    NUMERIC,
    
    /** blob */
    BINARY,
    
    /** date */
    DATE,
    
    /** bit */
    BOOLEAN,
    
    /** functions, procedure */
    SQL,
    
    /** undefined */
    UNDEFINED
}

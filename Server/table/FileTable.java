package Server.table;

public class FileTable {
    public static final String TABLE_NAME="files";
    public static final String COLUMN_REGISTRATION_NUMBER="registrationNo";
    public static final String COLUMN_FILENAME="filename";
    public static final String COLUMN_FILETYPE="filetype";
    public static final String COLUMN_FILEBLOB="fileblob";
    public static final String QUERY_FETCH_FILES  = "select * from "+TABLE_NAME+" where "+COLUMN_REGISTRATION_NUMBER+"=?;";
    public static final String QUERY_DOWNLOAD_FILE = "select * from "+TABLE_NAME+" where "+COLUMN_REGISTRATION_NUMBER+"=? and "+COLUMN_FILENAME+"=?;";
    public static final String QUERY_INSERT_FILE= "insert into "+TABLE_NAME+"("+COLUMN_REGISTRATION_NUMBER+","+COLUMN_FILENAME+","+COLUMN_FILETYPE +","+COLUMN_FILEBLOB+")VALUES(?,?,?,?)";
    public static final String QUERY_DELETE_FILE = "delete from "+TABLE_NAME+" where "+COLUMN_REGISTRATION_NUMBER+"=? and "+COLUMN_FILENAME+"=?;";
}

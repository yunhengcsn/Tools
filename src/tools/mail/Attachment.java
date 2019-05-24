package tools.mail;

import java.io.File;

/**
 * @BelongsProject: Tools
 * @BelongsPackage: tools.mail
 * @Author: csn
 * @Description: attachment of mail
 */
public class Attachment {
    private String atId;
    private File file;//文件
    private String filename;//文件名

    public Attachment() {}

    public Attachment(File file, String filename) {
        this.file = file;
        this.filename = filename;
    }

    public String getAtId() {
        return atId;
    }

    public void setAtId(String atId) {
        this.atId = atId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

package cn.lannooo.websocket;

/**
 * Created by 51499 on 2017/6/9 0009.
 */
public class ServerMessage {
    private String source;
    private String title;

    public ServerMessage(String source, String title) {
        this.source = source;
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

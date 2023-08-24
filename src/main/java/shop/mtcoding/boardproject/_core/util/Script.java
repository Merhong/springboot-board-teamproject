package shop.mtcoding.boardproject._core.util;

// 오버로딩
public class Script {

    // 경고창 + 뒤로가기
    public static String back(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }

    // 이동
    public static String href(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("location.href='" + url + "';");
        sb.append("</script>");
        return sb.toString();
    }

    // 경고창 + 이동
    public static String href(String url, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("location.href='" + url + "';");
        sb.append("</script>");
        return sb.toString();
    }

}

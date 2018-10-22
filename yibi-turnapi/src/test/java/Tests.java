import com.yibi.common.encrypt.AES;
import com.yibi.turnapi.commons.utils.StrUtils;

public class Tests {
    public static void main(String[] args) {
        String deParamStr = AES.decrypt("znGaqjsQRsspKKuxM/e9QnVFCUwRYyTLCobbLIw2aLY=", "803c85fd9c964568");
//        String deParamStr = AES.decrypt("ju9eGgR1bMDRdZBWXrEv5E\\/IeY8DJRJ7iDteQ6rB34Y=", "65086f22da034e38");

        System.out.println(deParamStr);

        String pwdStr = StrUtils.replaceAll(deParamStr, "\"password\":\".+?\"", "\"password\":\"***\"");
       String s = StrUtils.replaceAll(pwdStr, "\"oldPassword\":\".+?\"", "\"oldPassword\":\"***\"");
        System.out.println(s);
    }
}

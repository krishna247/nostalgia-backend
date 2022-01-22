package base.utils;

import java.io.File;

public class CheckLocal {
    public static boolean checkIsLocal(){
        return ! new File("/sys/hypervisor/uuid").exists();
    }

    public static void main(String[] args) {
        System.out.println(checkIsLocal());
    }
}

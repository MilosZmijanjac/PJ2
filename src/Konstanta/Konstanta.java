package Konstanta;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Konstanta {
    public static String kretanjaFolder;
    public static String voziloConfig;
    public static String kompozicijeFolder;
    public static String slikeFolder;
    public static String logFolder="logs";
    public static Map<String,String[]> kombinacije= Stream.of(new Object[][]{
            {"LPE", new String[]{"LPE", "LUE", "VPR", "VPL", "VPSS", "VPZS"}},
            {"LPD", new String[]{"LPD", "LUD", "VPR", "VPL", "VPSS", "VPZS"}},
            {"LPP", new String[]{"LPP", "LUP", "VPR", "VPL", "VPSS", "VPZS"}},
            {"LTD", new String[]{"LTD", "LUD", "VT"}},
            {"LTE", new String[]{"LTE", "LUE", "VT"}},
            {"LTP", new String[]{"LTP", "LUP", "VT"}},
            {"LUE", new String[]{"LPE","LTE", "LUE", "VT", "VPR", "VPL", "VPSS", "VPZS", "VPN"}},
            {"LUD", new String[]{"LPD","LTD", "LUD", "VT", "VPR", "VPL", "VPSS", "VPZS", "VPN"}},
            {"LUP", new String[]{"LPP","LTP", "LUP", "VT", "VPR", "VPL", "VPSS", "VPZS", "VPN"}},
            {"LME", new String[]{"VPN"}},
            {"LMD", new String[]{"VPN"}},
            {"LMP", new String[]{"VPN"}}}).collect(Collectors.toMap(data -> (String) data[0], data -> (String[]) data[1]));
}

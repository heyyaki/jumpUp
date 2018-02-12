package com.example.com.jumpupbitcoin;

import java.util.HashMap;

public class Const {

    public static final int VIBRATION_DISABLED = -1;

    public static long[] vibPattern = {100, 300};

    public static Integer[] sCoinImages = new Integer[]{R.drawable.btc, R.drawable.ada, R.drawable.xrp, R.drawable.snt, R.drawable.qtum, R.drawable.eth, R.drawable.mer, R.drawable.neo, R.drawable.sbd, R.drawable.steem,
            R.drawable.xlm, R.drawable.emc, R.drawable.btg, R.drawable.ardr, R.drawable.xem, R.drawable.tix, R.drawable.powr, R.drawable.bcc, R.drawable.kmd, R.drawable.strat,
            R.drawable.etc, R.drawable.omg, R.drawable.grs, R.drawable.storj, R.drawable.rep, R.drawable.waves, R.drawable.ark, R.drawable.xmr, R.drawable.ltc, R.drawable.lsk,
            R.drawable.vtc, R.drawable.pivx, R.drawable.mtl, R.drawable.dash, R.drawable.zec};

    public static HashMap<Integer, String> sCoinNames = new HashMap<>();

    static {
        sCoinNames.put(0, "비트코인");
        sCoinNames.put(1, "에이다");
        sCoinNames.put(2, "리플");
        sCoinNames.put(3, "스테이터스네트워크토큰");
        sCoinNames.put(4, "퀀텀");
        sCoinNames.put(5, "이더리움");
        sCoinNames.put(6, "머큐리");
        sCoinNames.put(7, "네오");
        sCoinNames.put(8, "스팀달러");
        sCoinNames.put(9, "스팀");
        sCoinNames.put(10, "스텔라루멘");
        sCoinNames.put(11, "아인스타이늄");
        sCoinNames.put(12, "비트코인골드");
        sCoinNames.put(13, "아더");
        sCoinNames.put(14, "뉴이코미무브먼트");
        sCoinNames.put(15, "블록틱스");
        sCoinNames.put(16, "파워렛저");
        sCoinNames.put(17, "비트코인캐시");
        sCoinNames.put(18, "코모도");
        sCoinNames.put(19, "스트라티스");
        sCoinNames.put(20, "이더리움클래식");
        sCoinNames.put(21, "오미세고");
        sCoinNames.put(22, "그리스톨코인");
        sCoinNames.put(23, "스토리지");
        sCoinNames.put(24, "어거");
        sCoinNames.put(25, "웨이브");
        sCoinNames.put(26, "아크");
        sCoinNames.put(27, "모네로");
        sCoinNames.put(28, "라이트코인");
        sCoinNames.put(29, "리스크");
        sCoinNames.put(30, "버트코인");
        sCoinNames.put(31, "피벡스");
        sCoinNames.put(32, "메탈");
        sCoinNames.put(33, "대쉬");
        sCoinNames.put(34, "지캐시");
    }

    public static HashMap<Integer, String> sCoinNamesWithEng = new HashMap<>();

    static {
        sCoinNamesWithEng.put(0, "비트코인\nBTC");
        sCoinNamesWithEng.put(1, "에이다\nADA");
        sCoinNamesWithEng.put(2, "리플\nXRP");
        sCoinNamesWithEng.put(3, "스테이터스네트워크토큰\nSNT");
        sCoinNamesWithEng.put(4, "퀀텀\nQTUM");
        sCoinNamesWithEng.put(5, "이더리움\nETH");
        sCoinNamesWithEng.put(6, "머큐리\nMER");
        sCoinNamesWithEng.put(7, "네오\nNEO");
        sCoinNamesWithEng.put(8, "스팀달러\nSBD");
        sCoinNamesWithEng.put(9, "스팀\nSTEEM");
        sCoinNamesWithEng.put(10, "스텔라루멘\nXLM");
        sCoinNamesWithEng.put(11, "아인스타이늄\nEMC2");
        sCoinNamesWithEng.put(12, "비트코인골드\nBTG");
        sCoinNamesWithEng.put(13, "아더\nARDR");
        sCoinNamesWithEng.put(14, "뉴이코미무브먼트\nXEM");
        sCoinNamesWithEng.put(15, "블록틱스\nTIX");
        sCoinNamesWithEng.put(16, "파워렛저\nPOWR");
        sCoinNamesWithEng.put(17, "비트코인캐시\nBCC");
        sCoinNamesWithEng.put(18, "코모도\nKMD");
        sCoinNamesWithEng.put(19, "스트라티스\nSTRAT");
        sCoinNamesWithEng.put(20, "이더리움클래식\nETC");
        sCoinNamesWithEng.put(21, "오미세고\nOMG");
        sCoinNamesWithEng.put(22, "그리스톨코인\nGRS");
        sCoinNamesWithEng.put(23, "스토리지\nSTORJ");
        sCoinNamesWithEng.put(24, "어거\nREP");
        sCoinNamesWithEng.put(25, "웨이브\nWAVES");
        sCoinNamesWithEng.put(26, "아크\nARK");
        sCoinNamesWithEng.put(27, "모네로\nXMR");
        sCoinNamesWithEng.put(28, "라이트코인\nLTC");
        sCoinNamesWithEng.put(29, "리스크\nLSK");
        sCoinNamesWithEng.put(30, "버트코인\nVTC");
        sCoinNamesWithEng.put(31, "피벡스\nPIVX");
        sCoinNamesWithEng.put(32, "메탈\nMTL");
        sCoinNamesWithEng.put(33, "대쉬\nDASH");
        sCoinNamesWithEng.put(34, "지캐시\nZEC");
    }
}

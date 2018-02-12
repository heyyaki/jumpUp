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
        sCoinNames.put(0, "비트코인\nBTC");
        sCoinNames.put(1, "에이다\nADA");
        sCoinNames.put(2, "리플\nXRP");
        sCoinNames.put(3, "스테이터스네트워크토큰\nSNT");
        sCoinNames.put(4, "퀀텀\nQTUM");
        sCoinNames.put(5, "이더리움\nETH");
        sCoinNames.put(6, "머큐리\nMER");
        sCoinNames.put(7, "네오\nNEO");
        sCoinNames.put(8, "스팀달러\nSBD");
        sCoinNames.put(9, "스팀\nSTEEM");
        sCoinNames.put(10, "스텔라루멘\nXLM");
        sCoinNames.put(11, "아인스타이늄\nEMC2");
        sCoinNames.put(12, "비트코인골드\nBTG");
        sCoinNames.put(13, "아더\nARDR");
        sCoinNames.put(14, "뉴이코미무브먼트\nXEM");
        sCoinNames.put(15, "블록틱스\nTIX");
        sCoinNames.put(16, "파워렛저\nPOWR");
        sCoinNames.put(17, "비트코인캐시\nBCC");
        sCoinNames.put(18, "코모도\nKMD");
        sCoinNames.put(19, "스트라티스\nSTRAT");
        sCoinNames.put(20, "이더리움클래식\nETC");
        sCoinNames.put(21, "오미세고\nOMG");
        sCoinNames.put(22, "그리스톨코인\nGRS");
        sCoinNames.put(23, "스토리지\nSTORJ");
        sCoinNames.put(24, "어거\nREP");
        sCoinNames.put(25, "웨이브\nWAVES");
        sCoinNames.put(26, "아크\nARK");
        sCoinNames.put(27, "모네로\nXMR");
        sCoinNames.put(28, "라이트코인\nLTC");
        sCoinNames.put(29, "리스크\nLSK");
        sCoinNames.put(30, "버트코인\nVTC");
        sCoinNames.put(31, "피벡스\nPIVX");
        sCoinNames.put(32, "메탈\nMTL");
        sCoinNames.put(33, "대쉬\nDASH");
        sCoinNames.put(34, "지캐시\nZEC");
    }
}

package com.example.com.jumpupbitcoin.setting;

public class SettingData {

    public int pre_check;    // 전전단계 퍼센티지 계산 유무  - 1 활성, 0 비활성
    public int trade_check;    // 거래량 증가 확인 유무  - 1 활성, 0 비활성
    public int pre_trade_check;    // 전전단계 거래량 퍼센티지 계산 유무  - 1 활성, 0 비활성

    public int bunbong;

    public float price_per;
    public float price_per_pre;
    public float trade_per;
    public float trade_per_pre;

    public float down_price_per;
    public float down_price_per_pre;
    public float down_trade_per;
    public float down_trade_per_pre;
}

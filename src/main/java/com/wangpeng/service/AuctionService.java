package com.wangpeng.service;

import com.wangpeng.config.SysResult;
import com.wangpeng.pojo.AuctionVO;

public interface AuctionService {


    SysResult start(AuctionVO auctionVO);

    SysResult favorite(AuctionVO auctionVO);

    SysResult login(AuctionVO auctionVO);
}

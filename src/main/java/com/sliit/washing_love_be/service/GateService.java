package com.sliit.washing_love_be.service;

import com.sliit.washing_love_be.dto.GateDto;

public interface GateService {
    String gateOperation(GateDto gateDto)throws Exception;
}

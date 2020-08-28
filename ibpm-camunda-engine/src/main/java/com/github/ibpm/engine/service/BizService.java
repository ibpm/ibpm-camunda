package com.github.ibpm.engine.service;

import java.util.Map;

public interface BizService {

    Object add(Map<String, Object> paramMap);

    Object update(Map<String, Object> paramMap);

    Object get(Map<String, Object> paramMap);
}

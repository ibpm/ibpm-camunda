package com.github.ibpm.core.ext.notice.channel;

import com.github.ibpm.core.ext.notice.MessageBean;
import com.github.ibpm.core.ext.notice.MessageBean;

public interface Sender {

    void send(MessageBean messageBean) throws Exception;

    int getIndex();
}

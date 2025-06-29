package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.entity.RedArchiveUser;

import java.util.List;

public interface RedArchiveUserMapper extends BaseMapper<RedArchiveUser> {

    RedArchiveUser findByEmail(String email);
}
